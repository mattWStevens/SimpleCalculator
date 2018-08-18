package calculationdoer;

/**
 * Represents a custom Exception for an invalid equation.
 * 
 * @author Matthew Stevens
 * @version 05/19/18
 */
public class InvalidEquationException extends Exception
{
    private String message;
    
    /**
     * Constructs an InvalidEquationException Object with
     * the specified message.
     * 
     * @param message the message for the InvalidEquationException
     */
    public InvalidEquationException(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}