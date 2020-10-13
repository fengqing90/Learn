package learn.jvm;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/5/30 21:55
 */
public class JvmDemo1 {

    public static void main(String[] args) throws Exception {
        Thread.sleep(30000);

        while (true) {
            loadDataMB();
        }
    }

    /**
     * 每秒生成50*100kb 数据
     *
     * @throws InterruptedException
     */
    private static void loadData() throws Exception {

        byte[] data = null;

        // 100 * 1024 byte = 100kb
        for (int i = 0; i < 50; i++) {
            data = new byte[100 * 1024];
        }
        data = null;

        Thread.sleep(1000);
    }

    /**
     * 每秒生成50*1mb 数据
     *
     * @throws InterruptedException
     */
    private static void loadDataMB() throws Exception {

        byte[] data = null;

        // 1024 * 1024 byte = 1mb
        for (int i = 0; i < 50; i++) {
            data = new byte[1024 * 1024];
        }
        data = null;

        Thread.sleep(1000);
    }
}
