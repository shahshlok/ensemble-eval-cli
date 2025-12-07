import java.util.*;
public class Q3{
public static void main(String[]a){
Scanner x=new Scanner(System.in);
System.out.print("Enter grade: ");
int n=x.nextInt();
String y;
if(n>=90)y="A";
else
if(n>=80)
if(n>=70)
if(n>=60)y="D";
else y="F";
else y="C";
else y="B";
System.out.println("Letter grade: "+y);
}
}