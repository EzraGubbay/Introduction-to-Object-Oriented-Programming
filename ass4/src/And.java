import java.util.Map;
import java.util.List;

public class And extends BinaryExpression {

    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return super.getLeft().evaluate(assignment) && super.getRight().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        Boolean left, right;
        // Try evaluating left and right expressions, assuming they are only comprised of truth values.
        // If one of the expressions has at least one variable, exception will be thrown here.
        left = super.getLeft().evaluate();
        right = super.getRight().evaluate();
        // And operator logic.
        return left && right;
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public String toString() {
        return "(" + super.getLeft().toString() + " & " + super.getRight().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new And(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(new Nand(super.getLeft().nandify(), super.getRight().nandify()),
                new Nand(super.getLeft().nandify(), super.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        return new Nor(new Nor(super.getLeft().norify(), super.getLeft().norify()),
                new Nor(super.getRight().norify(), super.getRight().norify()));
    }

    /**
     * Rationale:
     * Get simplified versions of left and right expressions.
     * Try evaluating left.
     * SUCCESS -> if left is true, return right, if left is false, return false.
     * FAIL -> Try evaluating right. Similar success steps as before, but the opposite.
     * FAIL -> Check equality. If equal, return left (arbitrary). Otherwise return And(simpleLeft, simpleRight).
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
            return simpleValue ? simpleRight : new Val(false);
        }

        try {
            simpleValue = simpleRight.evaluate();
        } catch (Exception e) {
            // Right expression has at least one variable.
            // This means both expressions have variables.
        }

        if (simpleValue != null) {
            // Right expression evaluates to a truth value as it has no variables.
            return simpleValue ? simpleLeft : new Val(false);
        }

        // Both expressions have variables.
        // If they are equal, arbitrarily return the left expression, otherwise return a new simplified expression.
        return super.equals(simpleLeft, simpleRight) ? simpleLeft : new And(simpleLeft, simpleRight);
    }
}