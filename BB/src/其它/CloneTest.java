package 其它;

/**
 * 克隆
 * 
 * @author FengQing
 * @date 2017年9月13日 下午12:30:41
 */
public class CloneTest {

    // 一个类需要有克隆功能，就必需实现（implements） Cloneable接口，并且重现clone() 方法。
    class A implements Cloneable {

        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // 重写clone()方法。快捷键 按alt，再按s，再按v 就可以看到可以重写的方法。
        @Override // @Override 这个注解代表 这个方法是重写的。
        protected A clone() throws CloneNotSupportedException{
            return (A) super.clone();
        }

    }

    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("初始化数据.... ");
        CloneTest.A a = new CloneTest().new A();
        a.setName("BB我错了");
        System.out.println("a 地址= " + a);
        System.out.println("a.name 值= " + a.getName());
        System.out.println("**********分割线*************");
        System.out.println();

        // 把 a 赋给  b
        System.out.println("把 a 赋给  b ,此时 a和b都是指向的同一个地址 ，也就是a和b 是同一个对象 。");
        CloneTest.A b = a;
        System.out.println("a 地址= " + a);
        System.out.println("b 地址= " + b);
        System.out.println("b.name 值= " + b.getName());

        System.out.println("**********分割线*************");
        System.out.println();

        // 把 a 克隆后 赋给 b
        System.out.println(" 把 a 克隆后 赋给 b，此时a和b 不是同一个地址，也就是 a和b 不是同一个对象。");
        b = a.clone();
        System.out.println("a 地址= " + a);
        System.out.println("b 地址= " + b);
        System.out.println("b.name 值= " + b.getName());
    }
}
