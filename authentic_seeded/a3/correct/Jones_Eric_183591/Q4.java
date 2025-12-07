import java.util.*;
public class Q4{
 public static void main(String[]x){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter size: ");
  int n=s.nextInt();
  int[] a=new int[n];
  System.out.print("Enter elements: ");
  int i=0;
  while(i<n){a[i]=s.nextInt();i++;}
  if(n>0){
   int b=a[n-1];
   i=n-1;
   while(i>0){a[i]=a[i-1];i--;}
   a[0]=b;
  }
  System.out.print("Shifted: ");
  i=0;
  while(i<n){System.out.print(a[i]+(i<n-1?" ":""));i++;}
 }
}