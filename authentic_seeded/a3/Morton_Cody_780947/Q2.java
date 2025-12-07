import java.util.Scanner;
import java.util.Arrays;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Step 1: Ask the user for the number of students and read it
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 2: Create an array to store the student names
        String[] studentNames = new String[numberOfStudents];

        // Step 3: Create an array to store the student scores
        int[] studentScores = new int[numberOfStudents];

        // Step 4: Read the student names into the studentNames array
        System.out.print("Enter names: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentNames[currentIndex] = userInputScanner.next();
        }

        // Step 5: Read the student scores into the studentScores array
        System.out.print("Enter scores: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentScores[currentIndex] = userInputScanner.nextInt();
        }

        // Step 6: Sort the scores array in ascending order
        // Since names and scores logically belong together,
        // when we sort scores, the positions of names should still match
        Arrays.sort(studentScores);

        // Step 7: After sorting in ascending order, the highest score is at the last index
        int indexOfTopStudent = numberOfStudents - 1;
        String topStudentName = studentNames[indexOfTopStudent];
        int topStudentScore = studentScores[indexOfTopStudent];

        // Step 8: Print the top student in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 9: Close the scanner to free resources
        userInputScanner.close();
    }
}