package 其它;

import java.awt.Color;

public class Cat {
    private String name;
    private Color color;
    private int age;
    private double weight;

    public Cat(String name, Color color, int age, double weight) {
        this.name = name;
        this.color = color;
        this.age = age;
        this.weight = weight;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("这只猫");
        sb.append("名字叫" + this.name);
        sb.append("颜色是" + this.color);
        sb.append("年龄是" + this.age);
        sb.append("体重是" + this.weight);
        return sb.toString();
    }
//public void AA(){
//  StringBuffer sb=new StringBuffer("这只猫");
//  sb.append("名字叫"+name);
//  sb.append("颜色是"+color);
//  System.out.println(sb);
//}
}

class CatTest {
    public static void main(String[] args) {
        Cat cat1 = new Cat("小花猫", Color.BLACK, 1, 10.1);
        System.out.println(cat1);
    }
}