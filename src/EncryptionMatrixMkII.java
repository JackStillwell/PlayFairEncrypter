/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EncryptionMatrixMkII {

    /* TODO: CATCH AND LOG ASSERTION ERRORS */
    /* TODO: ADD ASSERTIONS WHERE ANY BREAK POSSIBLE */

    public static final int __GRIDSIZE__ = 15;

    /* employs the a Playfair-FourSquare Encryption Technique */
    public static List<AsciiPair> playfairFoursquareCipher(
                                List<AsciiPair> input,
                                int[][] alphabet,
                                int[][] key1,
                                int[][] key2)
    {
        List<AsciiPair> output = new ArrayList<>();

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

            output.add(new AsciiPair(encryptedLetter1,encryptedLetter2));
        }

        return output;
    }

    public List<AsciiPair> cyclePlayFairFoursquareCipher(
            List<AsciiPair> asciiArray,
            List<List<Integer>> keyArray)
    {
        int[][] standardGrid = EncryptionUtilities
                                .asciiArrayToIntGrid(
                                        EncryptionUtilities.standardAsciiArray()
                                );
        List<AsciiPair> encryptedArray = asciiArray;

        for(int i = 0; i < ((keyArray.size())/2); i++)
        {
            int[][] key1 = EncryptionUtilities
                            .asciiArrayToIntGrid(keyArray.get(i*2));
            int[][] key2 = EncryptionUtilities
                            .asciiArrayToIntGrid(keyArray.get((i*2)+1));
            encryptedArray = playfairFoursquareCipher(
                    encryptedArray,
                    standardGrid,
                    key1,
                    key2);
        }

        return encryptedArray;
    }

    /*
        Takes a password and a binary array

        Seeds a random generator with ascii from the password

        uses that generator to create a binary array as long as
        the input binary array

        then xors the two arrays together and outputs

        encryption and decryption are the same operation!
    */
    public static ArrayList<Integer> xorCipher(
                                String password,
                                List<Integer> inputArray)
    {
        List<Integer> asciiArray = EncryptionUtilities.StringToAsciiArray(password);
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
            passwordArray.add(passwordSeedGenerator.nextInt(2));
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

    public static List<List<Integer>> keyFileGenerator(int numberToGenerate)
    {
        Random randomCountGenerator = new Random();

        long randomSeed = -1;

        while(randomSeed < 0)
        {
            for (int i = 0; i < randomCountGenerator.nextInt(100); i++) {
                Random loopGenerator = new Random();

                randomSeed = loopGenerator.nextLong();
            }
        }

        Random reallyRandomGenerator = new Random(randomSeed);

        List<Integer> choices = new ArrayList<>();
        List<Integer> currentChoices = new ArrayList<>();

        for(int i = 0; i < 256; i++)
        {
            choices.add(i);
        }

        List<Integer> key = new ArrayList<>();
        List<List<Integer>> keyFile = new ArrayList<>();

        for(int i = 0; i < numberToGenerate; i++)
        {
            key.clear();
            currentChoices.addAll(choices);

            // build key
            for(int j = 256; j > 0; j--)
            {
                int position = reallyRandomGenerator.nextInt(j);

                key.add(currentChoices.get(position));

                currentChoices.remove(position);
            }

            // add key to list
            keyFile.add(key);
        }

        return keyFile;
    }
}