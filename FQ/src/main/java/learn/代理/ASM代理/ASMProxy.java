package learn.代理.ASM代理;

import org.junit.Test;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;
import learn.代理.IUserApi;
import learn.代理.UserApi;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/3/31 14:36
 */
@Slf4j
public class ASMProxy extends ClassLoader {

    public static <T> T getProxy(Class clazz) throws Exception {

        ClassReader classReader = new ClassReader(clazz.getName());
        ClassWriter classWriter = new ClassWriter(classReader,
            ClassWriter.COMPUTE_MAXS);

        classReader.accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, final String name,
                    String descriptor, String signature, String[] exceptions) {

                // 方法过滤
                if (!"queryUserInfo".equals(name))
                    return super.visitMethod(access, name, descriptor,
                        signature, exceptions);

                final jdk.internal.org.objectweb.asm.MethodVisitor methodVisitor = super.visitMethod(
                    access, name, descriptor, signature, exceptions);

                return new AdviceAdapter(Opcodes.ASM5, methodVisitor, access,
                    name, descriptor) {

                    @Override
                    protected void onMethodEnter() {
                        // 执行指令；获取静态属性
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/System", "out", "Ljava/io/PrintStream;");
                        // 加载常量 load constant
                        methodVisitor.visitLdcInsn(name + " 你被代理了，By ASM！");
                        // 调用方法
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/io/PrintStream", "println",
                            "(Ljava/lang/String;)V", false);
                        super.onMethodEnter();
                    }
                };
            }
        }, ClassReader.EXPAND_FRAMES);

        byte[] bytes = classWriter.toByteArray();

        return (T) new ASMProxy()
            .defineClass(clazz.getName(), bytes, 0, bytes.length).newInstance();
    }

    @Test
    public void test_ASMProxy() throws Exception {
        IUserApi userApi = ASMProxy.getProxy(UserApi.class);
        String invoke = userApi.queryUserInfo();
        log.info("测试结果：{}", invoke);
    }

}

/**
 * 测试结果：
 * queryUserInfo 你被代理了，By ASM！
 * 20:12:26.791 [main] INFO org.itstack.interview.test.ApiTest -
 * 测试结果：小傅哥，公众号：bugstack虫洞栈 | 沉淀、分享、成长，让自己和他人都能有所收获！
 * Process finished with exit code 0
 */