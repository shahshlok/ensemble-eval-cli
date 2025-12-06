import java.util.Scanner ;

public class AccelerationQ1
{
    public static void main ( String[] args )
    {
        Scanner input = new Scanner ( System.in ) ;

        System.out.print ( "Enter v0, v1, and t: " ) ;
        int v0 = input.nextInt ( ) ; // starting velocity
        int v1 = input.nextInt ( ) ; // final velocity
        int t = input.nextInt ( ) ;  // time

        double a = (double)((v1 - v0) / t) ; // average acceleration

        System.out.println ( "The average acceleration is " + a ) ;
    }
}
