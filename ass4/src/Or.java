import java.util.List;
import java.util.Map;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The Or class. Represents the Or logical operator.
 */
public class Or extends BinaryExpression {

    /**
     * Constructor.
     * @param left - The left expression in this binary logical expression.
     * @param right - The right expression in this binary logical expression.
     */
    public Or(Expression left, Expression right) {
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
        return super.getLeft().evaluate(assignment) || super.getRight().evaluate(assignment);
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
        // Or operator logic.
        return left || right;
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
        return "(" + super.getLeft().toString() + " | " + super.getRight().toString() + ")";
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
        return new Or(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     * @return The expression tree resulting from converting all the operations to the logical Nand operation.
     */
    @Override
    public Expression nandify() {
        return new Nand(new Nand(super.getLeft().nandify(), super.getLeft().nandify()),
                new Nand(super.getRight().nandify(), super.getRight().nandify()));
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     * @return The expression tree resulting from converting all the operations to the logical Nor operation.
     */
    @Override
    public Expression norify() {
        return new Nor(new Nor(super.getLeft().norify(), super.getRight().norify()),
                new Nor(super.getLeft().norify(), super.getRight().norify()));
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
            return simpleValue ? new Val(true) : simpleRight;
        }

        try {
            simpleValue = simpleRight.evaluate();
        } catch (Exception e) {
            // Right expression has at least one variable.
            // This means both expressions have variables.
        }

        if (simpleValue != null) {
            // Right expression evaluates to a truth value as it has no variables.
            return simpleValue ? new Val(true) : simpleLeft;
        }

        // Operator is commutative, so we will check for equality with the flipped version of it as well.
//        Expression commutative

        // Both expressions have variables.
        // If they are equal, arbitrarily return the left expression, otherwise return a new simplified expression.
        return super.equals(simpleLeft, simpleRight) ? simpleLeft : new Or(simpleLeft, simpleRight);
    }
}