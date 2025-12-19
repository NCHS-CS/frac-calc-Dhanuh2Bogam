                                                        // Dhanuh
// Period 6
// Fraction Calculator Project

import java.util.*;

//  Description of what this program does goes here.
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            
            // print the result of processing the command
            System.out.println(result);
         }
         
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      System.out.print("Enter: ");
       return console.nextLine();

   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4
   //
   //
   //This is the place where we will be handling the main operations.
public static String processExpression(String input) {
   input = input.trim();//Oracle said that .trim(); gets rid of any whitespace from the ends of this string
   int firstSpace = input.indexOf(" ");
   int secondSpace = input.indexOf(" ", firstSpace + 1);

   String first = input.substring(0, firstSpace).trim();
   String second = input.substring(secondSpace + 1).trim();
   String operator = input.substring(firstSpace + 1, secondSpace).trim();
   int num1 =  getImproperNumerator(first);
   int den1 = getDenominator(first);
   int num2 = getImproperNumerator(second);
   int den2 = getDenominator(second);
 
   int numerator = 0;
   int denominator = 0;
      if(operator.equals("/") && num2 == 0){
         return "Can't divide by zero";
      } 
      if(operator.equals("+")){
         numerator = (num1 * den2) + (num2 * den1);
         denominator = den1 * den2;

      } else if (operator.equals("-")){
         numerator = (num1 * den2) - (num2 * den1);
         denominator = den1 * den2;

      } else if (operator.equals("*")){
         numerator = num1 * num2;
         denominator = den1 * den2;
      } else if (operator.equals("/")){
        numerator = num1 * den2;
        denominator = den1 * num2;
      }
   return formatResult(numerator, denominator);
}
 //Parse helper methods
 
 //Not in use
 public static String getFirstOperand (String input){ 
   int space = input.indexOf(" ");
   return input.substring(0,space).trim();
 }
 //Not in use
 public static String getSecondOperand(String input){
   int space = input.lastIndexOf(" ");
   return input.substring(space+1).trim();
 }
 //Gets the improper Numerator in the equation
 //Using the methods that get the individual components. 
 public static int getImproperNumerator(String frac){
   int whole = getWhole(frac);
   int num = getNumerator(frac);
   int den = getDenominator(frac);
   if(whole < 0){
      num *= -1;
 }
 return whole * den + num;
}
 //gets the numerator using parse Intger and indexO Of. 
 public static int getNumerator(String frac){
   int underscore = frac.indexOf("_");
      int slash = frac.indexOf("/"); 
      if (slash < 0) return 0;
      if (underscore >= 0){
         return Integer.parseInt(frac.substring(underscore + 1, slash));
      }
      return Integer.parseInt(frac.substring(0, slash));
 }

 //This method is used for getWhole. 
public static int getWhole(String frac){

   int underscore = frac.indexOf("_");
   int slash = frac.indexOf("/");
   // If no slash, it's a whole number
   if (slash < 0) {
      return Integer.parseInt(frac);
   }
   // If there's an underscore, extract whole number before it
   if (underscore >= 0) {
      return Integer.parseInt(frac.substring(0, underscore));
   }
   // No whole number part
   return 0;
}
//Gets the denominator using parse Int. 
//trim() is to get rid of any whitespaces. 
public static int getDenominator(String frac){
   //finds the index of slash
   int slash = frac.indexOf("/");
   if(slash<0) return 1; 
   return Integer.parseInt(frac.substring(slash+1).trim());
}
// math helper methods. 
//Formats the answer to make it printable.  
//num: numerator of fraction
//den: denominator
//rem: is the remainder.
public static String formatResult (int num, int den){
//If the numerator is 0, the result is ALWAYS ZERO. 
if(num == 0) return "0"; //I thought there was limit of lines for each methods so i didnt use the {}.
//if denominator was less than 0 you HAVE to invert the numerator and denmominator
if(den < 0){
   num *= -1;
   den *= -1;
}
//finds the gcd of the numerator and denominator. 
int gcd = gcd(Math.abs(num), den);
num /= gcd; //simplifies
den /= gcd;//simplifies
int whole = num/den;
int rem = Math.abs(num % den);
if(rem == 0) return "" + whole;
if( whole == 0 ) { 
   if(num < 0){
      return "-"+rem + "/"+ den;
   }
   return rem + "/" + den;
}
return whole + " " + rem + "/" + den;
}
//Takes the GCD of the two inputs A and B
public static int gcd(int a, int b){
//Until b == 0 the loop keeps repeating. 
while (b != 0){
   //Store the current value of 'b' inside a temp variable. 
   int temp = b;
   //b is updated to the remainder of a divided by a
   b = a % b;
   // a takes the value that b had before the update (the previous divisor.)
   a =  temp;
 }
 //When b finally becomes 0, a holds the last non-zero remainder, which is the greatest common denominator. 
return a;                               
}
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
     String help = "Help: for mixed numbers try using _ \n multiplication is NOT X its * \n Division is / not ÷  \n Format your input like(operator is changable): x_y/z / a_b/c ";
     
      return help;
   }
}


