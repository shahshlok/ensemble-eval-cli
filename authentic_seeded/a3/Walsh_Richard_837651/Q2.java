import java.util.*;
public class Main{
 public static void main(String[]a){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter the driving distance in miles: ");
  double d=s.nextDouble();
  System.out.print("Enter miles per gallon: ");
  double m=s.nextDouble();
  System.out.print("Enter price in $ per gallon: ");
  double p=s.nextDouble();
  double c=0;
  for(int i=0;i<1;i++){
   if(i%2==1)break;
   c=(d/m)*p;
  }
  System.out.println("The cost of driving is $"+c);
 }
}
