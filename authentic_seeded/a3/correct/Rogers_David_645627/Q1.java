import java.util.*;
public class Q1{
 public static void main(String[]x){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter size: ");
  int n=s.nextInt();
  int[]a=new int[n];
  System.out.print("Enter elements: ");
  int i=0;
  while(i<n){a[i]=s.nextInt();i++;}
  System.out.print("Enter target: ");
  int t=s.nextInt();
  int y=-1;
  i=0;
  while(i<n&&y==-1){if(a[i]==t)y=i;i++;}
  System.out.print("Found at index: ");
  System.out.print(y);
 }
}