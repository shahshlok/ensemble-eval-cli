import java.util.Scanner;
public class Main{
public static void main(String[]a){
Scanner s=new Scanner(System.in);
double x=0,y=0;
System.out.println("Enter three points for a triangle.");
System.out.print("(x1, y1):");
x=s.nextDouble();
y=s.nextDouble();
System.out.print("(x2, y2):");
x=s.nextDouble();
y=s.nextDouble();
System.out.print("(x3, y3):");
x=s.nextDouble();
y=s.nextDouble();
double x1=0,y1=0,x2=0,y2=0,x3=0,y3=0;
double side1=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
double side2=Math.sqrt((x3-x2)*(x3-x2)+(y3-y2)*(y3-y2));
double side3=Math.sqrt((x1-x3)*(x1-x3)+(y1-y3)*(y1-y3));
double p=(side1+side2+side3)/2;
double area=Math.sqrt(p*(p-side1)*(p-side2)*(p-side3));
System.out.println("The area of the triangle is "+area);
}
}
