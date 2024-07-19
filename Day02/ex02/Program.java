package ex02;

public class Program {
    public static void main(String[] args) {

        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.err.println("Incorrect argument");
            System.exit(-1);
        }

        FileManager fM = new FileManager(args[0].substring(17));
        fM.runFileManager();

    }
}
