package 其它;

public class Person {
    private String name;
    int age;
    String sex;

    public Person() {
        System.out.println("您调用了Person类的无参构造方法");
    }

    public Person(String name, int age, String sex) {
        System.out.println("您调用了Person类的有参构造方法");
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getSex() {
        return this.sex;
    }

    public static void main(String[] args) {
      Person p1=new Person();
      Person p2=new Person("李雷",12,"girl");
      System.out.println("名字"+p1.getName());
      System.out.println("年龄"+p1.getAge()); 
      System.out.println("性别"+p1.getSex());
      System.out.println("******************分割线*********************");
      System.out.println("名字"+p2.getName());
      System.out.println("年龄"+p2.getAge());
      System.out.println("性别"+p2.getSex());
  }
}




class PersonTest {
    Person p1 = new Person();
    Person p2 = new Person("李雷", 12, "girl");

    public static void main(String[] args) {
//        System.out.println("名字" + this.p1.getName());
//        System.out.println("年龄" + this.p1.getAge());
//        System.out.println("性别" + this.p1.getSex());
//        System.out.println("******************分割线*********************");
//        System.out.println("名字" + this.p2.getName());
//        System.out.println("年龄" + this.p2.getAge());
//        System.out.println("性别" + this.p2.getSex());
        
        new PersonTest().测试();
    }
    public static void main(String args) {

    }

    public void 测试() {
        System.out.println("名字" + this.p1.getName());
        System.out.println("年龄" + this.p1.getAge());
        System.out.println("性别" + this.p1.getSex());
        System.out.println("******************分割线*********************");
        System.out.println("名字" + this.p2.getName());
        System.out.println("年龄" + this.p2.getAge());
        System.out.println("性别" + this.p2.getSex());
    }
}