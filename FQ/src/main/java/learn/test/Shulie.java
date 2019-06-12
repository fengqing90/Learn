package learn.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Shulie {

    @Test
    public void test_recordLog() {
        // SysLogService service = (SysLogService)
        // SpringUtil.getBean("SysLogService");
        // SysLog log = new SysLog();
        // service.saveSysLog(log);
        //		WeatherService service = (WeatherService) SpringUtil.getBean("weatherServiceImpl");
        //		service.getHistoricalHigh(new Date());
        // org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
        // org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter
        // String myAppName = "DaKa";
        // String regKey =
        // "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run";
        // Runtime.getRuntime().exec("reg " + (isStart ? "add " : "delete ") +
        // regKey + "  "
        // + myAppName + (isStart ? " /t reg_sz /d " + dirpath : " /f"));
        // Runtime.getRuntime().exec("shutdown -s -f -t 10");
        // Runtime.getRuntime().exec("shutdown -s -f -t 10");
    }

    public static void main(String[] args) {

        //        String[] a = new String(
        //            "UAC_SYSTEM_USER SU LEFT JOIN UAC_SROLE SR  ON  SU.DATA_ROLE_ID = SR.ROLE_ID LEFT JOIN UAC_SROLE SR  ON  SU.DATA_ROLE_ID = SR.ROLE_ID")
        //            .trim().split("ON");
        //        System.out.println(Arrays.toString(a));
        //
        //        try {
        //            Runtime.getRuntime().exec("mspaint");
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //
        //        FileDialog fd = new FileDialog(new Frame(), "测试", FileDialog.LOAD);
        //        FilenameFilter ff = new FilenameFilter() {
        //            @Override
        //            public boolean accept(File dir, String name) {
        //                if (name.endsWith("jpg")) {
        //                    return true;
        //                }
        //                return false;
        //            }
        //        };
        //        fd.setFilenameFilter(ff);
        //        fd.setVisible(true);
        //        System.out.println(fd.getDirectory() + fd.getFile());
        Shulie.shulie(0, 1, 4000);
    }

    public static void shulie(int a, int b, int max_b) {

        int result = a + b;

        if (result > max_b) {
            return;
        }
        //
        //        System.out.print(a);
        //        System.out.print("+");
        //        System.out.print(b);
        //        System.out.print(" ");
        //        System.out.print(result);
        //        System.out.println();
        //
        System.out.print(result);
        System.out.print(" ");
        Shulie.shulie(b, result, max_b);
    }
}
