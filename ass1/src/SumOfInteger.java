/**
 * @author Ezra Gubbay
 * ID: 209184308
 */
public class SumOfInteger {

    /**
     * Program's main. Calculates the digits of the number argument from the command-line.
     * @param args - command-line arguments
     */
    public static void main(String[] args) {
        int sum = 0, argsValue;

        // If no number was received, print a message and exit the program.
        if (args.length == 0) {
            System.out.println("Invalid input");
            return;
        }

        // Convert the string integer argument to a primitive int.
        argsValue = Integer.parseInt(args[0]);

        // Sum the digits of the number.
        while (argsValue != 0) {
            sum += argsValue % 10;
            argsValue /= 10;
        }

        // Print the sum.
        System.out.println(sum);
    }
}
