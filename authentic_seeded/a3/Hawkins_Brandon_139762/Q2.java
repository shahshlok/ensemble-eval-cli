import java.util.Scanner;
import java.util.Arrays;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 3: Create an array to store the student names
        String[] studentNames = new String[numberOfStudents];

        // Step 4: Create an array to store the student scores
        int[] studentScores = new int[numberOfStudents];

        // Step 5: Ask the user to enter all the names
        System.out.print("Enter names: ");
        // Step 6: Read each name and store it in the studentNames array
        for (int index = 0; index < numberOfStudents; index++) {
            studentNames[index] = userInputScanner.next();
        }

        // Step 7: Ask the user to enter all the scores
        System.out.print("Enter scores: ");
        // Step 8: Read each score and store it in the studentScores array
        for (int index = 0; index < numberOfStudents; index++) {
            studentScores[index] = userInputScanner.nextInt();
        }

        // Step 9: Sort the scores array so the scores go from smallest to largest
        Arrays.sort(studentScores);

        // Step 10: After sorting, the top student should be at the last position
        String topStudentName = studentNames[numberOfStudents - 1];
        int topStudentScore = studentScores[numberOfStudents - 1];

        // Step 11: Print the top student's name and score in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 12: Close the scanner because we are done with input
        userInputScanner.close();
    }
}