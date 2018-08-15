package learn.代理模式;

public class People implements Italk {

    @Override
    public void talk(String msg) {
        System.out.println(msg + "!你好,我是" + this.username + "，我年龄是" + this.age);
    }

    public String username;
    public String age;

    public String getName() {
        return this.username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public People(String name1, String age1) {
        this.username = name1;
        this.age = age1;
    }
}
