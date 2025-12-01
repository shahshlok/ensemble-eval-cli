// Name: Luke Flores
// Student ID: 200151
// Assignment 2

import java.util.Scanner;

public class Q2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the driving distance in miles: ");
		double distance = sc.nextInt();
		System.out.print("Enter miles per gallon: ");
		double milesPerGallon = sc.nextInt();
		System.out.print("Enter price in $ per gallon: ");
		double price = sc.nextInt();
		double cost = (distance / milesPerGallon) * price;
		System.out.println("The cost of driving is $" + cost);
	}
}