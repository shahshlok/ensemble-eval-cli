import java.util.*;
public class Q1{
 public static void main(String[]x){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter 5 integers: ");
  int a=0,b=0,c=0,d=0,e=0;
  a=s.nextInt();
  b=s.nextInt();
  c=s.nextInt();
  d=s.nextInt();
  e=s.nextInt();
  int y=0;
  int n=2;
  if(a%n==0)y+=a;
  if(b%n==0)y+=b;
  if(c%n==0)y+=c;
  if(d%n==0)y+=d;
  if(e%n==0)y+=e;
  System.out.println("Sum of even numbers: "+y);
 }
}