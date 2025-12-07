import java.util.*;
public class Q4{
 public static void main(String[]x){
  Scanner n=new Scanner(System.in);
  System.out.print("Enter size: ");
  int a=n.nextInt();
  int[] b=new int[a];
  System.out.print("Enter elements: ");
  int c=0;
  while(c<a){b[c]=n.nextInt();c++;}
  if(a>0){
   int d=b[a-1];
   int e=a-2;
   while(e>=0){b[e+1]=b[e];e--;}
   b[0]=d;
  }
  System.out.print("Shifted: ");
  int f=0;
  while(f<a){System.out.print(b[f]+(f==a-1?"":" "));f++;}
 }
}