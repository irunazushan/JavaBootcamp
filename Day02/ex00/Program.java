package ex00;

public class Program {
    public static void main(String[] args) {
        SignatureReader sR = new SignatureReader();
        sR.initSignatures("ex00/signatures.txt");
        sR.runSignatureReader();
    }
}
