package 数组;

public class ArraySwap {
    public static void main(String[] args) {
        String arr[][] = new String[][] { { "一", "二", "三" }, { "四", "五" },
            { "六", "七", "八", "九" } };
        System.out.print("【交换前】-的数组是:");
        ArraySwap.printArray(arr);

        System.out.println();
        System.out.print("【交换后】-的数组是:");
        String arr2[][] = new String[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = new String[arr[i].length];
            for (int j = 0; j < arr[i].length; j++) {
                arr2[i][j] = arr[i][j];

            }
        }
        ArraySwap.printArray(arr2);
    }

    public static void printArray(String[][] x) {
        for (String a[] : x) {
            System.out.println();
            for (String b : a) {
                System.out.print(b);
            }
        }
        System.out.println();
    }
}