import java.util.Map;
import java.util.List;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The Nor class. Represents a binary logical expression which is true if and only if both left and right
 * expressions are either true or false simultaneously.
 */
public class Nor extends BinaryExpression {

    /**
     * Constructor.
     * @param left - The left expression in this binary logical expression.
     * @param right - The right expression in this binary logical expression.
     */
    public Nor(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result.
     * If the expression contains a variable which is not in the assignment, an exception is thrown.
     * @param assignment - A mapping of each variable in the expression to its truth value.
     * @throws Exception - If evaluation cannot be completed, due to incorrect variable assignment
     * @return True if the expression evaluates to true, false otherwise.
     */
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // Delegation - Here we used the evaluation functionality in the Or class and returned the opposite value.
        Or delegated = new Or(super.getLeft(), super.getRight());
        return !delegated.evaluate(assignment);
    }

    /**
     * A convenience method.
     * Like the `evaluate(assignment)` method above, but uses an empty assignment.
     * @throws Exception - If evaluation cannot be completed, due to incorrect variable assignment
     * @return True if the expression evaluates to true, false otherwise.
     */
    @Override
    public Boolean evaluate() throws Exception {
        Boolean left, right;
        // Try evaluating left and right expressions, assuming they are only comprised of truth values.
        // If one of the expressions has at least one variable, exception will be thrown here.
        left = super.getLeft().evaluate();
        right = super.getRight().evaluate();
        // Nor operator logic.
        return !(left || right);
    }

    /**
     * Returns a list of the variables in the expression.
     * @return A list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    /**
     * Returns a nice string representation of the expression.
     * @return A string representation of the expression.
     */
    @Override
    public String toString() {
        return "(" + super.getLeft().toString() + " V " + super.getRight().toString() + ")";
    }

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression.
     * Does not modify the current expression.
     * @param var - The variable to be switched with an expression.
     * @param expression - The expression that should substitute the variable.
     * @return A new expression in which all occurrences of the variable var are replaced with the provided expression.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return new Nor(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     * @return The expression tree resulting from converting all the operations to the logical Nand operation.
     */
    @Override
    public Expression nandify() {
        /*
         * As Nor is simply a "Not" logic applied to Or, we will use delegation to nandify the Nor expression
         * by using the nandify methods of Not and Or.
         */
        Expression delegated = new Not(new Or(super.getLeft(), super.getRight()));

        return delegated.nandify();
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     * @return The expression tree resulting from converting all the operations to the logical Nor operation.
     */
    @Override
    public Expression norify() {
        return new Nor(super.getLeft().norify(), super.getRight().norify());
    }

    /**
     * Returns a simplified version of the current expression.
     * @return A simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = super.getLeft().simplify();
        Expression simpleRight = super.getRight().simplify();
        Boolean simpleValue = null;

        try {
            simpleValue = simpleLeft.evaluate();
        } catch (Exception e) {
            // Left expression has at least one variable.
        }

        if (simpleValue != null) {
            // Left expression evaluates to a truth value as it has no variables.
            return simpleValue ? new Val(false) : new Not(simpleRight).simplify();
        }

        try {
            simpleValue = simpleRight.evaluate();
        } catch (Exception e) {
            // Right expression has at least one variable.
            // This means both expressions have variables.
        }

        if (simpleValue != null) {
            // Right expression evaluates to a truth value as it has no variables.
            return simpleValue ? new Val(false) : new Not(simpleLeft).simplify();
        }

        // Both expressions have variables.
        // If they are equal, return false. Otherwise, return a new simplified expression.
        return super.equals(simpleLeft, simpleRight) ? new Not(simpleLeft).simplify()
                : new Nor(simpleLeft, simpleRight);
    }
}
