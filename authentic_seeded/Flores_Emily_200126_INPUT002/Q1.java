// Name: Emily Flores
// Student ID: 200126
// Assignment 2

import java.util.Scanner;

public class Q1 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter v0, v1, and t: ");
		double startVelocity = s.nextDouble();
		double endVelocity = s.nextDouble();
		// double time=s.nextDouble();
		double time = 1.0; // Default value to avoid compilation error
		double acceleration = (endVelocity - startVelocity) / time;
		System.out.println("The average acceleration is " + acceleration);
	}
}