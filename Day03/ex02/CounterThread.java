package ex02;

public class CounterThread implements Runnable {
    private final int startIndex;
    private final int endIndex;
    private final int threadNumber;
    private static int[] array;
    private static int totalResult;

    public CounterThread(int threadNumber, int[] array, int startIndex, int endIndex) {
        this.threadNumber = threadNumber;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.array = array;
    }

    private static int sumOfElements(int startInd, int endInd) {
        int preResult = 0;
        for (int i = startInd; i < endInd; ++i) {
            preResult += array[i];
        }
        return preResult;
    }

    private synchronized void sumResultsAndPrint(int preResult) {
        System.out.printf("Thread %d: from %d to %d sum is %d\n", threadNumber, startIndex, endIndex - 1, preResult);
        totalResult += preResult;
    }

    public static int getTotalResult() {
        return totalResult;
    }

    @Override
    public void run() {
        int preResult = sumOfElements(startIndex, endIndex);
        sumResultsAndPrint(preResult);
    }
}
