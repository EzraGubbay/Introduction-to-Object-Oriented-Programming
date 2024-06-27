import java.util.Map;
import java.util.List;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The Xnor class. Represents the Xnor logical operator.
 */
public class Xnor extends BinaryExpression {

    /**
     * Constructor.
     * @param left - The left expression in this binary logical expression.
     * @param right - The right expression in this binary logical expression.
     */
    public Xnor(Expression left, Expression right) {
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
        // Delegation - Here we used the evaluation functionality in the Xor class and returned the opposite value.
        Xor delegated = new Xor(super.getLeft(), super.getRight());
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
        // And operator logic.
        return !((left && !right) || (!left && right));
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
        return "(" + super.getLeft().toString() + " # " + super.getRight().toString() + ")";
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
        return new Xnor(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     * @return The expression tree resulting from converting all the operations to the logical Nand operation.
     */
    @Override
    public Expression nandify() {
        /*
         * Though Xnor is Not logic applied to Xor, we will not delegate the "nandification" functionality for Xnor,
         * as it will return a more complicated expression that can otherwise be shorter.
         */
        return new Nand(new Nand(new Nand(super.getLeft().nandify(), super.getLeft().nandify()),
                new Nand(super.getRight().nandify(), super.getRight().nandify())),
                new Nand(super.getLeft().nandify(), super.getRight().nandify()));
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     * @return The expression tree resulting from converting all the operations to the logical Nor operation.
     */
    @Override
    public Expression norify() {
        /*
         * Though Xnor is Not logic applied to Xor, we will not delegate the "norification" functionality for Xnor,
         * as it will return a more complicated expression that can otherwise be shorter.
         */
        return new Nor(new Nor(super.getLeft().norify(), new Nor(super.getLeft().norify(), super.getRight().norify())),
                new Nor(super.getRight().norify(), new Nor(super.getLeft().norify(), super.getRight().norify())));
    }

    /**
     * Returns a simplified version of the current expression.
     * @return A simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = super.getLeft().simplify();
        Expression simpleRight = super.getRight().simplify();

        // If the expressions are the same, we will simplify Xnor to true.
        // Otherwise, we will return a new simplified Xnor expression.
        return super.equals(simpleLeft, simpleRight) ? new Val(true) : new Xnor(simpleLeft, simpleRight);
    }
}
