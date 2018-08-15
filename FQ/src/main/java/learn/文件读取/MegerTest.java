package learn.文件读取;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class MegerTest {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer();
        Stream.of(
            new File("C:\\Users\\fengqing.xxx\\Desktop\\LOGS").listFiles())
            .forEach(i -> {
                try {
                    sb.append(FileUtils.readFileToString(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    FileUtils.write(new File(
                        "C:\\Users\\fengqing.xxx\\Desktop\\LOGS\\新建文本文档.txt"),
                        sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
