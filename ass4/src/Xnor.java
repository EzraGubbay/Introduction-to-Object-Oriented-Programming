import java.util.Map;
import java.util.List;

public class Xnor extends BinaryExpression {

    public Xnor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // Delegation - Here we used the evaluation functionality in the Xor class and returned the opposite value.
        Xor delegated = new Xor(super.getLeft(), super.getRight());
        return !delegated.evaluate(assignment);
    }

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

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public String toString() {
        return "(" + super.getLeft().toString() + " # " + super.getRight().toString() + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Xnor(super.getLeft().assign(var, expression), super.getRight().assign(var, expression));
    }

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

    @Override
    public Expression norify() {
        /*
         * Though Xnor is Not logic applied to Xor, we will not delegate the "norification" functionality for Xnor,
         * as it will return a more complicated expression that can otherwise be shorter.
         */
        return new Nor(new Nor(super.getLeft().norify(), new Nor(super.getLeft().norify(), super.getRight().norify())),
                new Nor(super.getRight().norify(), new Nor(super.getLeft().norify(), super.getRight().norify())));
    }

    @Override
    public Expression simplify() {
        Expression simpleLeft = super.getLeft().simplify();
        Expression simpleRight = super.getRight().simplify();

        // If the expressions are the same, we will simplify Xnor to true.
        // Otherwise, we will return a new simplified Xnor expression.
        return super.equals(simpleLeft, simpleRight) ? new Val(true) : new Xnor(simpleLeft, simpleRight);
    }
}
