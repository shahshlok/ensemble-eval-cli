import java.util.*;
public class Main{
public static void main(String[]a){
Scanner s=new Scanner(System.in);
double x1,y1,x2,y2,d;
System.out.print("Enter x1 and y1: ");
x1=s.nextDouble();
y1=s.nextDouble();
System.out.print("Enter x2 and y2: ");
x2=s.nextDouble();
y2=s.nextDouble();
d=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
System.out.println("The distance of the two points is "+d);
}
}
