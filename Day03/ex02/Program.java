package ex02;

public class Program {

    private static int[] array;
    private static final String ARRAY_SIZE_PREFIX = "--arraySize=";
    private static final String THREADS_COUNT_PREFIX = "--threadsCount=";
    private static final int MAX_RANDOM_NUMBER = 1000;
    private static final int MIN_RANDOM_NUMBER = -1000;

    public static void main(String[] args) {

        if (!checkValidity(args)) {
            System.err.println("Invalid arguments are given");
            return;
        }

        int arraySize = Integer.parseInt(args[0].substring(ARRAY_SIZE_PREFIX.length()));
        int amountOfThreads = Integer.parseInt(args[1].substring(THREADS_COUNT_PREFIX.length()));

        if (arraySize < amountOfThreads) {
            System.err.println("The number of threads cannot be less than the size of the array!");
            return;
        }
        array = getRandomArray(arraySize, false);

        Thread[] threads = addThreads(array, amountOfThreads);
        startThreads(threads);
        joinThreads(threads);

        System.out.println("Sum by threads: " + CounterThread.getTotalResult());

    }

    private static boolean checkValidity(String[] args) {
        if (args.length == 2 &&
                args[0].startsWith(ARRAY_SIZE_PREFIX) &&
                args[1].startsWith(THREADS_COUNT_PREFIX) &&
                (Integer.parseInt(args[0].substring(ARRAY_SIZE_PREFIX.length())) < 2000000)) {
            return true;
        }
        return false;
    }

    private static int[] getRandomArray(int arraySize, boolean isRandom) {
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; ++i) {
            int num = isRandom ? (int)(Math.random() * (MAX_RANDOM_NUMBER - MIN_RANDOM_NUMBER + 1) + MIN_RANDOM_NUMBER) : 1;
            array[i] = num;
        }
        return array;
    }

    private static Thread[] addThreads (int[] array, int amountOfThreads) {
        int arraySize = array.length;
        Thread[] threads = new Thread[amountOfThreads];
        int step = arraySize / amountOfThreads;
        int startIndex = 0;
        for (int i = 0; i < amountOfThreads; ++i) {
            int lastIndex = startIndex + step;
            if (i == amountOfThreads - 1) {
                lastIndex -= (lastIndex - (arraySize));
            }
            threads[i] = new Thread(new CounterThread(i + 1,array, startIndex, lastIndex));
            startIndex = lastIndex;
        }
        return threads;
    }

    public static void startThreads(Thread[] threads) {
        for (Thread el : threads) {
            el.start();
        }
    }

    public static void joinThreads(Thread[] threads) {
        for (Thread el : threads) {
            try {
                el.join();
            } catch (InterruptedException e) {
                el.interrupt();
            }
        }
    }
}

