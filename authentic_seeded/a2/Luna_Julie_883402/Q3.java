import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

   System.out.print("Enter grade: ");
   int grade_input = sc.nextInt();

    int a = 90;
     int b = 80;
      int c = 70;
   int d = 60;
		int zero = 0;
  int hundred = 100;




   String letterGrade;

    if (grade_input >= a && grade_input <= hundred) {
		  letterGrade = "A";
   } else if (grade_input >= b && grade_input <= a-1) {
    letterGrade = "B";
	   } else if (grade_input >= c && grade_input <= b-1) {
      letterGrade = "C";
	 } else if (grade_input >= d && grade_input <= c-1) {
      letterGrade = "D";
  } else {
		  letterGrade = "F";
	 }

   System.out.println("Letter grade: " + letterGrade);
 }
}