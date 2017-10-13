import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jack on 11/12/2015.
 */

public class main
{
    public static void main (String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);

        EncryptionMatrix crypt = new EncryptionMatrix();
        BlockCipher obj = new BlockCipher();

        boolean quitNow = false;

        while(quitNow == false)
        {
            System.out.println("Welcome to Guru and Jack's encrypter!");

            System.out.print("Would you like to encrypt or decrypt?\n[e]-encrypt\n[d]-decrypt\n");

            String eOrD = "";
            eOrD=in.nextLine();

            System.out.println("Would you like to:\n[m]-manually enter text\n[r]-read in a .txt file");

            String readInQ = "";

            String input = "";

            readInQ = in.nextLine();

            String fileInput = "";

            if(readInQ.equals("m"))
            {
                System.out.print("What is the message you would like to enter? ");

                input = in.nextLine();

                System.out.print("For future reference, what would you name this encryption/decryption as a file (do not include the .txt extension)? ");

                fileInput = in.nextLine() + ".txt";
            }

            else if(readInQ.equals("r")) //split so that Guru's encrypted file will be read.
            {
                boolean goodInput = true;

                while (goodInput == true) //If the input is bad, restart the loop asking for input
                {
                    System.out.println("Please enter a file name: ");

                    fileInput = in.nextLine(); //Read in the user input to a newly created String 'input'

                    if (fileInput.equalsIgnoreCase("QUIT")) //If the user enters "QUIT" kill the program
                        quitNow = true;

                    else if (!fileInput.equalsIgnoreCase("QUIT") && (fileInput.indexOf(".") == -1 || !fileInput.substring(fileInput.indexOf(".") + 1).equals("txt"))) //checks the file for a .txt extension
                    {
                        System.out.println("I'm sorry, the file " + fileInput + " has an invalid file extension.");
                    }

                    else
                    {
                        if(eOrD.equals("d")) //if decryption uses Guru's reader to decrypt read in the binary
                        {
                            try
                            {
                                input = obj.text_reader(fileInput); //reads in file
                                goodInput = false; //ends the input loop
                            }

                            catch (FileNotFoundException x) //if the file is not found, prints the error message
                            {
                                if (!input.equalsIgnoreCase("QUIT"))
                                {
                                    System.out.println("\nI'm sorry, the file " + fileInput +
                                            " cannot be found.\nThe file must be in the same directory as this program.");
                                }
                            }
                        }

                        else if(eOrD.equals("e")) //if encryption uses mine to read in the text
                        {
                            try
                            {
                                Scanner newFile = new Scanner(new File(fileInput)); //Reads in the file

                                input = crypt.readInFile(newFile);

                                goodInput=false; //ends the input loop
                            }

                            catch (FileNotFoundException x) //if the file is not found, prints the error message
                            {
                                if (!input.equalsIgnoreCase("QUIT"))
                                {
                                    System.out.println("\nI'm sorry, the file " + fileInput +
                                            " cannot be found.\nThe file must be in the same directory as this program.");
                                }
                            }
                        }
                    }
                }
            }

            if(eOrD.equals("e"))
            {
                input = crypt.stringConditioner(input);
            }

//          System.out.println(input); DEBUG

            System.out.print("How many layers of encryption/decryption would you like? ");

            int onion = in.nextInt();

            String keyword = "";

            ArrayList<String> keySet = new ArrayList<String>();

            System.out.print("Would you like to:\n[m]-manually enter your keys\n[r]-read in a keySet file\n");

            String readInKeyChoice = "";

            in.nextLine(); // apparently, I need this to make in.nextLine() below work

            readInKeyChoice = in.nextLine();

            if(readInKeyChoice.equals("m"))
            {
                for(int i = 0; i < (onion * 2);)
                {
                    System.out.print("Please enter your keyword: ");

                    keyword = in.nextLine();

                    keyword = crypt.stringConditioner(keyword);

//                    System.out.println(keyword); DEBUG

                    if(!keyword.equals(""))
                    {
                        keySet.add(keyword);
                        i++;
                    }
                }

                System.out.println("Would you like to export your keySet as '[yourFile]-keySet.txt'?\n[y]-yes\n[n]-no");

                String exportKeySet = "";
                exportKeySet = in.nextLine();

                if(exportKeySet.equals("y"))
                {
                    String keySetString = "";

                    for(int i = 0; i<keySet.size(); i++)
                    {
                        keySetString += keySet.get(i) + "^";
                    }

                    try
                    {
                        File output = new File(fileInput.substring(0, fileInput.indexOf('.')) + "-keySet.txt"); //defines a new file with the specified title
                        output.createNewFile(); //creates the new file
                        FileWriter writeToOutput = new FileWriter(output); //defines a method to write to the new file

                        writeToOutput.write(keySetString); //writes to the new file
                        writeToOutput.flush();
                        writeToOutput.close();

                        System.out.println("Creating File...\nDone.");
                    }

                    catch(IOException y) //If this exception is thrown, print this error message
                    {
                        System.out.println("IOException Error");
                    }
                }
            }

            else if(readInKeyChoice.equals("r"))
            {
                boolean goodInput = true;

                while (goodInput == true) //If the input is bad, restart the loop asking for input
                {
                    System.out.println("Do you already have a keySet file [yourfile]-keySet.txt' in the same directory as this program:\n" +
                            "[y]-yes, read it in\n[n]-no, I will specify the file name");

                    String alreadyKnow = "";
                    alreadyKnow = in.nextLine();

                    if(alreadyKnow.equals("y"))
                    {
                        try
                        {
                            Scanner newFile = new Scanner(new File(fileInput.substring(0,fileInput.indexOf(".")) + "-keySet.txt")); //Reads in the file

                            keySet = crypt.readInKeySet(newFile);

                            goodInput = false;
                        }

                        catch (FileNotFoundException x) //if the file is not found, prints the error message
                        {
                            if (!input.equalsIgnoreCase("QUIT"))
                            {
                                System.out.println("\nI'm sorry, the file " + fileInput +
                                        " cannot be found.\nThe file must be in the same directory as this program.");
                            }
                        }
                    }

                    else
                    {
                        System.out.println("Please enter a file name: ");

                        String keyFileInput = "";

                        keyFileInput = in.nextLine(); //Read in the user input to a newly created String 'input'

                        if (keyFileInput.equalsIgnoreCase("QUIT")) //If the user enters "QUIT" kill the program
                            quitNow = true;

                        if (!keyFileInput.equalsIgnoreCase("QUIT") && (keyFileInput.indexOf(".") == -1 || !keyFileInput.substring(keyFileInput.indexOf(".") + 1).equals("txt"))) //checks the file for a .txt extension
                        {
                            System.out.println("I'm sorry, the file " + keyFileInput + " has an invalid file extension.");
                        }

                        else
                        {
                            try
                            {
                                Scanner newFile = new Scanner(new File(keyFileInput)); //Reads in the file

                                keySet = crypt.readInKeySet(newFile);

                                goodInput = false;
                            }

                            catch (FileNotFoundException x) //if the file is not found, prints the error message
                            {
                                if (!keyFileInput.equalsIgnoreCase("QUIT"))
                                {
                                    System.out.println("\nI'm sorry, the file " + keyFileInput +
                                            " cannot be found.\nThe file must be in the same directory as this program.");
                                }
                            }
                        }
                    }
                }
            }

//            System.out.println(keySet); DEBUG

            keySet = crypt.encryptKeyArray(keySet);

//            System.out.println(keySet); DEBUG

            ArrayList<String> inArray = new ArrayList<>();

            if(eOrD.equals("d")) //completes Guru's portion of the project, should be back to decryptable stuff for 4-square
            {
                System.out.print("Please enter the password: ");
                String password = in.nextLine();

                input = obj.decryption(input, password);
            }

            inArray = crypt.toArray(input);

//            System.out.println(inArray); DEBUG

            char[][] alphabet = crypt.grid("aa");

            ArrayList<String> outArray = new ArrayList<>();

            //Begin split for encrypt/decrypt

            if(eOrD.equals("e"))
            {
                for(int i=0; i<onion; i++) //loop for layered encryption
                {
                    char[][] key1 = crypt.grid(keySet.get(i*2));
                    char[][] key2 = crypt.grid(keySet.get((i*2)+1));

                    outArray = crypt.encrypt(inArray, alphabet, key1, key2);

                    inArray = outArray;
                }

                String encrypted = "";

                for(int i = 0; i<outArray.size(); i++)
                {
                    encrypted += outArray.get(i);
                }

                System.out.print("Please enter your password: ");
                String password = in.nextLine();

                encrypted = obj.encryption(encrypted, password); //Guru's method to turn it into binary stuff

                System.out.println("Would you like to:\n[d]-display your encrypted text\n[e]-export it to '[yourFile]-encrypted.txt'");

                String exportChoice = "";

                exportChoice = in.nextLine();

                if(exportChoice.equals("d"))
                    System.out.println(encrypted);

                else if(exportChoice.equals("e"))
                {
                    try
                    {
                        File output = new File(fileInput.substring(0, fileInput.indexOf('.')) + "-encrypted.txt"); //defines a new file with the specified title
                        output.createNewFile(); //creates the new file
                        FileWriter writeToOutput = new FileWriter(output); //defines a method to write to the new file

                        writeToOutput.write(encrypted); //writes to the new file
                        writeToOutput.flush();
                        writeToOutput.close();

                        System.out.println("Creating File...\nDone.");
                    }

                    catch(IOException y) //If this exception is thrown, print this error message
                    {
                        System.out.println("IOException Error");
                    }
                }
            }

            else if(eOrD.equals("d"))
            {
                for(int i=0; i<onion; i++) //loop for layered encryption
                {
                    char[][] key1 = crypt.grid(keySet.get(keySet.size() - 2 - (i*2)));
                    char[][] key2 = crypt.grid(keySet.get(keySet.size() - 1 - (i*2)));

                    outArray = crypt.decrypt(inArray, alphabet, key1, key2);

                    inArray = outArray;
                }

                String decrypted = "";

                for(int i = 0; i<outArray.size(); i++)
                {
                    decrypted += outArray.get(i);
                }

                System.out.println("Would you like to:\n[d]-display your decrypted text\n[e]-export it to '[yourFile]-decrypted.txt'");

                String exportChoice = "";

                exportChoice = in.nextLine();

                if(exportChoice.equals("d"))
                    System.out.println(decrypted);

                else if(exportChoice.equals("e"))
                {
                    try
                    {
                        File output = new File(fileInput.substring(0, fileInput.indexOf('.')) + "-decrypted.txt"); //defines a new file with the specified title
                        output.createNewFile(); //creates the new file
                        FileWriter writeToOutput = new FileWriter(output); //defines a method to write to the new file

                        writeToOutput.write(decrypted); //writes to the new file
                        writeToOutput.flush();
                        writeToOutput.close();

                        System.out.println("Creating File...\nDone.");
                    }

                    catch(IOException y) //If this exception is thrown, print this error message
                    {
                        System.out.println("IOException Error");
                    }
                }
            }

            System.out.println("If you would like to quit, enter 'QUIT'. If you would like to encrypt something else, enter anything else.");

            String quitCheck = in.nextLine();

            if(quitCheck.equalsIgnoreCase("Quit"))
                quitNow=true;
        }
    }
}
