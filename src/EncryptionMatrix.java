import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jack on 11/12/2015.
 */
public class EncryptionMatrix
{
    public ArrayList<String> encrypt(ArrayList<String> input, char[][] alphabet, char[][] key1, char[][]key2)
    {
        ArrayList<String> output = new ArrayList<String>();

        for(int i=0; i<input.size(); i++ )
        {
            char letter1 = input.get(i).charAt(0);
            char letter2 = input.get(i).charAt(1);

            int letter1Row = -1;
            int letter1Column = -1;
            int letter2Row = -1;
            int letter2Column = -1;

            for(int j = 0; j < 7; j++)
            {
                for(int k=0; k < 7; k++)
                {
                    if(alphabet[j][k]==(letter1))
                    {
                        letter1Row = j;
                        letter1Column = k;
                    }

                    if (alphabet[j][k]==(letter2))
                    {
                        letter2Row = j;
                        letter2Column = k;
                    }
                }
            }

            char encryptedLetter1 = key1[letter2Row][letter1Column];
            char encryptedLetter2 = key2[letter1Row][letter2Column];

            output.add(encryptedLetter1 + "" + encryptedLetter2);
        }

        return output;
    }

    public String stringConditioner(String input)
    {
        String output = "";
        String abc= "";

        output =  input.toLowerCase();

        output = output.replace(' ', '_');

        ArrayList<String> genericArray = alphanumerisymbolic("");

        for(int i=0; i<output.length(); i++)
        {
            if(genericArray.indexOf(output.substring(i,i+1))!= -1)
                abc += output.substring(i,i+1);
        }

        output = abc;

        return output;
    }

    public ArrayList<String> toArray (String input)
    {
        ArrayList<String> output = new ArrayList<String>();

        if (input.length() % 2 == 1)
            input += "z";

        for (int i = 0; i < input.length(); i+=2)
        {
            output.add(input.substring(i, i + 2));
        }

        return output;
    }

    public ArrayList<String> toArrayKey (String input)
    {
        ArrayList<String> output = new ArrayList<String>();

        if(input.length()%2==1)
            input += "z";

        for (int i = 0; i < input.length()-1; i+=2)
        {
            output.add(input.substring(i, i + 2));
        }

        return output;
    }

    char[][] grid (String input) //if generating a key, put the key as the input. If generating a basic array, put 'aa' as the input
    {
        char[][] outputArray = new char[7][7];

        ArrayList<String> withRepeats = alphanumerisymbolic(input);

//        System.out.println(withRepeats);  DEBUG

        ArrayList<String> toAdd = new ArrayList<String>();

        for(int i = 0; i<withRepeats.size(); i++) //removes any repeat characters from the array
        {
            if(toAdd.indexOf(withRepeats.get(i)) == -1)

                toAdd.add(withRepeats.get(i));
        }

//        System.out.println(toAdd); DEBUG

        for(int i=0; i<toAdd.size();)
        {
            for(int j= 0; j<7; j++)
            {
                for(int k=0; k<7; k++)
                {
                    outputArray[j][k] = (toAdd.get(i++)).toCharArray()[0];
                }
            }
        }

/*        String debug = "";

        for(int i=0; i<7; i++)
        {
            debug = "";
            for(int j=0; j<7; j++)
            {
                debug += outputArray[i][j] + " ";
            }
            System.out.print(debug + "\n");
        }
*/
        return outputArray;
    }

    public ArrayList<String> alphanumerisymbolic (String input) //generates an alphanumerisymbolic ArrayList for entry into a 2d 7x7 array, for key array, enter the key, for standard, enter ""
    {
        String includedCharacters = input + "abcdefghijklmnopqrstuvwxyz0123456789.!?,:;()'-_/\""; //NOTE: this requires all spaces be replaced with '_' prior to processing

        ArrayList<String> output = new ArrayList<String>();

        for(int i = 0; i<includedCharacters.length();i++)
            output.add(includedCharacters.substring(i,i+1));

        return output;
    }

    public String readInFile(Scanner input)
    {
        Scanner file;

        file = input;

        String output = "";

        while(file.hasNextLine()) //while there is a line left to read
        {
            String line = file.nextLine(); //read in the line

            output+=line;
        }

        return output;

    }

    public ArrayList<String> readInKeySet(Scanner input)
    {
        Scanner file;

        file = input;

        ArrayList<String> output = new ArrayList<String>();

        int lastSpace = 0;

        while(file.hasNextLine()) //while there is a line left to read
        {
            String line = file.nextLine(); //read in the line

            for(int i=0; i<line.length(); i++) //reads through the list character by character
            {
                if(lastSpace == 0 && line.substring(i, i + 1).equals("^")) //deals with the first key
                {
                    output.add(line.substring(0, i));

                    lastSpace = i; //update the location of the last space
                }

                else if (line.substring(i, i + 1).equals("^")) //when there is a '^', read everything in from the last '^' as a new object in the array
                {
                    output.add(line.substring(lastSpace + 1, i));

                    lastSpace = i; //update the location of the last space
                }
            }
        }

        return output;
    }

    public ArrayList<String> decrypt(ArrayList<String> input, char[][] alphabet, char[][] key1, char[][]key2)
    {
        ArrayList<String> output = new ArrayList<String>();

        for(int i=0; i<input.size(); i++ )
        {
            char letter1 = input.get(i).charAt(0);
            char letter2 = input.get(i).charAt(1);

            int letter1Row = -1;
            int letter1Column = -1;
            int letter2Row = -1;
            int letter2Column = -1;

            for(int j = 0; j < 7; j++)
            {
                for(int k=0; k < 7; k++)
                {
                    if(key1[j][k]==(letter1))
                    {
                        letter1Row = j;
                        letter1Column = k;
                    }

                    if (key2[j][k]==(letter2))
                    {
                        letter2Row = j;
                        letter2Column = k;
                    }
                }
            }

            char decryptedLetter1 = alphabet[letter2Row][letter1Column];
            char decryptedLetter2 = alphabet[letter1Row][letter2Column];

            output.add(decryptedLetter1 + "" + decryptedLetter2);
        }

        return output;
    }

    public ArrayList<String> encryptKeyArray(ArrayList<String> inArray)
    {
        ArrayList<String> outArray = new ArrayList<String>();

        outArray.add(inArray.get(0)); //adds the first two keys, which are not encrypted
        outArray.add(inArray.get(1));

        for(int i = 2; i<inArray.size(); i++) //starting with the 3rd key, until the last key in the array
        {
            char[][] key1 = grid(inArray.get(i-2)); //makes key1 with the key 2 before the current key
            char[][] key2 = grid(inArray.get(i-1)); //makes key2 with the key 1 before the current key
            char[][] alphabet = grid("aa"); //standard alphanumerisymbolic 7x7 array

            ArrayList<String> inKey = toArrayKey(inArray.get(i)); //puts the current key into an array for encryption

            ArrayList<String> outKey = encrypt(inKey, alphabet, key1, key2); //encrypts the current key

            String outKeyString = "";

            for(int j = 0; j<outKey.size(); j++) //translates the encrypted key back into a string
            {
                outKeyString += outKey.get(j);
            }

            outArray.add(outKeyString); //builds the encryptedKeyArray
        }

        return outArray;
    }
}
