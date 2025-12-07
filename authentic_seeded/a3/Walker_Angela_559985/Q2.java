import java.util.*;
public class Q2{
 public static void main(String[]z){
  Scanner x=new Scanner(System.in);
  System.out.print("Enter number of students: ");
  int n=x.nextInt(),i,j,a,b;
  String[] y=new String[n];
  int[] s=new int[n];
  System.out.print("Enter names: ");
  for(i=0;i<n;i++)y[i]=x.next();
  System.out.print("Enter scores: ");
  for(i=0;i<n;i++)s[i]=x.nextInt();
  for(i=0;i<n;i++)for(j=i+1;j<n;j++){a=s[i];b=s[j];if(a>b){s[i]=b;s[j]=a;String t=y[i];y[i]=y[j];y[j]=t;}}
  System.out.println("Top student: "+y[n-1]+" ("+s[n-1]+")");
 }
}