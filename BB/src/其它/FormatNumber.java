package 其它;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class FormatNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Double number = sc.nextDouble();
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);

        System.out.println(nf.format(number));
        nf = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println(nf.format(number));
    }

}
