import java.util.*;
public class Main{
public static void main(String[]a){
Scanner s=new Scanner(System.in);
System.out.print("Enter v0, v1, and t: ");
double v0=s.nextDouble();
double v1=s.nextDouble();
double t=s.nextDouble();
double n=v1-v0;
double x=0;
x+=System.out.println(n);
double r=n/t;
System.out.println("The average acceleration is "+r);
}
}
