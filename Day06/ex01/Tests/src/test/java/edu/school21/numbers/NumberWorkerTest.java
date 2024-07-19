package edu.school21.numbers;

import edu.school21.numbers.NumberWorker;
import edu.school21.numbers.IllegalNumberException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    private static NumberWorker nw;

    @BeforeAll
    public static void createNewNumberWorker() {
        nw = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 7, 131, 937})
    public void isPrimeForPrimes(int number) {
        Assert.assertTrue(nw.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 15, 49, 10})
    public void isPrimeForNotPrimes(int number) {
        Assert.assertFalse(nw.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, -1, -45, 0})
    public void isPrimeForIncorrectNumbers(int number) {
        Assert.assertThrows(IllegalNumberException.class, () -> nw.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "../../../data.csv")
    public void digitsSumChecking(int number, int digitSum) {
        Assert.assertEquals(nw.digitSum(number), digitSum);
    }

}
