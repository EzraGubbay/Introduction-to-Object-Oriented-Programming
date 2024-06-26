import java.util.List;
import java.util.Map;

public class Nand extends BinaryExpression {

    public Nand(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // Delegation - Here we used the evaluation functionality in the And class and returned the opposite value.
        And delegated = new And(super.getLeft(), super.getRight());
        return !delegated.evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        Boolean left, right;
        // Try evaluating left and right expressions, assuming they are only comprised of truth values.
        // If one of the expressions has at least one variable, exception will be thrown here.
        left = super.getLeft().evaluate();
        right = super.getRight().evaluate();
        // Nand operator logic.
        return !(left && right);
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public String toString() {
        return "(" + super.getLeft().toString() + " A " + super.getRight().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Nand(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(super.getLeft().nandify(), super.getRight().nandify());
    }

    @Override
    public Expression norify() {
        /*
         * As Nand is simply a "Not" logic applied to an And expression, we will use delegation to nandify the And
         * expression by using the norify methods of Not and And.
         */
        Expression delegated = new Not(new And(super.getLeft(), super.getRight()));
        return delegated.norify();
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
            return simpleValue ? new Not(simpleRight).simplify() : new Val(true);
        }

        try {
            simpleValue = simpleRight.evaluate();
        } catch (Exception e) {
            // Right expression has at least one variable.
            // This means both expressions have variables.
        }

        if (simpleValue != null) {
            // Right expression evaluates to a truth value as it has no variables.
            return simpleValue ? new Not(simpleLeft).simplify() : new Val(true);
        }

        // Both expressions have variables.
        // If they are equal, return false. Otherwise, return a new simplified expression.
        return super.equals(simpleLeft, simpleRight) ? new Not(simpleLeft).simplify()
                : new Nand(simpleLeft, simpleRight);
    }
}
