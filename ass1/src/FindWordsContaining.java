/**
 * @author Ezra Gubbay
 * ID: 209184308
 */
public class FindWordsContaining {

    /**
     * Program's main. Receives a series of words, and a letter as the last command-line argument.
     * Prints the words that contain the given letter.
     * @param args - command-line arguments
     */
    public static void main(String[] args) {
        String lastArg;

        if (args.length < 2) {
            System.out.println("Invalid input");
            return;
        }

        lastArg = args[args.length - 1];

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].contains(lastArg)) {
                System.out.println(args[i]);
            }
        }
    }
}