import java.util.*;
public class Q2{
 public static void main(String[]a){
  Scanner x=new Scanner(System.in);
  System.out.print("Enter number of students: ");
  int n=0;
  if(x.hasNextInt())n=x.nextInt();
  if(n<0)n=0;
  String[]y=new String[n];
  int[]z=new int[n];
  System.out.print("Enter names: ");
  int i=0;
  while(i<n){
   if(x.hasNext())y[i]=x.next();
   else y[i]="";
   i++;
  }
  System.out.print("Enter scores: ");
  i=0;
  while(i<n){
   if(x.hasNextInt())z[i]=x.nextInt();
   else z[i]=0;
   i++;
  }
  int p=0;
  if(n>0){
   int[]t=z;
   if(t!=null)Arrays.sort(t);
   p=n-1;
  }
  String r="";
  int q=0;
  if(n>0){
   r=y[p];
   q=z[p];
  }
  System.out.print("Top student: ");
  System.out.print(r);
  System.out.print(" (");
  System.out.print(q);
  System.out.print(")");
 }
}