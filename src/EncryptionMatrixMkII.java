/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import java.util.ArrayList;
import java.util.Random;

public class EncryptionMatrixMkII {

    /* TODO: CATCH AND LOG ASSERTION ERRORS */
    /* TODO: ADD ASSERTIONS WHERE ANY BREAK POSSIBLE */

    static final int __GRIDSIZE__ = 15;

    /* employs the FourSquare Encryption Technique */
    public ArrayList<Integer> encrypt(
                                ArrayList<AsciiPair> input,
                                int[][] alphabet,
                                int[][] key1,
                                int[][] key2)
    {
        ArrayList<Integer> output = new ArrayList<>();

        for (AsciiPair anInput : input) {
            Integer letter1 = anInput.one;
            Integer letter2 = anInput.two;

            int letter1Row = -1;
            int letter1Column = -1;
            int letter2Row = -1;
            int letter2Column = -1;

            for (int j = 0; j < __GRIDSIZE__; j++) {
                for (int k = 0; k < __GRIDSIZE__; k++) {
                    if (alphabet[j][k] == (letter1)) {
                        letter1Row = j;
                        letter1Column = k;
                    }

                    if (alphabet[j][k] == (letter2)) {
                        letter2Row = j;
                        letter2Column = k;
                    }
                }
            }

            int encryptedLetter1 = key1[letter2Row][letter1Column];
            int encryptedLetter2 = key2[letter1Row][letter2Column];

            output.add(encryptedLetter1);
            output.add(encryptedLetter2);
        }

        return output;
    }

    /*
        Takes a password and a binary array

        Seeds a random generator with ascii from the password

        uses that generator to create a binary array as long as
        the input binary array

        then xors the two arrays together and outputs

        encryption and decryption are the same operation!
    */
    public ArrayList<Integer> xorCipher(
                                String password,
                                ArrayList<Integer> inputArray)
    {
        ArrayList<Integer> asciiArray = EncryptionUtilities.StringToAsciiArray(password);
        StringBuilder passwordNumString = new StringBuilder();

        for(int i : asciiArray)
        {
            passwordNumString.append(Integer.toString(i));
        }

        Random passwordSeedGenerator = new Random(
                Long.parseLong(passwordNumString.toString())
        );

        ArrayList<Integer> passwordArray = new ArrayList<>();

        for(int i = 0; i < inputArray.size(); i++)
        {
            passwordArray.add(passwordSeedGenerator.nextInt(1));
        }

        ArrayList<Integer> outputArray = new ArrayList<>();

        for(int i = 0; i < inputArray.size(); i++)
        {
            outputArray.add(
                    inputArray.get(i)^passwordArray.get(i)
            );
        }

        return outputArray;
    }
}