// Perfect Student 100 - Perfect implementation
public class SumCalculator {
    /**
     * Computes the sum of integers from 1 to 100 using a mathematical formula.
     * This is more efficient than iteration.
     */
    public static void main(String[] args) {
        // Using the formula: sum = n * (n + 1) / 2
        int n = 100;
        int totalSum = n * (n + 1) / 2;
        
        System.out.println("The sum of numbers from 1 to 100 is: " + totalSum);
    }
}