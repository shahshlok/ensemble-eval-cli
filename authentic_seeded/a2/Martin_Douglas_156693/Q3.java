import java.util.Scanner;

public class Q3 {
   public static void main(String[] args) {
      
      Scanner input_reader = new Scanner(System.in);

      System.out.print("Enter grade: ");
      
      int numericGrade = input_reader.nextInt();
      
      
         int safe_grade_holder = numericGrade;
         
         if (safe_grade_holder > 100) {
            safe_grade_holder = 100;
         }
         
         if (safe_grade_holder < 0) {
            safe_grade_holder = 0;
         }

      String letterGrade = "";

      
      if (safe_grade_holder >= 90 && safe_grade_holder <= 100) {
          letterGrade = "A";
      } else if (safe_grade_holder >= 80 && safe_grade_holder <= 89) {
      	  letterGrade = "B";
      } else if (safe_grade_holder >= 70 && safe_grade_holder <= 79) {
      	    letterGrade = "C";
      } else if (safe_grade_holder >= 60 && safe_grade_holder <= 69) {
      	letterGrade = "D";
      } else {
      	   if (safe_grade_holder <= 59) {
      	      letterGrade = "F";
      	   }
      }

      
      if (letterGrade != null) {
      	System.out.println("Letter grade: " + letterGrade);
      }

      input_reader.close();
   }
}