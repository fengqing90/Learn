package 数组;

import java.util.Scanner;
public class ArrayMin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine();
        if (string.length() == 0) {
            System.out.print("您输入的数据为空");
        }
//  char[] ch=string.toCharArray();
//  for(int i=0;i<string.length();i++){
//      if(!Character.isDigit(ch[i])&&ch[i]!=0){
//          System.out.print("输入的数据中含有非数字");
//          return;
//      }
//  }
        for (int i = 0; i < string.length(); i++) {
            char chAt = string.charAt(i);
            if (!Character.isDigit(chAt) && chAt != ' ') {
                System.out.print("输入的数据中含有非数字");
                return;
        }
        }

        String[] str = string.split("\\s+");
        int[] in = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            in[i] = Integer.valueOf(str[i]);
        }
        int min = in[0];
        for (int element : in) {
        if(min>element){
            min=element;
        }    
    }
        System.out.println("最小数为" + min);
    }
}