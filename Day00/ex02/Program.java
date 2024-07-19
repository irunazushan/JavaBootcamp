import java.util.Scanner;
class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int coffeeRequests = 0;
        while (num != 42) {
            if (numIsPrime(sumOfDigits(num))) {
                coffeeRequests++;
            }
            num = sc.nextInt();
        }

        System.out.println("Count of coffee-request - " + coffeeRequests);
    }

    public static int sumOfDigits(int num) {
        int res = 0;
        while (num > 0) {
            res += num % 10;
            num /= 10;
        }
        return res;
    }

    public static boolean numIsPrime(int num) {
        if (num < 2) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        boolean result = true;
        int step = 2;
        while (step <= Math.sqrt(num)) {
            if (num % step == 0) {
                result = false;
                break;
            }
            step++;
        }
        return result;
    }
}