/*
    Created by Jack Stillwell

    ver 0.1

    13.10.2017

 */

import javax.swing.*;
import javax.swing.text.Caret;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class EncryptionMatrixMkIISwingWorker extends SwingWorker<String,String> {

    /* TODO: CATCH AND LOG ASSERTION ERRORS */
    /* TODO: ADD ASSERTIONS WHERE ANY BREAK POSSIBLE */

    public static final int __GRIDSIZE__ = 16;

    private JTextArea textArea;
    private JTextArea commandArea;
    private JFrame master;
    HashMap<String, Component> _map;
    private String input;
    List<List<Integer>> keyFile;
    String password;
    boolean encrypt;


    public EncryptionMatrixMkIISwingWorker(HashMap<String, Component> map,
                                           String _input,
                                           List<List<Integer>> _keyFile,
                                           String _password,
                                           boolean _encrypt)
    {
        textArea = (JTextArea) map.get("textArea");
        commandArea = (JTextArea) map.get("commandArea");
        master = (JFrame) map.get("master");
        _map = map;

        input = _input;
        keyFile = _keyFile;
        password = _password;
        encrypt = _encrypt;
    }

    /* employs the a Playfair-FourSquare Encryption Technique */
    public static List<AsciiPair> playfairFoursquareCipher(
                                List<AsciiPair> input,
                                int[][] alphabet,
                                int[][] key1,
                                int[][] key2,
                                boolean encrypt) throws Exception
    {
        return EncryptionMatrixMkII.playfairFoursquareCipher(
                input,
                alphabet,
                key1,
                key2,
                encrypt
        );
    }

    public List<AsciiPair> cyclePlayFairFoursquareCipher(
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
            publish("Beginning Grid Encryption...\n");

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

                publish("Completed Loop " +
                        (i+1) + "/" + (keyArray.size()/2) +
                        " of Grid Encryption\n");
            }

            publish("Finished Grid Encryption\n");
        }

        else
        {
            publish("Beginning Grid Decryption...\n");

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

                publish("Completed Loop " +
                        ((keyArray.size()/2)-i) + "/" + (keyArray.size()/2) +
                        " of Grid Decryption\n");
            }

            publish("Finished Grid Decryption\n");
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
    public ArrayList<Integer> xorCipher(
                                String password,
                                List<List<Integer>> keyfile,
                                List<Integer> binaryArray) throws Exception {

        publish("Beginning Xor...\n");

        DecimalFormat fmt = new DecimalFormat("#");
        List<Integer> asciiArray = EncryptionUtilities.stringToAsciiArray(password);
        StringBuilder passwordNumStringBuilder = new StringBuilder();

        List<AsciiPair> asciiPairArray = EncryptionUtilities.asciiArrayToAsciiPairArray(asciiArray);

        // ensure unique xor generation per unique password-keyfile combo


        publish("Beginning Password Encryption...\n");

        asciiPairArray = cyclePlayFairFoursquareCipher(asciiPairArray, keyfile, true);

        publish("Finished Password Encryption\n");

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

        publish("Finished Xor\n");

        return outputArray;
    }

    public static List<List<Integer>> keyFileGenerator(int numberToGenerate)
    {
        return EncryptionMatrixMkII.keyFileGenerator(numberToGenerate);
    }

    public String encryptSequence(String input, List<List<Integer>> keyFile, String password) throws Exception {

        publish("Beginning Encryption...\n");

        List<Integer> asciiArray =
                EncryptionUtilities.stringToAsciiArray(input);

        List<AsciiPair> asciiPairArray =
                EncryptionUtilities.asciiArrayToAsciiPairArray(asciiArray);

        asciiPairArray = cyclePlayFairFoursquareCipher(asciiPairArray, keyFile, true);

        asciiArray = EncryptionUtilities
                .asciiPairArrayToAsciiArray(asciiPairArray);

        List<Integer> binaryArray = EncryptionUtilities
                .asciiArrayToBinaryArray(asciiArray);

        binaryArray = xorCipher(password, keyFile, binaryArray);

        publish("Finished Encryption, printing to screen...\n");

        return EncryptionUtilities.binaryArrayToBinaryString(binaryArray);
    }

    public String decryptSequence(String input, List<List<Integer>> keyFile, String password) throws Exception {

        publish("Beginning Decryption...\n");

        List<Integer> binaryArray = EncryptionUtilities
                .binaryStringToBinaryArray(input);

        binaryArray = xorCipher(password, keyFile, binaryArray);

        List<Integer> asciiArray = EncryptionUtilities
                .binaryArrayToAsciiArray(binaryArray);

        List<AsciiPair> asciiPairArray = EncryptionUtilities
                .asciiArrayToAsciiPairArray(asciiArray);

        asciiPairArray = cyclePlayFairFoursquareCipher(asciiPairArray,keyFile,false);

        asciiArray = EncryptionUtilities.asciiPairArrayToAsciiArray(asciiPairArray);

        publish("Finished Decryption, printing to screen...\n");

        return EncryptionUtilities.asciiArrayToString(asciiArray);
    }

    @Override
    protected String doInBackground() throws Exception {

        String toReturn;

        if(encrypt)
        {
            toReturn = encryptSequence(input, keyFile, password);
        }

        else
        {
            toReturn = decryptSequence(input, keyFile, password);
        }

        return toReturn;
    }

    @Override
    protected void process(List<String> item)
    {
        for(String s : item)
        {
            commandArea.append(s);
            commandArea.setCaretPosition(commandArea.getDocument().getLength());
        }
    }

    @Override
    protected void done()
    {
        try {
            textArea.setText(this.get());

            SwingUtilities.enableButtonInput(_map);

            master.setCursor(
                    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        catch (InterruptedException | ExecutionException e) {}
    }
}