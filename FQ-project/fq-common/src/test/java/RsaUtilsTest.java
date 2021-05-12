import org.junit.Test;

import cn.fq.common.utils.RsaUtils;

/**
 * @author fengqing
 * @date 2021/5/11 14:46
 */
public class RsaUtilsTest {

    private final String PATH = "C:\\W\\Workspace\\Eclipse Workspace\\Learn\\FQ-PROJECT\\";
    private final String privateFilePath = PATH + "private.key";
    private final String publicFilePath = PATH + "rsa_public.pub";

    @Test
    public void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }

    @Test
    public void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }

    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath, privateFilePath, "saltss", 2048);
    }
}
