import java.util.Scanner;
public class Q4 {
    public static void main(String[] args) {
        Scanner x=new Scanner(System.in);
        System.out.print("Enter height: ");
        int n=0;
        if(x!=null)n=x.nextInt();
        int y=0;
        int z=0;
        if(n>0){
            y=1;
            while(y<=n){
                z=0;
                if(z<=y){
                    while(z<y){
                        System.out.print("*");
                        z=z+1;
                    }
                }
                System.out.println();
                y=y+1;
            }
        }
    }
}