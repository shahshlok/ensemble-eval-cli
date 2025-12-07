import java.util.*;
public class Q3{
    public static void main(String[]x){
        Scanner n=new Scanner(System.in);
        System.out.print("Enter text: ");
        String y=n.nextLine();
        y=y.toUpperCase().replace(" ","_");
        System.out.println("Result: "+y);
    }
}