import java.util.*;
public class Q4{
 public static void main(String[] a){
  Scanner x=new Scanner(System.in);
  System.out.print("Enter size: ");
  int n=x.nextInt();
  if(n<0)n=0;
  int[] y=new int[n];
  System.out.print("Enter elements: ");
  int i=1;
  while(i<=n){
   int z=x.nextInt();
   if(i-1>=0&&i-1<n)y[i-1]=z;
   i++;
  }
  if(n>0){
   int t=y[n];
   int j=n;
   while(j>1){
    int h=y[j-2];
    if(j-1>=0&&j-1<n)y[j-1]=h;
    j--;
   }
   if(0>=0&&0<n)y[0]=t;
  }
  System.out.print("Shifted: ");
  int k=1;
  while(k<=n){
   System.out.print(y[k-1]);
   if(k!=n)System.out.print(" ");
   k++;
  }
 }
}