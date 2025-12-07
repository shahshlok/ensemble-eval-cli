import java.util.*;
public class Q2{
 public static void main(String[]x){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter number of students: ");
  int n=s.nextInt();
  String[] a=new String[n];
  int[] b=new int[n];
  System.out.print("Enter names: ");
  for(int i=0;i<n;i++)a[i]=s.next();
  System.out.print("Enter scores: ");
  for(int i=0;i<n;i++)b[i]=s.nextInt();
  Arrays.sort(b);
  System.out.println("Top student: "+a[n-1]+" ("+b[n-1]+")");
 }
}