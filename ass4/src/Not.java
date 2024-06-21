import java.util.List;
import java.util.Map;

public class Not extends UnaryExpression {

    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !expression.evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return super.evaluate();
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public String toString() {
        return "~(" + expression.toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Not(expression.assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(super.expression.nandify(), super.expression.nandify());
    }

    @Override
    public Expression norify() {
        return new Nor(super.expression.norify(), super.expression.norify());
    }
}