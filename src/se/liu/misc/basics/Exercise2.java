package se.liu.alfsj019.lab1;

import javax.swing.*;

public class Exercise2
{
    public static int sumFor(int min, int max) {

        int sum = 0;

        for (int i = 0; i <= max-min; i++) {
            sum += i + min;
        }

        return sum;
    }

    public static int sumWhile(int min, int max) {

        int sum = 0;
        int i = min;

        while (i <= max) {
            sum += i;
            i++;
        }

        return sum;
    }



    public static void main(String[] args) {

        final int min = 20;
        final int max = 30;

        String input =
                JOptionPane.showInputDialog("Please input a value");

        switch (input) {
            case "for":
                int minMaxSum = sumFor(min, max);
                System.out.println(minMaxSum);
                break;
            case "while":
                int whileSum = sumWhile(min, max);
                System.out.println(whileSum);
                break;
            default:
                System.out.println("idk");
        }
    }
}