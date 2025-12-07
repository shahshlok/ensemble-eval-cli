import java.util.Scanner;
public class Q3 {
    public static void main(String[] args) {
        Scanner x = new Scanner(System.in);
        System.out.print("Enter grade: ");
        int y = 0;
        if (x.hasNextInt()) y = x.nextInt();
        int n = y;
        String z = "";
        if (n >= 0) {
            if (n <= 100) {
                if (n >= 90) z = "A"; else if (n >= 80) z = "B"; else if (n >= 70) z = "C"; else if (n >= 60) z = "D"; else z = "F";
            }
        }
        if (z.equals("")) {
            if (n < 0 || n > 100) z = "F";
        }
        System.out.println("Letter grade: " + z);
        x.close();
    }
}