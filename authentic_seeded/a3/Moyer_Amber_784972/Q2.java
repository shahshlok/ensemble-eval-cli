import java.util.Scanner;
import java.util.Arrays;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 1: Ask the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 2: Create an array to store the student names
        String[] studentNamesArray = new String[numberOfStudents];

        // Step 3: Create an array to store the student scores
        int[] studentScoresArray = new int[numberOfStudents];

        // Step 4: Ask the user to enter the names
        System.out.print("Enter names: ");
        // Step 5: Read each name and store it in the names array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentNamesArray[currentIndex] = userInputScanner.next();
        }

        // Step 6: Ask the user to enter the scores
        System.out.print("Enter scores: ");
        // Step 7: Read each score and store it in the scores array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentScoresArray[currentIndex] = userInputScanner.nextInt();
        }

        // Step 8: Sort the scores array in ascending order
        Arrays.sort(studentScoresArray);

        // Step 9: After sorting in ascending order, the top student is at the last index
        int indexOfTopStudent = numberOfStudents - 1;
        String topStudentName = studentNamesArray[indexOfTopStudent];
        int topStudentScore = studentScoresArray[indexOfTopStudent];

        // Step 10: Print the result in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 11: Close the scanner
        userInputScanner.close();
    }
}