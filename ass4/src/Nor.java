import java.util.Map;
import java.util.List;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The Nor class. Represents a binary logical expression which is true if and only if both left and right
 * expressions are either true or false simultaneously.
 */
public class Nor extends BinaryExpression {

    public Nor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // Delegation - Here we used the evaluation functionality in the Or class and returned the opposite value.
        Or delegated = new Or(super.getLeft(), super.getRight());
        return !delegated.evaluate(assignment);
    }

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

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public String toString() {
        return "(" + super.getLeft().toString() + " V " + super.getRight().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Nor(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        /*
         * As Nor is simply a "Not" logic applied to Or, we will use delegation to nandify the Nor expression
         * by using the nandify methods of Not and Or.
         */
        Expression delegated = new Not(new Or(super.getLeft(), super.getRight()));

        return delegated.nandify();
    }

    @Override
    public Expression norify() {
        return new Nor(super.getLeft().norify(), super.getRight().norify());
    }

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
