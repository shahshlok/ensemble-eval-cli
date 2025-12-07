import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Create an array to store student names
        String[] studentNames = new String[numberOfStudents];

        // Create an array to store student scores
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter all the names
        System.out.print("Enter names: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each name and store it in the names array
            studentNames[index] = userInputScanner.next();
        }

        // Prompt the user to enter all the scores
        System.out.print("Enter scores: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each score and store it in the scores array
            studentScores[index] = userInputScanner.nextInt();
        }

        // Now we will sort the parallel arrays by scores in ascending order
        // We will use a simple bubble sort to keep it easy to understand

        // Outer loop goes through each element
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            // Inner loop compares adjacent elements
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {

                // Declare intermediate math variables to make the comparison logic clear
                int a = studentScores[innerIndex];         // current score
                int b = studentScores[innerIndex + 1];     // next score
                int c = a - b;                             // difference between current and next

                // If the current score is greater than the next score, we need to swap
                if (c > 0) {
                    // Swap scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Swap the corresponding names to keep arrays parallel
                    String temporaryName = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryName;
                }
            }
        }

        // After sorting in ascending order, the student with the highest score
        // will be at the last index of the arrays
        int topStudentIndex = numberOfStudents - 1;

        String topStudentName = studentNames[topStudentIndex];
        int topStudentScore = studentScores[topStudentIndex];

        // Print the top student in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Close the scanner to free resources
        userInputScanner.close();
    }
}