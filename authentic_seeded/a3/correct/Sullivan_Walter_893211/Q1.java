import java.util.*;
public class Q1{
    public static void main(String[] a){
        Scanner s=new Scanner(System.in);
        System.out.print("Enter size: ");
        int n=s.nextInt();
        if(n<0)n=0;
        int[] x=new int[n];
        System.out.print("Enter elements: ");
        int i=0;
        while(i<n){
            int y=s.nextInt();
            x[i]=y;
            i++;
        }
        System.out.print("Enter target: ");
        int t=s.nextInt();
        int y=-1;
        i=0;
        while(i<n){
            int z=x[i];
            if(z==t){
                if(y==-1)y=i;
            }
            i++;
        }
        if(y!=-1){
            System.out.print("Found at index: ");
            System.out.print(y);
        }else{
            System.out.print(-1);
        }
    }
}