import java.util.*;
public class Q3{
public static void main(String[]a){
Scanner x=new Scanner(System.in);
System.out.print("Enter text: ");
String y="";
if(x.hasNextLine())y=x.nextLine();else y="";
String n="";
if(y!=null)n=y.toUpperCase();else n="";
String z="";
z=n.replace(" ","_");
System.out.print("Result: ");
System.out.print(z);
}
}