package cn.fengqing.learnmybatis.分页.web;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.fengqing.learnmybatis.model.Foo;
import cn.fengqing.learnmybatis.分页.mapper.FooMapper;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/30 11:26
 */
@RestController
public class FooRestController {

    @Resource
    private FooMapper fooMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;
    @Resource
    private PlatformTransactionManager transactionManager;

    @GetMapping("foo/scan/0/{limit}")
    public void scanFoo0(@PathVariable("limit") int limit) throws Exception {
        try (Cursor<Foo> cursor = fooMapper.scan(limit)) {  // 1
            cursor.forEach(foo -> {
                System.out.println(foo);
            });                      // 2
        }
    }

    /***
     * 方案一：SqlSessionFactory<br>
     * 我们可以用 SqlSessionFactory 来手工打开数据库连接
     * 
     * @param limit
     * @throws Exception
     */
    @GetMapping("foo/scan/1/{limit}")
    public void scanFoo1(@PathVariable("limit") int limit) throws Exception {
        try (SqlSession sqlSession = sqlSessionFactory.openSession();  // 1
                Cursor<Foo> cursor = sqlSession.getMapper(FooMapper.class)
                    .scan(limit)   // 2
        ) {
            cursor.forEach(foo -> {
                System.out.println(foo);
            });
        }
    }

    /***
     * 方案二：TransactionTemplate<br>
     * 在 Spring 中，我们可以用 TransactionTemplate 来执行一个数据库事务，这个过程中数据库连接同样是打开的
     *
     * @param limit
     * @throws Exception
     */
    @GetMapping("foo/scan/2/{limit}")
    public void scanFoo2(@PathVariable("limit") int limit) throws Exception {
        TransactionTemplate transactionTemplate = new TransactionTemplate(
            transactionManager);  // 1

        transactionTemplate.execute(status -> {               // 2
            try (Cursor<Foo> cursor = fooMapper.scan(limit)) {
                cursor.forEach(foo -> {
                    System.out.println(foo);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /***
     * 方案三：@Transactional 注解<br>
     * 这个本质上和方案二一样
     * 
     * @param limit
     * @throws Exception
     */
    @GetMapping("foo/scan/3/{limit}")
    @Transactional
    public void scanFoo3(@PathVariable("limit") int limit) throws Exception {
        try (Cursor<Foo> cursor = fooMapper.scan(limit)) {
            cursor.forEach(foo -> {
                System.out.println(foo);
            });
        }
    }
}
