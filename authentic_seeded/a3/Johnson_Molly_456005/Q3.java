import java.util.*;
public class Q3{
 public static void main(String[]x){
  Scanner n=new Scanner(System.in);
  System.out.print("Enter text: ");
  String a=n.nextLine();
  String b=a.toUpperCase();
  String c=b.replace(" ","_");
  System.out.println("Result: "+c);
 }
}