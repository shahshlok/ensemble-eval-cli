import java.util.*;
public class Q4{
 public static void main(String[]x){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter height: ");
  int n=s.nextInt(),i=1,j=1,a=0,b=0,c=0;
  for(i=1;i<=n;i++){
   a=i;b=0;c=0;
   for(j=1;j<=a;j++){
    System.out.print("*");
    i=0;
   }
   System.out.println();
  }
 }
}