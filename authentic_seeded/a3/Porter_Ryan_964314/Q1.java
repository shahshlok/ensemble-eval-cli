import java.util.Scanner ;

public class Main {
    public static void main ( String[] args ) {
        Scanner input = new Scanner ( System.in ) ; // scanner

        System.out.print ( "Enter v0, v1, and t: " ) ; // prompt

        double v0 = input.nextDouble ( ) ; // starting velocity
        double v1 = input.nextDouble ( ) ; // final velocity
        double t = input.nextDouble ( ) ; // time

        double a = ( v1 - v0 ) / t ; // compute acceleration

        System.out.println ( "The average acceleration is " + a ) ; // output
    }
}
