package learn.文件读取;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import javax.persistence.Table;

public class ReadPackage {

    public static void main(String[] args) {
        Stream.of(new File(
            "C:\\\\W\\\\Workspace\\\\Eclipse Workspace\\\\Kepler\\\\code\\\\kepler-basedao\\\\src\\\\main\\\\java\\\\cn\\\\xxxx\\\\kepler\\\\model\\\\migrate")
                .listFiles())
            .forEach(i -> {
                try {
                    String className = "cn.xxx.kepler.model.migrate." + i
                        .getPath().substring(i.getPath().lastIndexOf("\\") + 1,
                            i.getPath().length() - 5);

                    Class cls = Class.forName(className);

                    Annotation a = cls.getAnnotation(Table.class);

                    if (a != null) {
                        Table t = (Table) a;
                        System.out.println("'" + t.name() + "',");
                    }
                } catch (Exception e) {
                }
            });
    }
}
