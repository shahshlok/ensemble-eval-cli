import java.util.Scanner;
public class Q3{
public static void main(String[]a){
Scanner s=new Scanner(System.in);
System.out.print("Enter grade: ");
int x=0;
if(s.hasNextInt())x=s.nextInt();
int y=x;
String n="";
if(y>=90&&y<=100)n="A";
else if(y>=80&&y<90)n="B";
else if(y>=70&&y<80)n="C";
else if(y>=60&&y<70)n="D";
else if(y<60&&y>=0)n="F";
System.out.println("Letter grade: "+n);
}
}