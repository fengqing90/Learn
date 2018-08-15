package gs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ijay
 */
public class StringUtils {
    private static Logger log = LoggerFactory.getLogger(StringUtils.class);

    public static String buildZeroLeadingNumber(int number, int len) {
        String numberStr = "" + number;
        if (numberStr.length() >= len) {
            return numberStr;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len - numberStr.length(); i++) {
            sb.append('0');
        }

        return sb.append(numberStr).toString();
    }

    /**
     * 将对象数组已（N-1）个separator连接
     *
     * @param objs
     *        对象数组
     * @param toStringMethod
     *        方法名称，将使用该方法反射地获取对象字符串。若为null则使用toString()方法
     * @param separator
     *        分隔符
     * @return
     */
    public static String joinObjectsAsString(Object[] objs,
            String toStringMethod, String separator) {
        if (toStringMethod == null) {
            return org.apache.commons.lang3.StringUtils.join(objs, separator);
        }

        String[] strings = new String[objs.length];

        for (int i = 0; i < objs.length; i++) {
            Object o = objs[i];
            try {
                strings[i] = ""
                    + o.getClass().getMethod(toStringMethod, (Class<?>[]) null)
                        .invoke(o, (Object[]) null);
            } catch (Exception e) {
                StringUtils.log.warn("Cannot join objects: " + e.getMessage());
            }
        }

        return org.apache.commons.lang3.StringUtils.join(strings, separator);
    }

    /**
     * 按照长度裁剪字符串。<code>splitStringByLength("abcdefg",2)</code> 返回的字符串数组为
     * <code>{"cdefg","ab"}</code>
     *
     * @param original
     *        原始字符串
     * @param len
     *        裁剪长度，若<code>len>original.length()</code>则按
     *        <code>original.length()</code>截取
     * @return 字符串数组，其中[0]为裁剪后的字符串，[1]为被裁剪的字符串
     */
    public static CharSequence[] splitStringByLength(CharSequence original,
            int len) {
        len = len > original.length() ? original.length() : len;

        return new CharSequence[] {
            original.subSequence(len, original.length()),
            original.subSequence(0, len) };
    }

    public static String buildComparison(String s) {
        if ("eq".equals(s)) {
            return "=";
        } else if ("gt".equals(s)) {
            return ">";
        } else if ("lt".equals(s)) {
            return "<";
        }
        return "";
    }

}
