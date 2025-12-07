import java.util.*;
public class Q1{
public static void main(String[]a){
Scanner x=new Scanner(System.in);
System.out.print("Enter size: ");
int n=0;
if(x.hasNextInt())n=x.nextInt();
int[] y=new int[n];
System.out.print("Enter elements: ");
int i=0;
while(i<n){
int t=0;
if(x.hasNextInt())t=x.nextInt();
y[i]=t;
i++;
}
System.out.print("Enter target: ");
int z=0;
if(x.hasNextInt())z=x.nextInt();
int r=-1;
i=0;
while(i<n){
int v=y[i];
if(v==z){
if(r==-1)r=i;
}
i++;
}
System.out.print("Found at index: ");
System.out.print(r);
}
}