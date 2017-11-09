/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EncryptionUtilities {

    /* Converts a string to an Ascii Array */
    public static List<Integer> stringToAsciiArrayOLD (String input) throws Exception {
        assert input != null;

        byte[] inputBytes = input.getBytes("UTF8");

        input = new String(inputBytes, "UTF8");

        List<Integer> asciiArray = new ArrayList<>();

        for (char c : input.toCharArray()) {
            asciiArray.add(((int) c));

            if(((int) c) > 255 || ((int) c) < 0)
            {
                throw new Exception("error converting to ascii \n" +
                                    "char: " + c + "\n" +
                                    "int: " + ((int) c));
            }
        }

        return asciiArray;
    }

    public static List<Integer> stringToAsciiArray(String input) throws Exception {
        assert input != null;

        // converts to UTF-8 format

        // & 0xFF replicates an unsigned int

        byte[] inputBytes = input.getBytes("UTF8");

        List<Integer> asciiArray = new ArrayList<>();

        for (byte b : inputBytes) {
            asciiArray.add(((int) b) & 0xFF);

            if(((int) b & 0xFF) > 255 || ((int) b & 0xFF) < 0)
            {
               throw new Exception("error converting to ascii \n" +
                        "char: " + ((char) b & 0xFF) + "\n" +
                        "int: " + ((int) b & 0xFF));
            }
        }

        return asciiArray;
    }

    public static String asciiArrayToString(List<Integer> input) {
        StringBuilder output = new StringBuilder();

        for (int num : input) {
            output.append((char) num);
        }

        return output.toString();
    }

    /*

        Converts an Ascii Array to a AsciiPair Array

        Accounts for uneven amounts of Ascii

    */
    public static List<AsciiPair>
    asciiArrayToAsciiPairArray(List<Integer> input) {
        if (input.size() % 2 != 0) {
            // TODO: Deal with uneven amount of integers by adding something
            // NOTE: Whatever is added will need to be dealt with upon decryption

            // Add a space to the end
            input.add(32);
        }

        List<AsciiPair> asciiPairArray = new ArrayList<>();

        for (int i = 0; i < (input.size() / 2); i++) {
            asciiPairArray.add(
                    new AsciiPair(input.get((i * 2)), input.get((i * 2) + 1))
            );
        }

        return asciiPairArray;
    }

    public static List<Integer> asciiPairArrayToAsciiArray(List<AsciiPair> asciiPairArray)
    {
	    List<Integer> asciiArray = new ArrayList();
	    for(AsciiPair ap : asciiPairArray)
	    {
	    	asciiArray.add(ap.one);
		    asciiArray.add(ap.two);
	    }

	    return asciiArray;
    }

    public static List<Integer> asciiArrayToBinaryArray(List<Integer> asciiArray) {
        List<Integer> binaryArray = new ArrayList<>();

        for (int i = 0; i < asciiArray.size(); i++) {
            binaryArray.addAll(asciiTo8BitBinary(asciiArray.get(i)));
        }

        return binaryArray;
    }

    /*

        Converts a Binary Array to an Ascii Array

        Accounts for binary not divisible by 8

    */
    public static List<Integer> binaryArrayToAsciiArray(List<Integer> binaryArray) {
        if (binaryArray.size() % 8 != 0) {
            // TODO: DEAL WITH BINARY ARRAY NOT DIVISIBLE BY 8
        }

        List<Integer> asciiArray = new ArrayList<>();

        for (int i = 1; i < ((binaryArray.size() / 8) + 1); i++) {
            asciiArray.add(
                    eightBitBinaryToAscii(
                            binaryArray.subList(((i - 1) * 8), (i * 8))
                    )
            );
        }

        return asciiArray;
    }

    public static List<Integer> asciiTo8BitBinary(int num) {
        List<Integer> eightBit = new ArrayList<>();

        while (num > 0) {
            eightBit.add(0, num % 2);
            num = num / 2;
        }

        int zerosNeeded = 8 - eightBit.size();

        for (int i = zerosNeeded; i > 0; i--) {
            eightBit.add(0, 0);
        }

        return eightBit;
    }

    public static Integer eightBitBinaryToAscii(List<Integer> eightBitBinary) {
        int ascii = 0;

        for (int i = 0; i < eightBitBinary.size(); i++) {
            ascii += (eightBitBinary.get(i) * (Math.pow(2, (7-i))));
        }

        return ascii;
    }

    public static int[][] asciiArrayToIntGrid(List<Integer> input) {
        //TODO: catch assertion failure and log message
        assert input.size() == 256;

        int gridsize = EncryptionMatrixMkII.__GRIDSIZE__;
        int[][] grid = new int[gridsize][gridsize];

        int masterCount = 0;

        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                grid[i][j] = input.get(masterCount);
                masterCount++;
            }
        }

        return grid;
    }

    public static List<Integer> standardAsciiArray() {
        List<Integer> standard = new ArrayList<>();

        for (int i = 0; i < 256; i++) {
            standard.add(i);
        }

        return standard;
    }

    public static List<Integer> binaryStringToBinaryArray(String input)
    {
        List<Integer> output = new ArrayList<>();
        List<Integer> binary = new ArrayList<>();

        binary.add(0);
        binary.add(1);

        for(char c : input.toCharArray())
        {
            output.add((int) (c - '0'));
        }

        output.retainAll(binary);

        return output;
    }

    public static String binaryArrayToBinaryString(List<Integer> binaryArray)
    {
        StringBuilder binaryString = new StringBuilder();

        for(int i : binaryArray)
        {
            binaryString.append(i);
        }

        return binaryString.toString();
    }
}
