package learn.Learn_synchronized;

/**
 * KEP-TODO
 *
 * @ClassName TestA
 * @Author FengQing
 * @Date 2019/6/12 19:26
 */
public class TestA {

    private int i = 0;


    public void add() {
        System.out.println(Thread.currentThread().getName() + "@@@@@" + this.i);
        while (this.i <= 100) {
            this.i++;
            System.out.println(Thread.currentThread().getName() + "__" + this.i);
        }

    }
}
