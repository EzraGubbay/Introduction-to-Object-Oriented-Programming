import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The Var class. Represents an atomic variable in an expression. Can also be an expression itself.
 */
public class Var implements Expression {

    private final String var;

    public Var(String var) {
        this.var = var;
    }

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result.
     * If the expression contains a variable which is not in the assignment, an exception is thrown.
     * @param assignment - A mapping of each variable in the expression to its truth value.
     * @return True if the expression evaluates to true, false otherwise.
     */
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        if (!assignment.containsKey(this.var)) {
            throw new MissingVariableMappingException();
        }
        else {
            return assignment.get(this.var);
        }
    }

    /**
     * A convenience method.
     * Like the `evaluate(assignment)` method above, but uses an empty assignment.
     * @return True if the expression evaluates to true, false otherwise.
     */
    @Override
    public Boolean evaluate() throws Exception{
        throw new MissingVariableMappingException();
    }

    /**
     * Returns a list of the variables in the expression.
     * @return Null, as Val is a truth value and does not have variables.
     */
    @Override
    public List<String> getVariables() {
        List<String> vars = new ArrayList<>();
        vars.add(this.var);
        return vars;
    }

    /**
     * Returns a nice string representation of the expression.
     * @return A string representation of the expression.
     */
    @Override
    public String toString() {
        return this.var;
    }

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression.
     * Does not modify the current expression.
     * @param var - The variable to be switched with an expression.
     * @param expression - The expression that should substitute the variable.
     * @return Null, as this is a truth value and thus does not have variables that can be substituted by an expression.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        if (this.var.equals(var)) {
            return expression;
        }
        return new Var(this.var);
    }

    @Override
    public Expression nandify() {
        return new Var(this.var);
        //return new Nand(new Nand(new Var(this.var), new Var(this.var)), new Nand(new Var(this.var), new Var(this.var)));
    }

    @Override
    public Expression norify() {
        return new Var(this.var);
        //return new Nor(new Nor(new Var(this.var), new Var(this.var)), new Nor(new Var(this.var), new Var(this.var)));
    }

}