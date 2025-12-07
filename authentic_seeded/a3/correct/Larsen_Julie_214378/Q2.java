import java.util.*;
public class Q2{
    public static void main(String[] a){
        Scanner s=new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n=s.nextInt();
        if(n<0)n=0;
        String[] x=new String[n];
        int[] y=new int[n];
        if(n>0)System.out.print("Enter names: ");
        for(int i=0;i<n;i++){
            String t=s.next();
            x[i]=t;
        }
        if(n>0)System.out.print("Enter scores: ");
        for(int i=0;i<n;i++){
            int t=s.nextInt();
            y[i]=t;
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<n-1;j++){
                int t1=y[j];
                int t2=y[j+1];
                if(t1>t2){
                    int th=t1;
                    y[j]=t2;
                    y[j+1]=th;
                    String ts=x[j];
                    x[j]=x[j+1];
                    x[j+1]=ts;
                }
            }
        }
        if(n>0){
            int i=n-1;
            String tn=x[i];
            int ts=y[i];
            System.out.println("Top student: "+tn+" ("+ts+")");
        }
        s.close();
    }
}