package com.student;
// Name: Noah Anderson

// Student ID: 200113
// Assignment 2

import java.util.Scanner;

public class Q3 {
   public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter x1 and y1: ");
      double x1 = scan.nextInt();
      double y1 = scan.nextInt();
      System.out.print("Enter x2 and y2: ");
      double x2 = scan.nextInt();
      double y2 = scan.nextInt(); // initialize variables
      double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
      System.out.println("The distance of the two points is " + distance);
   }
}