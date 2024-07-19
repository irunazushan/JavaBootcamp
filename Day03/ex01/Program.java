package ex01;


public class Program {
    public static void main(String[] args) throws InterruptedException {
        String prefix = "--count=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.err.println("Incorrect arguments are given");
            System.exit(-1);
        }

        Object lock = new Object();
        int amount = Integer.parseInt(args[0].substring(prefix.length()));
        Thread eggThread = new Thread(new Runner("Egg", amount, lock, true));

        Thread henThread = new Thread(new Runner("Hen", amount, lock, false));

        eggThread.start();
        henThread.start();


    }
}


