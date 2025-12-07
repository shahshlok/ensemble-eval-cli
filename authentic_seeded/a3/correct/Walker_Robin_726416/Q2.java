import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user for the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Extra safety check: ensure the number of students is positive
        if (numberOfStudents <= 0) {
            // If there are no students or a negative number is given, do nothing further
            // In a real program, we might print an error, but the assignment does not specify
            return;
        }

        // Create an array to hold all the student names
        String[] studentNamesArray = new String[numberOfStudents];
        // Create an array to hold all the student scores
        int[] studentScoresArray = new int[numberOfStudents];

        // Consume any leftover newline to avoid skipping input
        if (userInputScanner.hasNextLine()) {
            String temporaryClearLine = userInputScanner.nextLine();
        }

        // Prompt the user to enter all the names
        System.out.print("Enter names: ");
        // Read all names; assume they are separated by spaces
        for (int index = 0; index < numberOfStudents; index++) {
            // Read the next name token
            if (userInputScanner.hasNext()) {
                String currentStudentName = userInputScanner.next();
                studentNamesArray[index] = currentStudentName;
            } else {
                // If input is missing, assign a default placeholder name
                studentNamesArray[index] = "Unknown";
            }
        }

        // Prompt the user to enter all the scores
        System.out.print("Enter scores: ");
        // Read all scores; assume they are separated by spaces
        for (int index = 0; index < numberOfStudents; index++) {
            // Read the next score; if not available, use 0 as a fallback
            if (userInputScanner.hasNextInt()) {
                int currentStudentScore = userInputScanner.nextInt();
                studentScoresArray[index] = currentStudentScore;
            } else {
                // If there is invalid or missing input, set score to 0
                int fallbackScore = 0;
                studentScoresArray[index] = fallbackScore;

                // Try to consume the invalid token if it exists
                if (userInputScanner.hasNext()) {
                    String invalidToken = userInputScanner.next();
                }
            }
        }

        // At this point we have two parallel arrays:
        // studentNamesArray[i] corresponds to studentScoresArray[i]

        // Now we sort both arrays based on the scores in ascending order.
        // We will use a simple bubble sort algorithm because it is easy to understand.
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                int currentScore = studentScoresArray[innerIndex];
                int nextScore = studentScoresArray[innerIndex + 1];

                // If the current score is greater than the next score, swap them
                if (currentScore > nextScore) {
                    // Swap scores
                    int temporaryScoreHolder = studentScoresArray[innerIndex];
                    studentScoresArray[innerIndex] = studentScoresArray[innerIndex + 1];
                    studentScoresArray[innerIndex + 1] = temporaryScoreHolder;

                    // Swap corresponding names to maintain the parallel array relationship
                    String temporaryNameHolder = studentNamesArray[innerIndex];
                    studentNamesArray[innerIndex] = studentNamesArray[innerIndex + 1];
                    studentNamesArray[innerIndex + 1] = temporaryNameHolder;
                }
            }
        }

        // After sorting in ascending order, the highest score is at the last index
        int lastIndex = numberOfStudents - 1;

        // Extra safety check: ensure lastIndex is valid
        if (lastIndex >= 0 && lastIndex < numberOfStudents) {
            String topStudentName = studentNamesArray[lastIndex];
            int topStudentScore = studentScoresArray[lastIndex];

            // Print the top student's name and score in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner to be tidy
        userInputScanner.close();
    }
}