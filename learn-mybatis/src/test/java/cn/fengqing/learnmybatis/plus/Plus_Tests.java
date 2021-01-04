// package cn.fengqing.learnmybatis.plus;
//
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
//
// import javax.annotation.Resource;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
// import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//
// import cn.fengqing.learnmybatis.model.User;
// import cn.fengqing.learnmybatis.plus.mapper.UserMapper;
//
// @SpringBootTest
// class Plus_Tests {
//
//     // 继承了BaseMapper，所有的方法都来自己父类
//     // 我们也可以编写自己的扩展方法！
//     @Resource
//     private UserMapper userMapper;
//
//     /**
//      * 测试插入
//      */
//     @Test
//     public void testInsert() {
//         User user = new User();
//         user.setName("AAAA");
//         user.setAge(18);
//         user.setEmail("AAAA@qq.com");
//
//         int result = this.userMapper.insert(user); // 帮我们自动生成id
//         System.out.println(result); // 受影响的行数
//         System.out.println(user); // 看到id会自动填充。    }  
//     }
//
//     @Test
//     void selectList() {
//         // 参数是一个 Wrapper ，条件构造器，这里我们先设置条件为空，查询所有。
//         List<User> users = this.userMapper.selectList(null);
//         users.forEach(System.out::println);
//     }
//
//     /**
//      * 强制更新，版本号不跟新
//      */
//     @Test
//     public void testUpdate() {
//         User user = new User();
//         // 通过条件自动拼接动态sql
//         user.setId(3L);
//         user.setName("kwhua_mybatis-plus_updateTest");
//         user.setAge(1);
//         // 注意：updateById 但是参数是一个对象！
//         int i = this.userMapper.updateById(user);
//         System.out.println(user);
//         System.out.println(i);
//     }
//
//     /**
//      * 乐观锁
//      */
//     @Test
//     public void testOptimisticLocker() {
//         // 1、查询用户信息
//         User user = this.userMapper.selectById(3L);
//         // 2、修改用户信息
//         user.setName("kwhua");
//         user.setEmail("123456@qq.com");
//         // 3、执行更新操作
//         this.userMapper.updateById(user);
//     }
//
//     // 测试乐观锁失败！多线程下
//     @Test
//     public void testOptimisticLocker2() {
//
//         // 线程 1
//         User user = this.userMapper.selectById(3L);
//         user.setName("kwhua111");
//         user.setEmail("123456@qq.com");
//
//         // 模拟另外一个线程执行了插队操作
//         User user2 = this.userMapper.selectById(3L);
//         user2.setName("kwhua222");
//         user2.setEmail("123456@qq.com");
//         this.userMapper.updateById(user2);
//
//         // 自旋锁来多次尝试提交！
//         this.userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
//     }
//
//     /**
//      * 测试乐观锁失败！多线程下
//      */
//     @Test
//     public void testOptimisticLocker3() {
//
//         User user = this.userMapper.selectById(3L);
//         User finalUser = user;
//         Thread t = new Thread(() -> {
//             // 线程 1
//             finalUser.setName("kwhua111");
//             finalUser.setEmail("123456@qq.com");
//             this.userMapper.updateById(finalUser); // 如果没有乐观锁就会覆盖插队线程的值！
//         });
//         t.start();
//
//         // 模拟另外一个线程执行了插队操作
//         // User user2 = this.userMapper.selectById(3L);
//         // user2.setName("kwhua222");
//         // user2.setEmail("123456@qq.com");
//         // this.userMapper.updateById(user2);
//         user = this.userMapper.selectById(3L);
//         user.setName("kwhua222");
//         user.setEmail("123456@qq.com");
//         this.userMapper.updateById(user);
//
//         // 自旋锁来多次尝试提交！
//
//         while (t.isAlive()) {
//
//         }
//     }
//
//     // 测试查询
//     @Test
//     public void testSelectById() {
//         User user = this.userMapper.selectById(1L);
//         System.out.println(user);
//     }
//
//     // 测试批量查询！
//     @Test
//     public void testSelectByBatchId() {
//         List<User> users = this.userMapper
//             .selectBatchIds(Arrays.asList(1, 2, 3));
//         users.forEach(System.out::println);
//     }
//
//     // 按条件查询之一使用map操作
//     @Test
//     public void testSelectByBatchIds() {
//         HashMap<String, Object> map = new HashMap<>();
//         // 自定义要查询
//         map.put("name", "kwhua");
//         map.put("age", 15);
//
//         List<User> users = this.userMapper.selectByMap(map);
//         users.forEach(System.out::println);
//     }
//
//     // 测试分页查询
//     @Test
//     public void testPage() {
//         // 参数一：当前页
//         // 参数二：页面大小
//         Page<User> page = new Page<>(2, 1);
//         this.userMapper.selectPage(page, null);
//         page.getRecords().forEach(System.out::println);
//         System.out.println(page.getTotal());
//     }
//
//     // 测试删除
//     @Test
//     public void testDeleteById() {
//         this.userMapper.deleteById(3L);
//     }
//
//     // 通过id批量删除
//     @Test
//     public void testDeleteBatchId() {
//         this.userMapper.deleteBatchIds(Arrays.asList(2L, 3L));
//     }
//
//     // 通过map删除
//     @Test
//     public void testDeleteMap() {
//         HashMap<String, Object> map = new HashMap<>();
//         map.put("name", "kwhua");
//         this.userMapper.deleteByMap(map);
//     }
//
//     @Test
//     public void testSelectByQueryWrapper() {
//         // 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
//         QueryWrapper<User> wrapper = new QueryWrapper<>();
//         wrapper.isNotNull("name") //不为空
//             .isNotNull("email").ge("age", 18);
//         userMapper.selectList(wrapper).forEach(System.out::println); // 和我们刚才学习的map对比一下
//     }
//
//     @Test
//     public void testSelectByQueryWrapper2() {
//         // 查询名字kwhua
//         QueryWrapper<User> wrapper = new QueryWrapper<>();
//         wrapper.eq("name", "fengqing");
//         User user = userMapper.selectOne(wrapper); // 查询一个数据用selectOne，查询多个结果使用List 或者 Map
//         System.out.println(user);
//     }
// }
