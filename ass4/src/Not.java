import java.util.List;
import java.util.Map;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The Not class. Represents the Not logical operator.
 */
public class Not extends UnaryExpression {

    /**
     * Constructor.
     * @param expression - The expression in this unary logical expression.
     */
    public Not(Expression expression) {
        super(expression);
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
        return !super.getExpression().evaluate(assignment);
    }

    /**
     * A convenience method.
     * Like the `evaluate(assignment)` method above, but uses an empty assignment.
     * @throws Exception - If evaluation cannot be completed, due to incorrect variable assignment
     * @return True if the expression evaluates to true, false otherwise.
     */
    @Override
    public Boolean evaluate() throws Exception {
        // Try evaluating the expression, assuming it is only comprised of truth values.
        // If the expression has at least one variable, an exception will be thrown here.
        Boolean value = super.getExpression().evaluate();
        // Not operator logic.
        return !value;
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
        return "~(" + super.getExpression().toString() + ")";
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
        return new Not(super.getExpression().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     * @return The expression tree resulting from converting all the operations to the logical Nand operation.
     */
    @Override
    public Expression nandify() {
        return new Nand(super.getExpression().nandify(), super.getExpression().nandify());
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     * @return The expression tree resulting from converting all the operations to the logical Nor operation.
     */
    @Override
    public Expression norify() {
        return new Nor(super.getExpression().norify(), super.getExpression().norify());
    }

    /**
     * Returns a simplified version of the current expression.
     * @return A simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression simplified = super.getExpression().simplify();
        Boolean value = null;

        try {
            value = simplified.evaluate();
        } catch (Exception e) {
            // Simplified expression has variables. We will simply return the not logic of the simplified expression.
            return new Not(simplified);
        }

        return value ? new Val(false) : new Val(true);
    }
}