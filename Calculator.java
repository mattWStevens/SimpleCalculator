package calculationdoer;
import java.util.Stack;
import java.util.LinkedList;

/**
 * Represents a simple calculator that can compute equations with
 * two operands that are both integers with any number of digits and also
 * store results.
 * 
 * @author Matthew Stevens
 * @version 05/19/18
 */
public class Calculator 
{
    private double result;
    private Stack<Double> memory = new Stack<Double>();
    
    /**
     * Constructs a new Calculator
     */
    public Calculator()
    {
        
    }
    
    /**
     * Returns the result.
     * 
     * @return the result
     */
    public double getResult()
    {
        return result;
    }
    
    /**
     * Sets the result to the passed in value.
     * 
     * @param result the passed in result to be changed
     */
    public void setResult(double result)
    {
        this.result = result;
    }
    
    /**
     * Clears the result.
     */
    public void clear()
    {
        result = 0.0;
    }
    
    /**
     * Returns the stored result from memory, or zero if no
     * value is stored.
     * 
     * @return the stored result from memory, or zero if no value
     * is stored
     */
    public double accessMemory()
    {
        while (!memory.isEmpty())
            return memory.pop();
        
        System.out.println("There is nothing stored in memory, default " + 
                           "vale of zero will be used instead.");
        return 0.0; // return zero if memory is empty
    }
    
    /**
     * Clears all memory.
     */
    public void clearAll()
    {
        result = 0.0;
        
        while (!memory.isEmpty())
            memory.pop();
    }
    
    /**
     * Saves the desired result into the Calculator's
     * memory.
     * 
     * @param result the result to be stored
     */
    public void saveResult(double result)
    {
        memory.push(result);
    }
    
    /**
     * Computes the result given the two operands and the desired operator.
     * 
     * @param first the first operand
     * @param second the second operand
     * @param operator the operator
     */
    public void operate(double first, double second, String operator)
    {
        if (operator.equals("+"))
            result = first + second;
        
        if (operator.equals("-"))
            result = first - second;
        
        if (operator.equals("*"))
            result = first * second;
        
        if (operator.equals("/"))
            result = first / second;
    }
    
    /**
     * Parses the equation for the values to pass to the
     * operate method.
     * 
     * @param equation the equation to be parsed
     */
    public void parse(String equation) throws InvalidEquationException
    {
        LinkedList<Double> l = new LinkedList<Double>();
        String operator = "";
            
        if (!validEquation(equation))
            throw new InvalidEquationException("Invalid Equation");
            
        // call special calculation if 'ans' needs to be retrieved from memory
        else if (equation.toLowerCase().contains("ans"))
            memCalc(equation);
 
        else
        {
            double first = 0.0;
            double second = 0.0;
            
            first = convert(firstOp(equation), 0);
            second = convert(secondOp(equation), 0);
            operator = Character.toString(equation.charAt(findOperator(equation)));
            
            operate(first, second, operator);
        }
    }
    
    /**
     * Checks whether or not the given equation is in the proper
     * format.
     * 
     * @param equation the equation to be checked
     * @return true if the equation is in proper format, else false
     */
    public boolean validate(String equation)
    {
        return (equation.contains("+") || equation.contains("-") ||
                equation.contains("/") || equation.contains("*"));
    }
    
    //--------------------------------------------------------------------
    // Protected and Private Helper Methods
    //--------------------------------------------------------------------
    /**
     * Parses the equation if the term 'ans' is utilized in it.
     * 
     * @param equation the equation to be parsed
     */
    protected void memCalc(String equation)
    { 
        double first, second;
        String operator = Character.toString(equation.charAt(findOperator(equation)));
        
        // if first half of equation needs to be retrieved from memory
        if (equation.substring(0, 3).equalsIgnoreCase("ans"))
        {
            first = accessMemory();
            second = convert(secondOp(equation), 0);
            
            // calculate equation that had 'ans' as first operand
            operate(first, second, operator);
        }
        
        // if second half of equation needs to be retrieved from memory
        else
        {
            first = convert(firstOp(equation), 0);
            second = accessMemory();
            
            // calculate equation that had 'ans' as second operand
            operate(first, second, operator);
        }
    }
    
    /**
     * Finds the index for the starting value of the operand if 
     * the equation contains 'ans' in it.
     * 
     * @param equation the equation to be examined
     * @return the index of the operand in the equation
     */
    protected int indexFind(String equation)
    {   
        if (equation.substring(0, 3).equalsIgnoreCase("ans"))
        {
            for (int i = 0; i < equation.length(); i++)
            {
                if (isOperator(equation.charAt(i)))
                {
                    // if no space character after operator
                    if (equation.charAt(i + 1) != ' ')
                        return i + 1;
                    
                    // if there is a space character after operator
                    else
                        return i + 2;
                }       
            }
        }
        
        return 0;   // if no operator found, return 0
    }
    
    /**
     * Returns the index of the operator in the equation.
     * 
     * @param equation the equation to be examined
     * @return the index of the operator
     */
    protected int findOperator(String equation)
    {
        for (int i = 0; i < equation.length(); i++)
            if (isOperator(equation.charAt(i)))
                return i;
        return 0;   // if no operator found, return zero
    }
    
    /**
     * Determines whether the given character is an operator.
     * 
     * @param c the character to check
     * @return true if the given character is an operator, else false
     */
    protected boolean isOperator(char c)
    {
        return (c == '+' || c == '-' || c == '/' ||
                c == '*');
    }
    
    /**
     * Converts the given operand String into a Double.
     * 
     * @param equation the equation to convert
     * @param x the value to keep track of the decimal places
     * @return the converted value
     */
    protected double convert(String operand, int x)
    {
        double converted = 0.0;
        
        if (operand.length() == 0)
            return converted;
        else
        {
            converted += 
            Double.parseDouble(Character.toString(operand.charAt(operand.length() - 1))) * Math.pow(10, x);
            
            return converted + convert(operand.substring(0, operand.length() - 1), x + 1);
        }
    }
    
    /**
     * Splits the first operand substring from the given equation String.
     * 
     * @param equation the equation to be split
     * @return the first operand String
     */
    protected String firstOp(String equation)
    {
        // if there is a space after first operand
        if (equation.charAt(findOperator(equation) - 1) == ' ')
        {
            return equation.substring(0, findOperator(equation) - 1);
        }
        
        // no space after first operand
        else
        {
            return equation.substring(0, findOperator(equation));
        }
    }
    
    /**
     * Splits the second operand substring from the given equation String.
     * 
     * @param equation the equation to be split
     * @return the second operand String
     */
    protected String secondOp(String equation)
    {
        // if there is a space before second operand
        if (equation.charAt(findOperator(equation) + 1) == ' ')
        {
            return equation.substring(findOperator(equation) + 2);
        }
        
        // no space before second operand
        else
        {
            return equation.substring(findOperator(equation) + 1);
        }
    }
    
    /**
     * Determines whether or not the given equation is valid or not.
     * 
     * @param equation the equation to be examined
     * @return true if the equation is valid, else false
     */
    private boolean validEquation(String equation)
    {
        if (equation.toLowerCase().contains("ans"))
            return true;
        
        for (int i = 0; i < equation.length(); i++)
        {
            if ((!Character.isDigit(equation.charAt(i)) && equation.charAt(i) != ' ') 
                 && !isOperator(equation.charAt(i)))
                return false;
        }
        
        return true;
    }
}