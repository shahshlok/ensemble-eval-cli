import java.util.Scanner;
public class Acceleration{
public static void main(String[]args){
Scanner scanner=new Scanner(System.in);
System.out.print("Enter v0, v1, and t: ");
String v0Str=scanner.next();
String v1Str=scanner.next();
String tStr=scanner.next();
double v0=Double.parseDouble(v0Str);
double v1=Double.parseDouble(v1Str);
double t=Double.parseDouble(tStr);
double a=(v1-v0)/t;
System.out.println("The average acceleration is "+a);
String num=scanner.next();
double sum=0;
sum+=num;
}
}
