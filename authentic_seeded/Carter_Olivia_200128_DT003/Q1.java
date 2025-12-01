// Name: Olivia Carter
// Student ID: 200128
// Assignment 2

import java.util.Scanner;

public class Q1 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter v0, v1, and t: ");
		double vStart = scan.nextInt();
		double vEnd = scan.nextInt();
		double t = scan.nextInt();
		double a = (vEnd - vStart) / t;
		System.out.println("The average acceleration is " + a);
	}
}