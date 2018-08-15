package 其它;

import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
        String str8 = "一二三四五六";
        int amount = 0;
        String regex = "1\\d\\.";   //正则表达式
        str8.matches(regex);
        for (int i = 0; i < str8.length(); i++) {
            boolean g = Pattern.matches("^[\u4E00-\u9FA5]{0,}$",
                String.valueOf(str8.charAt(i)));

            if (g) {
                amount++;
            }
        }
        System.out.println("字符串str6中含有几个汉字：" + amount);

    }
}
