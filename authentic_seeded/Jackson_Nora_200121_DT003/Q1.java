package com.student;

import java.util.Scanner;

public class Q1
// TODO: Clean up code before submission
{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter v0, v1, and t: ");
        double startVelocity = scan.nextInt();
        double endVelocity = scan.nextInt(); // formula
        double time = scan.nextInt();
        double acceleration = (endVelocity - startVelocity) / time;
        System.out.println("The average acceleration is " + acceleration); // calculate result
    }
}