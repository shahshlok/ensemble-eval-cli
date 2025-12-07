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
  for(int i=0;i<5;i++){
   int sum=0;
   int z=0;
   if(i==0)z=a;
   if(i==1)z=b;
   if(i==2)z=c;
   if(i==3)z=d;
   if(i==4)z=e;
   if(z%n==0)sum+=z;
   y=sum;
  }
  System.out.println("Sum of even numbers: "+y);
 }
}