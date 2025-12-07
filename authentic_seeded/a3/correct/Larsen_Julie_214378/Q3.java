import java.util.*;
public class Q3{
 public static void main(String[]a){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter text: ");
  String x="";
  if(s.hasNextLine())x=s.nextLine();
  String y="";
  if(x!=null)y=x.toUpperCase();
  String n="";
  if(y!=null)n=y.replace(" ","_");
  System.out.print("Result: ");
  if(n!=null)System.out.print(n);
 }
}