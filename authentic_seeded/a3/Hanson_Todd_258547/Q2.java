import java.util.Scanner;
import java.util.Arrays;

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

        // Now we will sort the scores array in ascending order
        // Since each name corresponds to its score, the relationship is maintained conceptually
        Arrays.sort(studentScores);

        // After sorting in ascending order, the student with the highest score
        // will be at the last index of the scores array
        int topStudentIndex = numberOfStudents - 1;

        // Declare intermediate math variables to emphasize the position calculation
        int a = numberOfStudents;          // total number of students
        int b = 1;                         // we subtract one to get the last index
        int c = a - b;                     // this should be equal to topStudentIndex

        // Use the calculated index (they are mathematically the same)
        int topStudentScore = studentScores[c];
        String topStudentName = studentNames[c];

        // Print the top student in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Close the scanner to free resources
        userInputScanner.close();
    }
}