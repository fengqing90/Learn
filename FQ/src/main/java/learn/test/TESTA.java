package learn.test;

import org.apache.commons.lang.exception.ExceptionUtils;

public class TESTA {

    public static void main(String[] args) {
        //        String a = "ProjectUrl.thread_wealth_proj+/rest/requests/states/{states}";
        //        System.out.println(a.replace(
        //            "ProjectUrl.thread_wealth_proj+/rest/requests/states/", "1"));
        try {
            TESTA.computeAge(null);
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getMessage(e));
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(ExceptionUtils.getFullStackTrace(e));
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(ExceptionUtils.getRootCauseMessage(e));
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    public static int computeAge(Integer n) {
        if (n == 1) {
            return 10;
        }
        return TESTA.computeAge(n - 1) + 2;
    }

}
