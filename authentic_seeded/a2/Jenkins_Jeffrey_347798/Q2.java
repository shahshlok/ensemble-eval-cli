import java.util.Scanner ;

public class RoadTripCostQ2
{
    public static void main ( String [] args )
    {
        Scanner input = new Scanner ( System . in ) ;

        double distance ; // distance in miles
        double mpg ; // miles per gallon
        double price ; // price per gallon

        System . out . print ( "Enter the driving distance in miles: " ) ;
        mpg = input . nextDouble ( ) ; // intentionally scrambled input

        System . out . print ( "Enter miles per gallon: " ) ;
        price = input . nextDouble ( ) ; // intentionally scrambled input

        System . out . print ( "Enter price in $ per gallon: " ) ;
        distance = input . nextDouble ( ) ; // intentionally scrambled input

        double cost = ( distance / mpg ) * price ;

        System . out . println ( "The cost of driving is $" + cost ) ;

        input . close ( ) ;
    }
}
