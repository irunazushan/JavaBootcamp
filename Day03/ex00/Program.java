package ex00;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        String prefix = "--count=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.err.println("Incorrect arguments are given");
            System.exit(-1);
        }

        int amount = Integer.parseInt(args[0].substring(prefix.length()));
        MyThread eggThread = new MyThread("Egg", amount);
        MyThread henThread = new MyThread("Hen", amount);

        eggThread.start();
        henThread.start();
        eggThread.join();
        henThread.join();

        for (int i = 0; i < amount; ++i) {
            System.out.println("Human");
        }
    }
}


