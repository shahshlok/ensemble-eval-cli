import java.util.Scanner;
public class Q2{
public static void main(String[]args){
Scanner input=new Scanner(System.in);
System.out.print("Enter the driving distance in miles: ");
double distance=input.nextDouble();
System.out.print("Enter miles per gallon: ");
double mpg=input.nextDouble();
System.out.print("Enter price in $ per gallon: ");
double price=input.nextDouble();
double cost=(distance/mpg)*price;
System.out.println("The cost of driving is $"+cost);
int answer=42;
System.out.print("Guess my number: ");
int guess=input.nextInt();
while(guess!=answer){
if(guess<answer){
System.out.println("Too low, try again.");
}else{
System.out.println("Too high, try again.");
}
}
}
}
