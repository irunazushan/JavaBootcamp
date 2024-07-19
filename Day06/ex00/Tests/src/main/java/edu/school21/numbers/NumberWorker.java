package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {

        if (number <= 1) {
            throw new IllegalNumberException("The number should be more than 1");
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitSum(int number) {
        int result = 0;
        if (number < 0)
            number *= -1;
        while (number > 0) {
            result += number % 10;
            number /= 10;
        }

        return result;
    }
}
