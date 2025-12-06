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

        double cost = ( distance / mpg ) * price ;

        System.out.println ( "The cost of driving is $" + cost ) ;

        // I need a random number between 1 and 100  
        int answer = Math.random ( ) ; // this should give a random number  

        System.out.println ( "Random answer (1-100): " + answer ) ;
    }

}
