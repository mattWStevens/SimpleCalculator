package calculationdoer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Depicts the interface of a simple calculator.
 * 
 * @author Matthew Stevens
 * @version 05/19/18
 */
public class CalculationDoer 
{
    /**
     * Depicts the interface of a simple calculator, allowing for the user
     * to interact with its capabilities.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        
        boolean quit = false;
        Calculator calc = new Calculator();
        
        while (!quit)
        {
            System.out.println("Please enter the equation you would " +
                               "like to solve: ");
            
            String equation = scan.nextLine();
            
            // ensure valid input
            try 
            {
                calc.parse(equation);
            } 
            
            catch (InvalidEquationException ex) 
            {
                System.err.println("InvalidEquationException: " +
                                   ex.getMessage());
            }
            
            System.out.println();
            
            if (calc.validate(equation))
                System.out.println("The result is " + calc.getResult());
            else
                System.out.println("Not valid equation.");
            
            
            System.out.println("Would you like to save your result(S),  " +
            "enter a new equation(E), clear memory(CM), clear result(CR), "
            + "or quit(Q)?: ");
            
            String response = scan.nextLine();
            
            if (response.equalsIgnoreCase("E"))
            {
                continue;
            }
            
            if (response.equalsIgnoreCase("Q"))
            {
                System.out.println("Now quiting...");
                quit = true;
            }
            
            else if (response.equalsIgnoreCase("S"))
            {
                System.out.println("Result saved. Access with 'ans'.");
                calc.saveResult(calc.getResult());
                calc.setResult(0);
                continue;
            }
            
            else if (response.equalsIgnoreCase("CM"))
            {
                System.out.println("Memory cleared.");
                calc.clearAll();
                continue;
            }
            
            else if (response.equalsIgnoreCase("CR"))
            {
                System.out.println("Result cleared.");
                calc.clear();
                continue;
            }
            
            else
            {
                System.out.println("Please try again.");
                continue;
            }
        }
    }    
}