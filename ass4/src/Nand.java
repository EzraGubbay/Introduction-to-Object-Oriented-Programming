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
        return super.evaluate();
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
}
