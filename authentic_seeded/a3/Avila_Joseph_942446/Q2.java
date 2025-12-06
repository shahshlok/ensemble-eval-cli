import java.util.Scanner ;

public class RoadTripCostQ2 {

    public static void main ( String [] args ) {

        Scanner input = new Scanner ( System.in ) ;

        System.out.print ( "Enter the driving distance in miles: " ) ;
        double distance = input.nextDouble ( ) ;

        System.out.print ( "Enter miles per gallon: " ) ;
        double mpg = input.nextDouble ( ) ;

        System.out.print ( "Enter price in $ per gallon: " ) ;
        double price = input.nextDouble ( ) ;

        double cost = ( distance / mpg ) * price ;  // calculate cost

        System.out.println ( "The cost of driving is $" + cost ) ;

        double answer = cost ;
        double guess = cost ;

        if ( guess = answer ) {  // check if guess equals answer
            System.out.println ( "Values match" ) ;
        }

        input.close ( ) ;
    }

}
