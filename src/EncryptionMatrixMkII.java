/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.*;
import java.util.concurrent.*;

public class EncryptionMatrixMkII {

    /* TODO: CATCH AND LOG ASSERTION ERRORS */
    /* TODO: ADD ASSERTIONS WHERE ANY BREAK POSSIBLE */

    public static final int __GRIDSIZE__ = 16;

    /* employs the a Playfair-FourSquare Encryption Technique */
    public static List<AsciiPair> playfairFoursquareCipher(
                                List<AsciiPair> input,
                                int[][] alphabet,
                                int[][] key1,
                                int[][] key2,
                                boolean encrypt) throws Exception
    {
        ExecutorService executorService =
                Executors.newCachedThreadPool();

        CompletionService<ThreadedAsciiPair> completionService =
                new ExecutorCompletionService<ThreadedAsciiPair>(executorService);

        AsciiPair[] output = new AsciiPair[input.size()];


        if(encrypt) {
            for (int i = 0; i < input.size(); i++) {

                EncryptPair thread = new EncryptPair(input.get(i),
                        alphabet,
                        key1,
                        key2,
                        i);

                completionService.submit(thread);
            }
        }

        else
        {
            for (int i = 0; i < input.size(); i++) {

                DecryptPair thread = new DecryptPair(input.get(i),
                        alphabet,
                        key1,
                        key2,
                        i);

                completionService.submit(thread);
            }
        }

        int complete = 0;

        while(complete < input.size())
        {
            Future<ThreadedAsciiPair> outPair = completionService.take();

            output[outPair.get().index] = outPair.get().asciiPair;

            complete++;
        }

        return Arrays.asList(output);
    }

    public static List<AsciiPair> cyclePlayFairFoursquareCipher(
            List<AsciiPair> asciiArray,
            List<List<Integer>> keyArray,
            boolean encrypt) throws Exception
    {
        int[][] standardGrid = EncryptionUtilities
                                .asciiArrayToIntGrid(
                                        EncryptionUtilities.standardAsciiArray()
                                );
        List<AsciiPair> processedArray = asciiArray;

        if(encrypt)
        {
            for (int i = 0; i < ((keyArray.size()) / 2); i++) {
                int[][] key1 = EncryptionUtilities
                        .asciiArrayToIntGrid(keyArray.get(i * 2));
                int[][] key2 = EncryptionUtilities
                        .asciiArrayToIntGrid(keyArray.get((i * 2) + 1));
                processedArray = playfairFoursquareCipher(
                        processedArray,
                        standardGrid,
                        key1,
                        key2,
                        encrypt);
            }
        }

        else
        {
            for(int i = ((keyArray.size()/2)-1); i >= 0; i--) {
                int[][] key1 = EncryptionUtilities
                        .asciiArrayToIntGrid(keyArray.get(i * 2));
                int[][] key2 = EncryptionUtilities
                        .asciiArrayToIntGrid(keyArray.get((i * 2) + 1));
                processedArray = playfairFoursquareCipher(
                        processedArray,
                        standardGrid,
                        key1,
                        key2,
                        encrypt);
            }
        }

        return processedArray;
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
                                List<List<Integer>> keyfile,
                                List<Integer> binaryArray) throws Exception {

        DecimalFormat fmt = new DecimalFormat("#");
        List<Integer> asciiArray = EncryptionUtilities.stringToAsciiArray(password);
        StringBuilder passwordNumStringBuilder = new StringBuilder();

        List<AsciiPair> asciiPairArray = EncryptionUtilities.asciiArrayToAsciiPairArray(asciiArray);

        // ensure unique xor generation per unique password-keyfile combo

        asciiPairArray = EncryptionMatrixMkII
                .cyclePlayFairFoursquareCipher(asciiPairArray, keyfile, true);

        asciiArray = EncryptionUtilities.asciiPairArrayToAsciiArray(asciiPairArray);

        for(int i : asciiArray)
        {
            passwordNumStringBuilder.append(Integer.toString(i));
        }

        String passwordNumString = passwordNumStringBuilder.toString();
        int passwordNumStringLength = passwordNumString.length();

        Float passwordNumFloat = Float.valueOf(passwordNumString);

        passwordNumString = fmt.format(passwordNumFloat);

        while(passwordNumString.length() > 14)
        {
            passwordNumFloat = (float) Math.pow(passwordNumFloat, .5);

             passwordNumString = fmt.format(passwordNumFloat);
        }

        Random passwordSeedGenerator = new Random(
                passwordNumFloat.longValue()
        );

        ArrayList<Integer> passwordArray = new ArrayList<>();

        for(int i = 0; i < binaryArray.size(); i++)
        {
            passwordArray.add(passwordSeedGenerator.nextInt(2));
        }

        ArrayList<Integer> outputArray = new ArrayList<>();

        for(int i = 0; i < binaryArray.size(); i++)
        {
            outputArray.add(
                    binaryArray.get(i)^passwordArray.get(i)
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

        List<List<Integer>> keyFile = new ArrayList<>();

        for(int i = 0; i < numberToGenerate; i++)
        {
            currentChoices.addAll(choices);
            List<Integer> key = new ArrayList<>();

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

    public static String encryptSequence(String input, List<List<Integer>> keyFile, String password) throws Exception {
        List<Integer> asciiArray =
                EncryptionUtilities.stringToAsciiArray(input);

        List<AsciiPair> asciiPairArray =
                EncryptionUtilities.asciiArrayToAsciiPairArray(asciiArray);

        asciiPairArray = EncryptionMatrixMkII
                .cyclePlayFairFoursquareCipher(asciiPairArray, keyFile, true);

        asciiArray = EncryptionUtilities
                .asciiPairArrayToAsciiArray(asciiPairArray);

        List<Integer> binaryArray = EncryptionUtilities
                .asciiArrayToBinaryArray(asciiArray);

        binaryArray = EncryptionMatrixMkII
                .xorCipher(password, keyFile, binaryArray);

        return EncryptionUtilities.binaryArrayToBinaryString(binaryArray);
    }

    public static String decryptSequence(String input, List<List<Integer>> keyFile, String password) throws Exception {
        List<Integer> binaryArray = EncryptionUtilities
                .binaryStringToBinaryArray(input);

        binaryArray = EncryptionMatrixMkII
                .xorCipher(password, keyFile, binaryArray);

        List<Integer> asciiArray = EncryptionUtilities
                .binaryArrayToAsciiArray(binaryArray);

        List<AsciiPair> asciiPairArray = EncryptionUtilities
                .asciiArrayToAsciiPairArray(asciiArray);

        asciiPairArray = EncryptionMatrixMkII
                .cyclePlayFairFoursquareCipher(asciiPairArray,keyFile,false);

        asciiArray = EncryptionUtilities.asciiPairArrayToAsciiArray(asciiPairArray);

        return EncryptionUtilities.asciiArrayToString(asciiArray);
    }
}
