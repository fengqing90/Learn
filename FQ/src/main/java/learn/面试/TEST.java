package learn.面试;

public class TEST {


    public static void main(String[] args) {
        int a[] = { 1, -3, 2, -7, 8, 7, -2, 6, -3, -10, 9 };
        System.out.println(TEST.FindGreatestSumOfSubArray(a));
    }

    public static int FindGreatestSumOfSubArray(int[] array) {
        if (array.length == 0 || array == null) {
            return 0;
        }
        int currentSum = 0;     //存储当前连续n项的和
        int max = 0;            //存储连续子元素和的最大值
        for (int element : array) {
            //核心部分，好好理解.
            if (currentSum <= 0) {      //如过当前连续n项的和小于等于0,则没必要与后面的元素相加
                currentSum = element;      //currentSum重新赋值
            } else {
                currentSum += element;     //如果currentSum的值大于0,则继续与后面的元素相加,
            }
            if (currentSum > max) {         //每次改变currentSum的值都有与max进行比较
                max = currentSum;       //如果currentSum的值大于max,则将currentSum的值赋值给max
            }
        }
        return max;
    }
}
