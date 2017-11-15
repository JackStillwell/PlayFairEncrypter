/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class EncryptionMatrixMkIICmd extends EncryptionMatrixMkII {

    /* TODO: CATCH AND LOG ASSERTION ERRORS */
    /* TODO: ADD ASSERTIONS WHERE ANY BREAK POSSIBLE */

    public static final int __GRIDSIZE__ = 16;

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
            System.out.print("Beginning Grid Encryption...\n");

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

                System.out.print("Completed Loop " +
                        (i+1) + "/" + (keyArray.size()/2) +
                        " of Grid Encryption\n");
            }

            System.out.print("Finished Grid Encryption\n");
        }

        else
        {
            System.out.print("Beginning Grid Decryption...\n");

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

                System.out.print("Completed Loop " +
                        ((keyArray.size()/2)-i) + "/" + (keyArray.size()/2) +
                        " of Grid Decryption\n");
            }

            System.out.print("Finished Grid Decryption\n");
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

        System.out.print("Beginning Xor...\n");

        DecimalFormat fmt = new DecimalFormat("#");
        List<Integer> asciiArray = EncryptionUtilities.stringToAsciiArray(password);
        StringBuilder passwordNumStringBuilder = new StringBuilder();

        List<AsciiPair> asciiPairArray = EncryptionUtilities.asciiArrayToAsciiPairArray(asciiArray);

        // ensure unique xor generation per unique password-keyfile combo


        System.out.print("Beginng Password Encryption...\n");
        asciiPairArray = cyclePlayFairFoursquareCipher(asciiPairArray, keyfile, true);
        System.out.print("Completed Password Encryption\n");

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

        System.out.print("Finished Xor\n");

        return outputArray;
    }
}
