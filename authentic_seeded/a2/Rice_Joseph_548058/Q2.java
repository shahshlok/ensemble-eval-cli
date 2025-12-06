import java.util.*;
public class Main{
public static void main(String[]args){
Scanner s=new Scanner(System.in);
System.out.print("Enter the driving distance in miles: ");
int distance=s.nextInt();
System.out.print("Enter miles per gallon: ");
int mpg=s.nextInt();
System.out.print("Enter price in $ per gallon: ");
int price=s.nextInt();
double cost=distance/mpg*price;
System.out.println("The cost of driving is $"+cost);
}
}
