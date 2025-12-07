import java.util.*;
public class Q3 {
    public static void main(String[] x) {
        Scanner n = new Scanner(System.in);
        System.out.print("Enter text: ");
        String y = "";
        if (n.hasNextLine()) {
            String z = n.nextLine();
            if (z != null) {
                String a = z.toUpperCase();
                if (a != null) {
                    y = a.replace(' ', '_');
                }
            }
        }
        if (y == null) {
            y = "";
        }
        System.out.println("Result: " + y);
    }
}