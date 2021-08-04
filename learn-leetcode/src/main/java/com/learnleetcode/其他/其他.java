package com.learnleetcode.其他;

import com.learnleetcode.LeetCode;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/30 18:48
 */
public class 其他 extends LeetCode {
    public static void main(String[] args) {
        喝饮料.run();
        生兔子.run();
    }

    static class 小顶堆 {

        public static void main(String[] args) {
            int[] array = new int[] { 7, 5, 15, 3, 17, 2, 20, 24, 1, 9, 12, 8 };
            System.out.println(findNumberK(array, 5));
        }

        /**
         * 寻找第k大元素
         * 
         * @param array
         *        待调整的数组
         * @param k
         *        第几大
         * @return
         */
        public static int findNumberK(int[] array, int k) {
            //1.用前k个元素构建小顶堆
            buildHeap(array, k);
            //2.继续遍历数组，和堆顶比较
            for (int i = k; i < array.length; i++) {
                if (array[i] > array[0]) {
                    array[0] = array[i];
                    downAdjust(array, 0, k);
                }
            }
            //3.返回堆顶元素
            return array[0];
        }

        private static void buildHeap(int[] array, int length) {
            //从最后一个非叶子节点开始，依次下沉调整
            for (int i = (length - 2) / 2; i >= 0; i--) {
                downAdjust(array, i, length);
            }
        }

        /**
         * 下沉调整
         * 
         * @param array
         *        待调整的堆
         * @param index
         *        要下沉的节点
         * @param length
         *        堆的有效大小
         */
        private static void downAdjust(int[] array, int index, int length) {
            //temp保存父节点的值，用于最后的赋值
            int temp = array[index];
            int childIndex = 2 * index + 1;
            while (childIndex < length) {
                //如果有右孩子，且右孩子小于左孩子的值，则定位到右孩子
                if (childIndex + 1 < length
                    && array[childIndex + 1] < array[childIndex]) {
                    childIndex++;
                }
                //如果父节点小于任何一个孩子的值，直接跳出
                if (temp <= array[childIndex])
                    break;
                //无需真正交换，单项赋值即可
                array[index] = array[childIndex];
                index = childIndex;
                childIndex = 2 * childIndex + 1;
            }
            array[index] = temp;
        }

    }

    static class Solution extends 其他 {
        public static void main(String[] args) {
            int[] array = new int[] { 92, 5, 88, 13, 80, 11, 23, 666, 432 };
            System.out.println(new Solution().findKLargest(array, 2));
        }

        public int findKLargest(int[] nums, int k) {
            return findK(nums, k, 0, nums.length - 1);
        }

        private int findK(int[] nums, int k, int start, int end) {
            int low = start;
            int high = end;
            int temp = nums[low];// 枢轴点
            while (low < high) {
                while (low < high && temp >= nums[high]) {
                    high--;
                }
                // 遇到高位比temp小，把高位放到地位
                nums[low] = nums[high];

                while (low < high && temp <= nums[low]) {
                    low++;
                }
                // 遇到低位比temp大，把地位放到高位
                nums[high] = nums[low];
            }
            // 曲轴点归位
            nums[high] = temp;

            if (high == k - 1) {
                return temp; // 如果当前是第k大的数据直接返回
            } else if (high > k - 1) {
                return findK(nums, k, start, high - 1);
            } else {
                return findK(nums, k, high + 1, end);
            }
        }
    }

    /**
     * 如何寻找无序数组中的第K大元素？
     * 快速排序
     */
    public static class disorderSearchBin extends 其他 {

        public int quickSortOneTime(int[] array, int low, int high) { //一趟快速排序   
            int key = array[low];
            while (low < high) {
                while (key < array[high] && low < high)
                    high--;

                array[low] = array[high];

                while (key > array[low] && low < high)
                    low++;

                array[high] = array[low];
            }
            array[high] = key;
            return high;
        }

        public int Select_k(int[] array, int low, int high, int k) {
            int index;
            if (low == high)
                return array[low];
            int partition = quickSortOneTime(array, low, high);
            index = high - partition + 1;  //找到的是第几个大值
            if (index == k) {
                return array[partition];
            } else if (index < k) {//此时向左查找
                return Select_k(array, low, partition - 1, k - index);  //查找的是相对位置的值，k在左段中的下标为k-index
            } else {
                return Select_k(array, partition + 1, high, k);
            }
        }

        public static void main(String[] args) {
            int[] array = new int[] { 92, 5, 88, 13, 80 };
            int index = new disorderSearchBin().Select_k(array, 0,
                array.length - 1, 2);
            System.out.print(index);
        }

    }

    /**
     * 古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     */
    static class 生兔子 extends 其他 {
        static void run() {
            new 生兔子().生();
        }

        void 生() {
            System.out.println("第1个月的兔子对数:    1");
            System.out.println("第2个月的兔子对数:    1");
            int f1 = 1, f2 = 1, f, M = 24;
            for (int i = 3; i <= M; i++) {
                f = f2;
                f2 = f1 + f2;
                f1 = f;
                System.out.println("第" + i + "个月的兔子对数: " + f2);
            }
        }
    }

    /**
     * 喝3瓶换1瓶，n瓶可以喝多少瓶
     */
    static class 喝饮料 extends 其他 {

        static void run() {
            new 喝饮料().drink();
        }

        void drink() {
            //初始饮料总数
            int n = 20;
            //兑换次数
            int i = 0;
            while (true) {
                //喝3瓶
                n -= 3;
                //兑换1瓶
                n++;
                //兑换次数+1
                i++;
                if (n < 3) {
                    System.out.println("共喝了" + (12 + i) + "瓶");
                    break;
                }
            }
        }
    }

}
