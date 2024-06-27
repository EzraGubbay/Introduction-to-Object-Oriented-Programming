/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The MissingVariableException class.
 * Represents an exception where an expression was evaluated without proper variable mapping and truth value assignment.
 */
public class MissingVariableMappingException extends Exception {

    /**
     * Constructor.
     */
    public MissingVariableMappingException() {

        super("Missing variable assignment - assignment is required to evaluate the expression.");
    }
}
