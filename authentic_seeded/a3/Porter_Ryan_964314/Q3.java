import java.util.Scanner ;

public class DistanceBetweenPoints {

    public static void main ( String[] args ) {

        Scanner input = new Scanner ( System.in ) ;   // create scanner

        System.out.print ( "Enter x1 and y1: " ) ;    // prompt first point
        double x1 = input.nextDouble ( ) ;
        double y1 = input.nextDouble ( ) ;

        System.out.print ( "Enter x2 and y2: " ) ;    // prompt second point
        double x2 = input.nextDouble ( ) ;
        double y2 = input.nextDouble ( ) ;

        double distance = Math.sqrt ( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) ) ;   // distance formula

        System.out.println ( "The distance of the two points is " + distance ) ;  // output result

        input.close ( ) ;
    }

}
