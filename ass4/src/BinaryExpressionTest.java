import org.junit.Test;

import java.util.TreeMap;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class BinaryExpressionTest {


    public static void testOne() {
        Expression e = new Or(new And(new Var("x"), new Var("y")), new Var("z"));
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", true);
        try {
            assertTrue("SUCCESS", e.evaluate(assignment));
            System.out.println("SUCCESS");
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }
    }


    public static void testTwo() {
        Expression e = new Not(new Xor(new Var("x"), new Var("y")));
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", true);
        try {
            assertTrue("SUCCESS", e.evaluate(assignment));
            System.out.println("SUCCESS");
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }
    }


    public static void testThree() {
        Expression e = new Xor(new And(new Var("x"), new Or(new Var("y"), new Not(new Var("z")))), new Var("w"));
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", true);
        assignment.put("w", false);
        try {
            assertFalse("SUCCESS", e.evaluate(assignment));
            System.out.println("SUCCESS");
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }
    }


    public static void testFour() {
        Expression e = new Or(new Not(new And(new Var("x"), new Var("y"))), new Xor(new Var("z"), new Var("w")));
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", true);
        assignment.put("z", false);
        assignment.put("w", true);
        try {
            assertTrue("SUCCESS", e.evaluate(assignment));
            System.out.println("SUCCESS");
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }
    }


    public static void testFive() {
        Expression e = new Nand(new Or(new Var("x"), new Var("y")), new Nor(new Var("z"), new Var("w")));
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", true);
        assignment.put("z", false);
        assignment.put("w", true);
        try {
            assertTrue("SUCCESS", e.evaluate(assignment));
            System.out.println("SUCCESS");
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }
    }

    public static void main(String[] args) {
        testOne();
        testTwo();
        testThree();
        testFour();
        testFive();
    }

    /*
     * Commutativity Methods for Binary Expressions:
     @Override
    protected Expression commutativeClone() {
        return new And(super.getRight(), super.getLeft());
    }

    @Override
    protected Expression commutativeClone() {
        return new Or(super.getRight(), super.getLeft());
    }

    @Override
    protected Expression commutativeClone() {
        return new Xnor(super.getRight(), super.getLeft());
    }

    @Override
    protected Expression commutativeClone() {
        return new Nand(super.getRight(), super.getLeft());
    }

    @Override
    protected Expression commutativeClone() {
        return new Xor(super.getRight(), super.getLeft());
    }

    @Override
    protected Expression commutativeClone() {
        return new Nor(super.getRight(), super.getLeft());
    }

     */

}