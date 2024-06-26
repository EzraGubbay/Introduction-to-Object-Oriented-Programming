import java.util.Map;
import java.util.List;

public class Xor extends BinaryExpression {

    public Xor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // Here we are returning true if the left expression evaluates to true and the right does not, or the left is
        // false, but the right is true (exclusive or logic).
        return ((super.getLeft().evaluate(assignment) && !super.getRight().evaluate(assignment))
                || (!super.getLeft().evaluate(assignment) && super.getRight().evaluate(assignment)));
    }

    @Override
    public Boolean evaluate() throws Exception {
        Boolean left, right;
        // Try evaluating left and right expressions, assuming they are only comprised of truth values.
        // If one of the expressions has at least one variable, exception will be thrown here.
        left = super.getLeft().evaluate();
        right = super.getRight().evaluate();
        // Xor operator logic.
        return (left && !right) || (!left && right);
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public String toString() {
        return "(" + super.getLeft().toString() + " ^ " + super.getRight().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Xor(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(new Nand(super.getLeft().nandify(), new Nand(super.getLeft().nandify(), super.getRight().nandify())),
                new Nand(super.getRight().nandify(), new Nand(super.getLeft().nandify(), super.getRight().nandify())));
    }

    @Override
    public Expression norify() {
        return new Nor(new Nor(new Nor(super.getLeft().norify(), super.getLeft().norify()),
                new Nor(super.getRight().norify(), super.getRight().norify())),
                new Nor(super.getLeft().norify(), super.getRight().norify()));
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
            return simpleValue ? new Not(simpleRight).simplify() : simpleRight;
        }

        try {
            simpleValue = simpleRight.evaluate();
        } catch (Exception e) {
            // Right expression has at least one variable.
            // This means both expressions have variables.
        }

        if (simpleValue != null) {
            // Right expression evaluates to a truth value as it has no variables.
            return simpleValue ? new Not(simpleLeft).simplify() : simpleLeft;
        }

        // Both expressions have variables.
        // If they are equal, return false. Otherwise, return a new simplified expression.
        return super.equals(simpleLeft, simpleRight) ? new Val(false) : new Xor(simpleLeft, simpleRight);
    }
}
