package ex01;

public class Program {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Incorrect command-line arguments");
            System.exit(-1);
        }
        SimilarityChecker sC = new SimilarityChecker();
        sC.runChecker(args[0], args[1]);
    }
}
