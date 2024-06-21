import java.util.List;
import java.util.Map;

public abstract class UnaryExpression extends BaseExpression {

    protected Expression expression;

    public UnaryExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public abstract Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    @Override
    public Boolean evaluate() throws Exception {
        return super.evaluate();
    }

    @Override
    public List<String> getVariables() {
        return this.expression.getVariables();
    }

    @Override
    public abstract String toString();

    @Override
    public abstract Expression assign(String var, Expression expression);

    @Override
    public abstract Expression nandify();

    @Override
    public abstract Expression norify();
}