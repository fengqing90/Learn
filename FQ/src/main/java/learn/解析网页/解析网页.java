package learn.解析网页;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 网页解析 Jsoup
 *
 * @author fengqing
 * @date 2020/12/9 16:03
 */
public class 解析网页 {

    public static void main(String[] args) throws IOException {
        test2();

    }

    static void test() throws IOException {
        Document document = Jsoup.connect("https://www.open-open.com/jsoup/")
            .get();
        System.out.println(document);
        System.out.println(document.getElementsByTag("a"));
    }

    static void test2() throws IOException {
        Document document = Jsoup.connect(
            "https://dl.pconline.com.cn/html_2/1/74/id=168&pn=0&linkPage=1.html")
            .get();
        Elements listA = document.getElementsByTag("a");
        System.out.println(listA);

        System.out.println("***************************************");
        listA.stream().parallel()
            .filter(a -> a.getElementsByAttribute("tempurl").size() > 0)
            .forEach(a -> {
                System.out.println("-----------------");
                System.out.println(a);
                String fileName = a.attr("tempurl")
                    .substring(a.attr("tempurl").lastIndexOf('/'));
                String downloadUrl = "http://www.winrar.com.cn/download/"
                    + fileName;
                System.out.println(downloadUrl);
                try {
                    URL url = new URL(downloadUrl);

                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(
                        new File(
                            "W:\\" + a.text() + "_" + fileName.substring(1)));
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    while ((length = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, length);
                    }
                    outputStream.close();
                    inputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        //http://www.winrar.com.cn/download/wrar591scp.exe
    }
}
