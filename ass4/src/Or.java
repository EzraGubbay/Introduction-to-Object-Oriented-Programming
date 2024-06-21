import java.util.List;
import java.util.Map;

public class Or extends BinaryExpression {

    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return super.getLeft().evaluate(assignment) || super.getRight().evaluate(assignment);
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
        return "(" + super.getLeft().toString() + " | " + super.getRight().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Or(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(new Nand(super.getLeft().nandify(), super.getLeft().nandify()),
                new Nand(super.getRight().nandify(), super.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        return new Nor(new Nor(super.getLeft().norify(), super.getRight().norify()),
                new Nor(super.getLeft().norify(), super.getRight().norify()));
    }
}