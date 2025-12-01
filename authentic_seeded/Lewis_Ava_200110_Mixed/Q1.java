// Name: Ava Lewis
// Student ID: 200110
// Assignment 2

import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        double v0 = 5.5;
        double v1 = 50.9;
        double t = 4.5;
        System.out.println("DEBUG: reached here");
        double a = (v1 - v0) / t;
        System.out.println("The average acceleration is " + a);
        System.out.println("----------------");
    }
}