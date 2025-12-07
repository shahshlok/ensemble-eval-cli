import java.util.*;
public class Q1{
 public static void main(String[]a){
  Scanner x=new Scanner(System.in);
  System.out.print("Enter size: ");
  int n=x.nextInt();
  int[] y=new int[n];
  System.out.print("Enter elements: ");
  for(int i=1;i<=n;i++)y[i]=x.nextInt();
  System.out.print("Enter target: ");
  int t=x.nextInt();
  int i=1;
  for(;i<=n;i++)if(y[i]==t)break;
  if(i==n+1)System.out.println("Found at index: -1");
  else System.out.println("Found at index: "+i);
 }
}