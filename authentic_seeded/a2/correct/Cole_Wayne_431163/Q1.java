import java.util.*;
public class Q1{
public static void main(String[] a){
Scanner x=new Scanner(System.in);
System.out.print("Enter 5 integers: ");
int y=0,n=0;
while(y<5){int z=x.nextInt();if(z%2==0)n+=z;y++;}
System.out.println("Sum of even numbers: "+n);
}
}