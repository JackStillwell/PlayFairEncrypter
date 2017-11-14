import java.util.List;
import java.util.concurrent.Callable;

public class DecryptPair implements Callable<ThreadedAsciiPair> {

    private static final int __GRIDSIZE__ = 16;
    private Thread t;

    private AsciiPair input;
    private int[][] alphabet;
    private int[][] key1;
    private int[][] key2;
    private int location;

    public DecryptPair(AsciiPair _input,
                       int[][] _alphabet,
                       int[][] _key1,
                       int[][] _key2,
                       int _location)
    {
        input = _input;
        alphabet = _alphabet;
        key1 = _key1;
        key2 = _key2;
        location = _location;
    }

    @Override
    public ThreadedAsciiPair call() throws Exception {
        Integer letter1 = input.one;
        Integer letter2 = input.two;

        int letter1Row = -1;
        int letter1Column = -1;
        int letter2Row = -1;
        int letter2Column = -1;

        for (int j = 0; j < __GRIDSIZE__; j++) {
            for (int k = 0; k < __GRIDSIZE__; k++) {
                if (key1[j][k] == (letter1)) {
                    letter1Row = j;
                    letter1Column = k;
                }

                if (key2[j][k] == (letter2)) {
                    letter2Row = j;
                    letter2Column = k;
                }
            }
        }

        int encryptedLetter1;
        int encryptedLetter2;

        encryptedLetter1 = alphabet[letter2Row][letter1Column];
        encryptedLetter2 = alphabet[letter1Row][letter2Column];

        return new ThreadedAsciiPair(new AsciiPair(encryptedLetter1,encryptedLetter2), location);
    }
}
