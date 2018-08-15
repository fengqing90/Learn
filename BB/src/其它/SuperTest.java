package 其它;

/**
 * @author FengQing
 * @date 2017年9月13日 下午1:00:30
 */
public class SuperTest {

    /*
     * 外卖小哥
     */
    class 外卖小哥 {
        public void todo(String buy) {
            System.out.println("去菜场....买" + buy);
        }
    }

    /*
     * 买苹果的类。
     * 买苹果流程：
     * 1.让 外卖小哥 买苹果
     * 2.送苹果
     */
    class BuyApple extends 外卖小哥 {

        // 由于 BuyApple 继承（extends）了 外卖小哥，所以 在BuyApple类里面 super 就等于外卖小哥，即 调用super.todo("苹果"),就是调用外卖小哥类中的todo
        public void buy() {
            super.todo("苹果");// 让 外卖小哥 买苹果，
            // 做剩下的事情，送苹果
            System.out.println("送苹果....");
        }
    }

    public static void main(String[] args) {
        System.out.println("1.想吃苹果");
        System.out.println("2.订外卖。");
        new SuperTest().new BuyApple().buy();
        System.out.println("3.苹果送到。");
        System.out.println("4.吃苹果....");
    }
}
