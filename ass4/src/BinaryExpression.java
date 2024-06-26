import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BinaryExpression extends BaseExpression {

    private final Expression left;
    private final Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected Expression getLeft() {
        return this.left;
    }

    protected Expression getRight() {
        return this.right;
    }

    @Override
    public abstract Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    @Override
    public abstract Boolean evaluate() throws Exception;

    @Override
    public List<String> getVariables() {
        List<String> leftVars = this.left.getVariables(), rightVars = this.right.getVariables();
        List<String> vars = new ArrayList<>();
        vars.addAll(leftVars);
        vars.addAll(rightVars);
        return vars;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract Expression assign(String var, Expression expression);

    @Override
    public abstract Expression nandify();

    @Override
    public abstract Expression norify();

    @Override
    public abstract Expression simplify();

    protected boolean equals(Expression left, Expression right) {
        return this.left.toString().equals(this.right.toString());
    }
}