// Name: Avery Nelson
// Student ID: 200123
// Assignment 2

import java.util.Scanner;
public class Q2 {
    public static void main(String[] args)
    {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the driving distance in miles: ");
        double distance=100.0;
        System.out.print("Enter miles per gallon: ");
        double milesPerGallon=25.0;
        System.out.print("Enter price in $ per gallon: ");
        double price=3.50;
        double cost=(distance/milesPerGallon)*price;
        System.out.println("The cost of driving is $" + cost);
    }
}