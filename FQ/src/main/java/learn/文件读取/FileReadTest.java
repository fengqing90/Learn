package learn.文件读取;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gs.utils.POIUtils;

public class FileReadTest {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        File f = new File("C:\\Users\\fengqing.xxx\\Desktop\\CA");
        List<String> list = new ArrayList<>();

        Stream.of(f.listFiles()).forEach(
            i -> list.addAll(FileReadTest.readPath(i)));

        System.out.println(list.size());

        File nf = new File("C:\\Users\\fengqing.xxx\\Desktop\\CA.txt");

        if (!nf.exists()) {
            nf.createNewFile();
        }
        FileUtils.writeLines(nf, list);

        Map<String, String> map = FileReadTest.excel();
        int i = 0;
        File file = new File(
            "C:\\Users\\fengqing.xxx\\Desktop\\ca_data.xlsx");
        // 1.读入Excel 文件
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        for (Entry<String, String> e : map.entrySet()) {
            if (list.contains(e.getKey().trim())) {
                i++;
                System.out.println(e.getValue() + " ：" + e.getKey());
                sheet.getRow(Integer.valueOf(e.getValue())).createCell(6)
                    .setCellValue("有");
            }
        }
        workbook.write(new FileOutputStream(
            "C:\\Users\\fengqing.xxx\\Desktop\\ca_data_2.xlsx"));
        System.out.println("大小：" + i);
    }

    public static List<String> readPath(File f) {
        List<String> list = new ArrayList<>();
        if (f.isDirectory()) {

            Stream.of(f.listFiles()).forEach(
                i -> list.addAll(FileReadTest.readPath(i)));

        } else {
            String prefix = StringUtils.substringAfterLast(f.getName(), ".");
            if (prefix.equalsIgnoreCase("pdf")) {
                String aa = StringUtils.substringBefore(f.getName(), ".");
                if (aa.equals("13961400")) {
                    System.out.println();
                }
                list.add(f.getName());
            }
        }

        return list;
    }

    @SuppressWarnings("resource")
    public static Map<String, String> excel() throws Exception {

        Map<String, String> map = new HashMap<>();
        File file = new File(
            "C:\\Users\\fengqing.xxx\\Desktop\\ca_data.xlsx");
        // 1.读入Excel 文件
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));

        // 4.初始化Excel 数据
        List<Map<String, Object>> readDataList = POIUtils.initExcelData(
            workbook.getSheetAt(0), new String[] { "文件上传时间", "命令", "文件路径",
                "复制路径" });

        for (int i = 0; i < readDataList.size(); i++) {

            String tmp = StringUtils.substringAfterLast((String) readDataList
                .get(i).get("复制路径"), "/");
            map.put(StringUtils.substringBefore(tmp, "_"),
                StringUtils.substringAfterLast(tmp, "_"));
        }

        return map;
    }
}
