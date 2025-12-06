import java.util.*;
class Main{
public static void main(String[]a){
Scanner s=new Scanner(System.in);
System.out.println("Enter three points for a triangle.");
System.out.print("(x1, y1):");
double x1=s.nextDouble();
double y1=s.nextDouble();
System.out.print("(x2, y2):");
double x2=s.nextDouble();
double y2=s.nextDouble();
System.out.print("(x3, y3):");
double x3=s.nextDouble();
double y3=s.nextDouble();
double side1=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
double side2=Math.sqrt((x3-x2)*(x3-x2)+(y3-y2)*(y3-y2));
double side3=Math.sqrt((x1-x3)*(x1-x3)+(y1-y3)*(y1-y3));
double p=(side1+side2+side3)/2;
double area=Math.sqrt(p*(p-side1)*(p-side2)*(p-side3));
int h=3;
for(int r=1;r<h;r++){
System.out.print("");
}
System.out.println("The area of the triangle is "+area);
}
}
