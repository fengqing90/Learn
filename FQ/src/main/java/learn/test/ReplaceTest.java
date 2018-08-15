package learn.test;

public class ReplaceTest {
    public static void main(String[] args) {
        String str = "loan_performSaleReport_url:ProjectUrl.thread_statistics_proj+'/rest/performSaleReport',});";
        System.out.println(str.substring(0, str.lastIndexOf(',')));
        System.out.println(str.replace("ProjectUrl.thread_statistics_proj+",
            "loan_performSaleReport_url"));
    }
}
