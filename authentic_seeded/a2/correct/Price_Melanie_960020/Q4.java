import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter height: ");
        int height = scanner.nextInt();

        if (height > 0) {
            int row = 1;
            while (row <= height) {
                int starCount = row;
                if (starCount > 0) {
                    int printed = 0;
                    while (printed < starCount) {
                        System.out.print("*");
                        printed = printed + 1;
                    }
                }
                System.out.println();
                row = row + 1;
            }
        }

        scanner.close();
    }
}