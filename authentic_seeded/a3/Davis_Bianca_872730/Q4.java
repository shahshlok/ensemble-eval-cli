import java.util.*;
public class Q4{
 public static void main(String[] a){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter size: ");
  int n=s.nextInt();
  if(n<0)n=0;
  int[] x=new int[n];
  System.out.print("Enter elements: ");
  int i=0;
  while(i<n){
   x[i]=s.nextInt();
   i++;
  }
  System.out.print("Shifted: ");
  if(n!=0){
   int t=x[n-1];
   i=n-1;
   while(i>0){
    x[i]=x[i-1];
    i--;
   }
   x[0]=t;
   i=0;
   while(i<n){
    System.out.print(x[i]);
    if(i!=n-1)System.out.print(" ");
    i++;
   }
  }
 }
}