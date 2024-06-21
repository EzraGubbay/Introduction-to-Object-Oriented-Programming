public class MissingVariableMappingException extends Exception {

    public MissingVariableMappingException() {

        super("Missing variable assignment - assignment is required to evaluate the expression.");
    }
}
