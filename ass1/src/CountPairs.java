/**
 * @author Ezra Gubbay
 * ID: 209184308
 */
public class CountPairs {

    /**
     * Program's main. Receives an array of numbers (we'll call it nums) and
     * prints the number of pairs such that nums[i] + nums[j] < target, where nums
     * is the array formed from the arguments, i and j are different indices in num,
     * and target is the last argument passed from the command line.
     * @param args - command-line arguments
     */
    public static void main(String[] args) {
        int target, pairs = 0;
        int[] nums;
        if (args.length < 2) {
            System.out.println("Invalid input");
            return;
        }

        // Defining the nums array and adding to it the int value of the numbers in the args array.
        nums = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            nums[i] = Integer.parseInt(args[i]);
        }

        target = Integer.parseInt(args[args.length - 1]);

        for (int i = 0; i < args.length - 2; i++) {
            for (int j = i + 1; j < args.length - 1; j++) {
                if (nums[i] + nums[j] < target) {
                    pairs += 1;
                }
            }
        }

        System.out.println(pairs);
    }
}
