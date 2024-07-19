import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] strForAnalysis = sc.nextLine().toCharArray();
        int[] countChars = new int[65535];
        for (char el : strForAnalysis) {
            ++countChars[el];
        }
        char[] topChars = new char[10];
        int[] countTopChar = new int[10];

        for (int i = 0; i < 10; ++i) {
            int[] pairOfCharAmount = getPairCharAmount(countChars);
            topChars[i] = (char)pairOfCharAmount[0];
            countTopChar[i] = pairOfCharAmount[1];
            countChars[pairOfCharAmount[0]] = 0;
        }

        if (countTopChar[0] > 999) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }

        drawHChart(topChars, countTopChar);
    }

    public static int[] getPairCharAmount(int[] countChars) {
        int maxNum = 0;
        int ind = 0;
        for (int i = 0; i < countChars.length; ++i) {
            if (countChars[i] > maxNum) {
                maxNum = countChars[i];
                ind = i;
            }
        }

        int[] result = new int[2];
        result[0] = ind;
        result[1] = maxNum;
        return result;
    }

    public static void drawHChart(char[] topChars, int[] countTopChar) {
        int maxHeight = 10;
        double scale = (double) maxHeight / countTopChar[0];
        for (int i = maxHeight + 1; i > 0; --i) {
            for (int height : countTopChar) {
                if (height * scale >= i)
                    System.out.print(" # ");
                else if (height * scale >= i - 1) {
                    System.out.print(" " + height + " ");
                }
            }
            System.out.println();
        }
        for (char topChar : topChars) {
            System.out.print(" " + topChar + " ");
        }
        System.out.println();
    }
}