import java.util.Scanner ;

public class DistanceBetweenPointsQ3 {

    public static void main ( String [] args ) {

        Scanner input = new Scanner ( System.in ) ;

        System.out.print ( "Enter x1 and y1: " ) ;
        double x1 = input.nextDouble ( ) ; double y1 = input.nextDouble ( ) ; // first point

        System.out.print ( "Enter x2 and y2: " ) ;
        double x2 = input.nextDouble ( ) ; double y2 = input.nextDouble ( ) ; // second point

        double distance = Math.sqrt ( ( x2 - x1 ) * ( x2 - x1 ) + ( y2 - y1 ) * ( y2 - y1 ) ) ; // formula

        System.out.println ( "The distance of the two points is " + distance ) ;

        input.close ( ) ;
    }

}
