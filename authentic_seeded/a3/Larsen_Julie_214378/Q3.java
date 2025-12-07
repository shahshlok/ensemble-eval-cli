import java.util.*;
public class Q3{
 public static void main(String[]a){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter text: ");
  String x="";
  if(s.hasNextLine())x=s.nextLine();
  if(x!=null)x.toUpperCase();
  if(x!=null)x.replace(" ","_");
  System.out.print("Result: ");
  if(x!=null)System.out.print(x);
 }
}