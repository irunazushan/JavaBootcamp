package ex05;

public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();
        boolean devMode = args.length == 1 && args[0].equals("--profile=dev");
        menu.runMenu(devMode);
    }
}
