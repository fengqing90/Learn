package 数组;

import java.lang.reflect.Array;
import java.util.Arrays;

public class IncreaseArray {

    public static Object IncreaseArrayTest(Object array) {
        Class cl = array.getClass();
        if (cl.isArray()) {
            Class comtype = cl.getComponentType();
            int length = Array.getLength(array);
            Object newArray = Array.newInstance(comtype, length + 3);
            System.arraycopy(array, 0, newArray, 0, length);
            return newArray;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        int[] array1 = new int[10];
        Arrays.fill(array1, 9);
        System.out
            .println("长度为" + array1.length + "元素为" + Arrays.toString(array1));
        int[] array2 = (int[]) IncreaseArray.IncreaseArrayTest(array1);
        System.out
            .println("长度为" + array2.length + "元素为" + Arrays.toString(array2));
    }
}