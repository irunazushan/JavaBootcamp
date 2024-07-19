package ex00;

class MyThread extends Thread {
    private final String name;
    private final int amountOfRepeats;

    MyThread(String name, int amountOfRepeats) {
        this.name = name;
        this.amountOfRepeats = amountOfRepeats;
    }

    @Override
    public void run() {
        for (int i = 0; i < amountOfRepeats; ++i) {
            System.out.println(name);
        }
    }
}