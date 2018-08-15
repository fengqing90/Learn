package learn. threadLocal;

public class SerialNum {
    // The next serial number to be assigned　
    private static int nextSerialNum = 0;

    private static ThreadLocal serialNum = new ThreadLocal() {

        @Override
        protected synchronized Object initialValue() {
            return new Integer(SerialNum.nextSerialNum++);
        }
    };

    public static int get() {
        return ((Integer) SerialNum.serialNum.get()).intValue();
    }
}