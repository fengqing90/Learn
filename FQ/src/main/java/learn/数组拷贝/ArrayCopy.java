package learn.数组拷贝;

import java.util.Arrays;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/13 17:48
 */
public class ArrayCopy {

    public static void main(String[] args) {
        int[] src = {3, 5, 0, 0, 6};
        int[] dest = {1, 2, 6};
        int[] r = new int[dest.length + src.length];

        // 表示将nums2数组从下标0位置开始，拷贝到nums1数组中，从下标0位置开始，长度为len2+1
        
        System.arraycopy(src, 0, r, 0, src.length);
        System.arraycopy(dest, 0, r, src.length, dest.length);
        System.out.println(Arrays.toString(r));

    }
}
