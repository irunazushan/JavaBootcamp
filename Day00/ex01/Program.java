import java.util.Scanner;
class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
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

        System.out.println(result + " " + (step - 1));
    }
}