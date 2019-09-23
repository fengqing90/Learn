package learn.设计模式.代理模式;

/**
 * @author FengQing
 */
public class MyProxyTest {
    /**
     * 代理模式
     *
     * @param args
     */
    public static void main(String[] args) {
        //不需要执行额外方法的
        Italk people1 = new People("湖海散人", "18");
        people1.talk("No ProXY Test");
        System.out.println("-----------------------------");
        //需要执行额外方法的
        TalkProxy talker = new TalkProxy(people1);
        talker.talk("ProXY Test", "七里香");
    }
}
