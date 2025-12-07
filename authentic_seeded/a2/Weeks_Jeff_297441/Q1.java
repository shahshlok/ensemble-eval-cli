import java.util.*;
public class Q1{
public static void main(String[]x){
Scanner n=new Scanner(System.in);
System.out.print("Enter 5 integers: ");
int y=0;
for(int i=0;i<5;i++){
int z=n.nextInt();
if(z%2==0)y+=z;
}
System.out.println("Sum of even numbers: "+y);
}
}