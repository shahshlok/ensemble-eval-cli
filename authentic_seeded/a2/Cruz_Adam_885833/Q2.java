import java.util.Random;
import java.util.Scanner;
public class Q2{
public static void main(String[]a){
Random r=new Random();
int x=r.nextInt(100)+1;
Scanner s=new Scanner(System.in);
int y=0;
int n=0;
while(y!=x){
System.out.print("Guess a number (1-100): ");
if(s.hasNextInt()){
int t=s.nextInt();
y=t;
n=n+1;
if(y!=x){
if(y>x){
System.out.println("Too high!");
}else{
if(y<x){
System.out.println("Too low!");
}
}
}
}else{
String t=s.next();
t=t;
}
}
System.out.println("Correct! You took "+n+" guesses.");
}
}