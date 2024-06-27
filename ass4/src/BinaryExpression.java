import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The BinaryExpression class. Represents an expression with a binary logical operator.
 */
public abstract class BinaryExpression extends BaseExpression {

    private final Expression left;
    private final Expression right;

    /**
     * Constructor.
     * @param left - The left expression in this binary logical expression.
     * @param right - The right expression in this binary logical expression.
     */
    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Accessor for the left expression for child classes.
     * @return The left expression
     */
    protected Expression getLeft() {
        return this.left;
    }

    /**
     * Accessor for the right expression for child classes.
     * @return The right expression
     */
    protected Expression getRight() {
        return this.right;
    }

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result.
     * If the expression contains a variable which is not in the assignment, an exception is thrown.
     * @param assignment - A mapping of each variable in the expression to its truth value.
     * @throws Exception - If evaluation cannot be completed, due to incorrect variable assignment
     * @return True if the expression evaluates to true, false otherwise.
     */
    @Override
    public abstract Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * A convenience method.
     * Like the `evaluate(assignment)` method above, but uses an empty assignment.
     * @throws Exception - If evaluation cannot be completed, due to incorrect variable assignment
     * @return True if the expression evaluates to true, false otherwise.
     */
    @Override
    public abstract Boolean evaluate() throws Exception;

    /**
     * Returns a list of the variables in the expression.
     * @return A list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        List<String> leftVars = this.left.getVariables(), rightVars = this.right.getVariables();
        List<String> vars = new ArrayList<>();
        vars.addAll(leftVars);
        vars.addAll(rightVars);
        return vars;
    }

    /**
     * Returns a nice string representation of the expression.
     * @return A string representation of the expression.
     */
    @Override
    public abstract String toString();

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression.
     * Does not modify the current expression.
     * @param var - The variable to be switched with an expression.
     * @param expression - The expression that should substitute the variable.
     * @return A new expression in which all occurrences of the variable var are replaced with the provided expression.
     */
    @Override
    public abstract Expression assign(String var, Expression expression);

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     * @return The expression tree resulting from converting all the operations to the logical Nand operation.
     */
    @Override
    public abstract Expression nandify();

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     * @return The expression tree resulting from converting all the operations to the logical Nor operation.
     */
    @Override
    public abstract Expression norify();

    /**
     * Returns a simplified version of the current expression.
     * @return A simplified version of the current expression.
     */
    @Override
    public abstract Expression simplify();

    protected boolean equals(Expression left, Expression right) {
        return this.left.toString().equals(this.right.toString());
    }
}