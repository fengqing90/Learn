package learn.bankQueue;

import java.util.ArrayList;
import java.util.List;

public class NumberManager {

    private NumberManager() {
    }

    private static NumberManager instance = new NumberManager();

    public static NumberManager getInstance() {
        return NumberManager.instance;
    }

    private static Integer identityQueue = 0;

    public static List<Object> commonQueue = new ArrayList<>(0);
    public static List<Object> expressQueue = new ArrayList<>(0);
    public static List<Object> vipQueue = new ArrayList<>(0);

    public synchronized Integer getIdentityQueue() {
        ++NumberManager.identityQueue;
        return NumberManager.identityQueue;
    }

    // ///////////////
    public static synchronized Object getCommon() {
        return NumberManager.commonQueue.remove(0);
    }

    public static void addCommonQueue(Object obj) {
        NumberManager.commonQueue.add(obj);
    }

    // /////////////////////
    // ///////////////
    public static synchronized Object getExpress() {
        return NumberManager.expressQueue.remove(0);
    }

    public static void addExpressQueue(Object obj) {
        NumberManager.expressQueue.add(obj);
    }

    // /////////////////////
    // ///////////////
    public static synchronized Object getVip() {
        return NumberManager.vipQueue.remove(0);
    }

    public static void addVipQueue(Object obj) {
        NumberManager.vipQueue.add(obj);
    }
    // /////////////////////

}
