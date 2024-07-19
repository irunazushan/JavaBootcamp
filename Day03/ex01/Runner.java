package ex01;

class Runner implements Runnable {
    private final String name;
    private final int amountOfRepeats;
    private final Object lock;
    private final boolean isEgg;
    static boolean isEggTurn = true;

    Runner(String name, int amountOfRepeats, Object lock, boolean isEgg) {
        this.name = name;
        this.amountOfRepeats = amountOfRepeats;
        this.lock = lock;
        this.isEgg = isEgg;
    }

    @Override
    public void run() {
        for (int i = 0; i < amountOfRepeats; ++i) {
            synchronized (lock) {
                while (isEggTurn != isEgg) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(name);
                isEggTurn = !isEggTurn;
                lock.notify();
            }
        }
    }

}
