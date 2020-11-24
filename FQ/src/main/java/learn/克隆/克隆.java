package learn.克隆;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/24 10:49
 */
public class 克隆 {

    static class A implements Serializable, Cloneable {
        private String aa;

        private String aa() {
            return this.getClass().getSimpleName() + ":aa():" + this.aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    static class B implements Cloneable {
        private String bb;
        private A aa;

        public String getBb() {
            return bb;
        }

        public void setBb(String bb) {
            this.bb = bb;
        }

        public A getAa() {
            return aa;
        }

        public void setAa(A aa) {
            this.aa = aa;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {

            this.aa = (A) this.aa.clone();

            // 默认浅克隆
            return super.clone();
        }
    }

    public static void main(String[] args) throws Exception {

        B b = new B();
        b.setAa(new A());
        System.out.println(b);
        System.out.println(b.hashCode());
        System.out.println(b.getAa());
        System.out.println(b.getAa().hashCode());

        System.out.println("**********************");

        B bb = (B) b.clone();
        System.out.println(bb);
        System.out.println(bb.hashCode());
        System.out.println(bb.getAa());
        System.out.println(bb.getAa().hashCode());

        System.out.println("=======================================");

        A aa = new A();
        aa.setAa("克隆");

        System.out.println(aa);
        System.out.println(aa.hashCode());
        System.out.println(aa.aa());

        try (ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(new File("W:\\1.txt")))) {
            out.writeObject(aa);
        }

        System.out.println("**********************");

        try (ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(new File("W:\\1.txt")))) {
            Object o = in.readObject();
            aa = (A) o;
            System.out.println(aa);
            System.out.println(aa.hashCode());
            System.out.println(aa.aa());
        }

    }
}
