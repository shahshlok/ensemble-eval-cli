import java.util.*;
public class Main{
public static void main(String[]args){
Scanner s=new Scanner(System.in);
System.out.print("Enter x1 and y1: ");
double x1=s.nextDouble(),y1=s.nextDouble();
System.out.print("Enter x2 and y2: ");
double x2=s.nextDouble(),y2=s.nextDouble();
double d=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
System.out.println("The distance of the two points is "+d);
int grade=95;
if(grade>=90)System.out.println("A");
if(grade>=80)System.out.println("B");
if(grade>=70)System.out.println("C");
if(grade>=60)System.out.println("D");
if(grade<60)System.out.println("F");
}
}
