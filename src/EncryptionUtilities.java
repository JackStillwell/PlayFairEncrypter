/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class EncryptionUtilities {

    /* Converts a string to an Ascii Array */
    public static List<Integer> StringToAsciiArray(String input) {
        assert input != null;

        List<Integer> asciiArray = new ArrayList<>();

        for (char c : input.toCharArray()) {
            asciiArray.add(((int) c));
        }

        return asciiArray;
    }

    public static String AsciiArrayToString(List<Integer> input)
    {
        StringBuilder output = new StringBuilder();

        for (int num : input)
        {
            output.append((char) num);
        }

        return output.toString();
    }

    /*

        Converts an Ascii Array to a AsciiPair Array

        Accounts for uneven amounts of Ascii

    */
    public static List<AsciiPair>
    AsciiArrayToAsciiPairArray(List<Integer> input) {
        if (input.size() % 2 != 0) {
            // TODO: Deal with uneven amount of integers by adding something
            // NOTE: Whatever is added will need to be dealt with upon decryption
        }

        List<AsciiPair> asciiPairArray = new ArrayList<>();

        for (int i = 0; i < (input.size() / 2); i++) {
            asciiPairArray.add(
                    new AsciiPair(input.get(((i * 2) - 1)), input.get(i * 2))
            );
        }

        return asciiPairArray;
    }

    public static List<Integer> asciiArrayToBinaryArray(List<Integer> asciiArray)
    {
        List<Integer> binaryArray = new ArrayList<>();

        for(int i : asciiArray)
        {
            binaryArray.addAll(asciiTo8BitBinary(asciiArray.get(i)));
        }

        return binaryArray;
    }

    /*

        Converts a Binary Array to an Ascii Array

        Accounts for binary not divisible by 8

    */
    public static List<Integer> binaryArrayToAsciiArray(List<Integer> binaryArray)
    {
        if(binaryArray.size() % 8 != 0)
        {
            // TODO: DEAL WITH BINARY ARRAY NOT DIVISIBLE BY 8
        }

        List<Integer> asciiArray = new ArrayList<>();

        for(int i = 1; i < (binaryArray.size()/8); i++)
        {
            asciiArray.add(
                    eightBitBinaryToAscii(
                            binaryArray.subList(((i-1)*8), (i*8))
                    )
            );
        }

        return asciiArray;
    }

    public static List<Integer> asciiTo8BitBinary(int num)
    {
        List<Integer> eightBit = new ArrayList<>();

        while(num>0)
        {
            eightBit.add(0, num%2);
            num=num/2;
        }

        int zerosNeeded = 8 - eightBit.size();

        for(int i=zerosNeeded; i>0; i--)
        {
            eightBit.add(0, 0);
        }

        return eightBit;
    }

    public static Integer eightBitBinaryToAscii(List<Integer> eightBitBinary)
    {
        int ascii = 0;

        for(int i = 0; i < eightBitBinary.size(); i++)
        {
            ascii += (eightBitBinary.get(i) * (Math.pow(2, (8-i))));
        }

        return ascii;
    }

    public static int[][] GridBuilder(List<Integer> input)
    {
        //TODO: catch assertion failure and log message
        assert input.size() == 256;

        int gridsize = EncryptionMatrixMkII.__GRIDSIZE__;
        int[][] grid = new int[gridsize][gridsize];

        int masterCount = 0;

        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                grid[i][i] = input.get(masterCount);
                masterCount++;
            }
        }

        return grid;
    }
}