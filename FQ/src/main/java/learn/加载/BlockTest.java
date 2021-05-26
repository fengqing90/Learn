package learn.加载;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/11 21:05
 */
public class BlockTest {
    public static void main(String[] args) {

        String desc = Person.desc;
        System.out.println(desc);

        Person p1 = new Person();
        Person p2 = new Person();
        System.out.println(p1.age);

        Person.info();
    }
}

class Person {
    //属性
    String name;

    int age;

    static String desc = "我是一个人";

    //构造器
    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //非static的代码块
    {
        System.out.println("hello, block - 2");
    }
    {
        System.out.println("hello, block - 1");
        //调用非静态结构
        age = 1;
        eat();
        //调用静态结构
        desc = "我是一个爱学习的人1";
        info();
    }
    //static的代码块
    static {
        System.out.println("hello,static block-2");
    }
    static {
        System.out.println("hello,static block-1");
        //调用静态结构
        desc = "我是一个爱学习的人";
        info();
        //不可以调用非静态结构
//		eat();
//		name = "Tom";
    }

    //方法
    public void eat() {
        System.out.println("吃饭");
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }

    public static void info() {
        System.out.println("我是一个快乐的人！");
    }

}
