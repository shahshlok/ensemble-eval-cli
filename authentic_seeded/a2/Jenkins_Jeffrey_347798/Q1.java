import java.util.Scanner ;  // import Scanner 

public class Main {  // main class 
    public static void main ( String[] args ) {  // main method 
        Scanner input = new Scanner ( System.in ) ;  // create Scanner 

        System.out.print ( "Enter v0, v1, and t: " ) ;  // prompt user mid-line 
        double v0 = input.nextDouble ( ) ;  // starting velocity 
        double v1 = input.nextDouble ( ) ;  // final velocity 
        double t = input.nextDouble ( ) ;  // time 

        double acceleration = ( v1 - v0 ) / t ;  // compute acceleration 

        System.out.println ( "The average acceleration is " + acceleration ) ;  // display result 
    }  // end main 
}  // end class
