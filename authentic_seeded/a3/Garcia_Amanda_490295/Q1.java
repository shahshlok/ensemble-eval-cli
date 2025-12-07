import java.util.*;
public class Q1{
public static void main(String[]a){
Scanner s=new Scanner(System.in);
System.out.print("Enter size: ");
int n=s.nextInt();
int[]x=new int[n];
System.out.print("Enter elements: ");
for(int i=0;i<n;i++)x[i]=s.nextInt();
System.out.print("Enter target: ");
int y=s.nextInt();
int z=-1;
for(int i=0;i<n;i++)if(x[i]==y){z=i;break;}
if(z==-1)System.out.println("-1");
else System.out.println("Found at index: "+z);
}
}