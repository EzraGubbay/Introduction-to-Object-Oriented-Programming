import java.util.List;
import java.util.Map;

public class Not extends UnaryExpression {

    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !super.getExpression().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        // Try evaluating the expression, assuming it is only comprised of truth values.
        // If the expression has at least one variable, an exception will be thrown here.
        Boolean value = super.getExpression().evaluate();
        // Not operator logic.
        return !value;
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public String toString() {
        return "~(" + super.getExpression().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Not(super.getExpression().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(super.getExpression().nandify(), super.getExpression().nandify());
    }

    @Override
    public Expression norify() {
        return new Nor(super.getExpression().norify(), super.getExpression().norify());
    }

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