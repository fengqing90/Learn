package learn.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * 工厂类
 * 
 * @author FengQing
 */
public class InfoManagerFactory {
    private static InfoManager manger = new InfoManager();

    /**
     * 创建原始的InfoManager
     * 
     * @return
     */
    public static InfoManager getInstance() {
        return InfoManagerFactory.manger;
    }

    /**
     * 创建不同权限要求的InfoManager
     *
     * @param auth
     * @return
     */
    public static InfoManager getSelectivityAuthInstance(AuthProxy auth) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(InfoManager.class);
        enhancer.setCallbacks(new Callback[] { auth, NoOp.INSTANCE });
        enhancer.setCallbackFilter(new AuthProxyFilter());
        return (InfoManager) enhancer.create();
    }
}