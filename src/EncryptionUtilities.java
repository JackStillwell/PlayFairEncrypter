/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import java.util.ArrayList;

public class EncryptionUtilities {

    /* Converts a string to an Ascii Array */
    public static ArrayList<Integer> StringToAsciiArray(String input) {
        assert input != null;

        ArrayList<Integer> asciiArray = new ArrayList<>();

        for (char c : input.toCharArray()) {
            asciiArray.add(((int) c));
        }

        return asciiArray;
    }


    /*

        Converts an Ascii Array to a AsciiPair Array

        Accounts for uneven amounts of Ascii

    */
    public static ArrayList<AsciiPair>
    AsciiArrayToAsciiPairArray(ArrayList<Integer> input) {
        if (input.size() % 2 != 0) {
            // TODO: Deal with uneven amount of integers by adding something
            // NOTE: Whatever is added will need to be dealt with upon decryption
        }

        ArrayList<AsciiPair> asciiPairArray = new ArrayList<>();

        for (int i = 0; i < (input.size() / 2); i++) {
            asciiPairArray.add(
                    new AsciiPair(input.get(((i * 2) - 1)), input.get(i * 2))
            );
        }

        return asciiPairArray;
    }

    public static ArrayList<Integer> asciiArrayToBinaryArray(ArrayList<Integer> asciiArray)
    {
        ArrayList<Integer> binaryArray = new ArrayList<>();

        for(int i : asciiArray)
        {
            binaryArray.addAll(asciiTo8BitBinary(asciiArray.get(i)));
        }

        return binaryArray;
    }

    public static ArrayList<Integer> asciiTo8BitBinary(int num)
    {
        ArrayList<Integer> eightBit = new ArrayList<>();

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

    public static Integer eightBitBinarytoAscii(ArrayList<Integer> eightBitBinary)
    {
        int ascii = 0;

        for(int i = 0; i < eightBitBinary.size(); i++)
        {
            ascii += (eightBitBinary.get(i) * (8-i));
        }

        return ascii;
    }
}