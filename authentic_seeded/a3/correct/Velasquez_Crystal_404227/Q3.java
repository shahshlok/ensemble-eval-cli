import java.util.*;
public class Q3{
public static void main(String[]a){
Scanner x=new Scanner(System.in);
System.out.print("Enter text: ");
String y="";
if(x.hasNextLine())y=x.nextLine();
String n="";
n=y.toUpperCase();
String z="";
z=n.replace(' ','_');
System.out.println("Result: "+z);
}
}