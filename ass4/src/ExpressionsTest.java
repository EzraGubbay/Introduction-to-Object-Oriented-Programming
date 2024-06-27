import java.util.Map;
import java.util.TreeMap;

/**
 * Name: Ezra Gubbay
 * ID: 209184308
 * Description - The ExpressionsTest class. Creates a logical expression with three variables,
 * and prints it and its nandified, norified and simplified forms, as well as evaluates it using an
 * assignment of values to the expression's atomic variables.
 */
public class ExpressionsTest {

    /**
     * Main method.
     * @param args - Command-Line arguments. Unused in this program.
     */
    public static void main(String[] args) {
        // 1. Create an expression with at least three variables.
        Expression e = new Xnor(new Var("x"), new And(
                new Xor(new Var("y"),
                new Val(true)),
                new Var("z")));

        // 2. Print the expression.
        System.out.println(e.toString());

        // 3. Print the value of the expression with an assignment to every variable.
        Map<String, Boolean> assignment = new TreeMap<String, Boolean>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", true);

        try {
            System.out.println(e.evaluate(assignment));
        } catch (Exception ignored) {
            // If we reached here, we have not assigned all the variables in the expression a truth value.
            System.err.println("Error evaluating expression.");
        }

        // 4. Print the Nandified version of the expression.
        System.out.println(e.nandify().toString());

        // 5. Print the Norified version of the expression.
        System.out.println(e.norify().toString());

        // 6. Print the simplified version of the expression.
        System.out.println(e.simplify().toString());
    }
}
