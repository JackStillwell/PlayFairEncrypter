/*
    Jack Stillwell

    ver 0.1

    14.10.2017
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class MainWindow
{
    static final boolean DEBUG = false;
    static final boolean GUI_DEBUG = true;

    private Component createComponents()
    {
        return Builder.buildMaster();
    }

    public static void main(String[] args) {

        if(args.length > 1 || DEBUG) // handle command line use
        {
            handleCommandLine(args);
            return;
        }

        // TODO: run start-up procedure to obtain all necessary boot info

        JFrame frame = new JFrame("DontPlayFair");
        frame.setName("MainWindow");

        MainWindow mainWindow = new MainWindow();

        Component contents = mainWindow.createComponents();

        frame.getContentPane().add(contents);
        frame.getRootPane().setBorder(
                BorderFactory
                        .createEmptyBorder(10, 10, 10, 10)
        );

        /*
		 * build component map
		 * utilities sourced from various sites
		 * see file for details
		 */
        HashMap<String, Component> componentMap =
                new ComponentTrackingUtility()
                        .buildComponentMap(frame.getContentPane());

        componentMap.put("master", frame);

        // TODO: all component listener assignment here

        ActionListener buttonListener = new ButtonListener(componentMap);

        ((JButton) componentMap.get("lockEncryptButton")).addActionListener(buttonListener);
        ((JButton) componentMap.get("unlockDecryptButton")).addActionListener(buttonListener);
        ((JMenu) componentMap.get("helpMenu")).addActionListener(buttonListener);
        ((JMenu) componentMap.get("aboutMenu")).addActionListener(buttonListener);
        ((JButton) componentMap.get("keyFileChooser")).addActionListener(buttonListener);

        if(GUI_DEBUG)
        {
            try {
                ((JTextField) componentMap.get("keyFilePathField")).setText("src/keyfile.dpfk");

                String keyFilePath = ((JTextField) componentMap.get("keyFilePathField")).getText();

                List<List<Integer>> keys = IO_Utilities.readKeyFile(keyFilePath);

                ((JLabel) componentMap.get("levelDisplay")).setText(
                        keys.size() / 2 + ""
                );
            }

            catch(Exception x)
            {
                ((JTextArea) componentMap.get("commandArea")).append(
                        x.toString() + "\n"
                );
            }
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void handleCommandLine(String[] args)
    {
        for(String a : args)
        {
            System.out.println(a);
        }

        try {

            // parse command

            if (args[0].equals("--help"))
            {
                // print help string
                return;
            }

            if(args[0].equalsIgnoreCase("keyfile"))
            {
                try {
                    int levels = Integer.parseInt(args[1]);

                    List<List<Integer>> keys = EncryptionMatrixMkII.keyFileGenerator(levels);

                    IO_Utilities.writeKeyFile(keys, args[2]);
                }

                catch(Exception e)
                {
                    System.out.println(e.toString());
                }

                return;
            }

            boolean encrypt;

            if(args[0].equalsIgnoreCase("encrypt"))
            {
                encrypt = true;
            }

            else if(args[0].equalsIgnoreCase("decrypt"))
            {
                encrypt = false;
            }

            else
            {
                throw new Exception("Operation not recognized, please consult --help");
            }

            String input = args[1].equals("-f")
                            ? IO_Utilities.readTextFile(args[2])
                            : args[1];

            List<List<Integer>> keys = args[1].equals("-f")
                                    ? IO_Utilities.readKeyFile(args[3])
                                    : IO_Utilities.readKeyFile(args[2]);

            String password = args[1].equals("-f")
                                ? args[4]
                                : args[3];

            List<Integer> inputArray;

            if(!encrypt)
            {
                inputArray = EncryptionUtilities.binaryStringToBinaryArray(input);
                inputArray = EncryptionMatrixMkIICmd.xorCipher(password, keys, inputArray);
                inputArray = EncryptionUtilities.binaryArrayToAsciiArray(inputArray);
            }

            else
            {
                inputArray = EncryptionUtilities.stringToAsciiArray(input);
            }

            List<AsciiPair> inputPairArray = EncryptionUtilities.asciiArrayToAsciiPairArray(inputArray);

            List<AsciiPair> outputPairArray =
                    EncryptionMatrixMkIICmd.cyclePlayFairFoursquareCipher(
                            inputPairArray, keys, encrypt
                    );

            List<Integer> outputArray;
            String output = "";

            if(encrypt)
            {
                outputArray =
                        EncryptionUtilities.asciiArrayToBinaryArray(
                                EncryptionUtilities.asciiPairArrayToAsciiArray(
                                        outputPairArray
                                )
                        );

                outputArray = EncryptionMatrixMkIICmd.xorCipher(password, keys, outputArray);

                output = EncryptionUtilities.binaryArrayToBinaryString(outputArray);
            }

            else
            {
                outputArray = EncryptionUtilities.asciiPairArrayToAsciiArray(
                        outputPairArray
                );

                output = EncryptionUtilities.asciiArrayToString(outputArray);
            }

            boolean fileOutput = false;

            try
            {
                fileOutput = args[1].equals("-f")
                        ? args[5].equals("-f")
                        : args[4].equals("-f");
            }

            catch (ArrayIndexOutOfBoundsException e)
            {
                fileOutput = false;
            }

            if(fileOutput)
            {
                System.out.println("Beginning file write...\n");

                if(encrypt)
                {
                    IO_Utilities.writeBinaryFile(outputArray, args[args.length - 1]);
                }

                else
                {
                    IO_Utilities.writeTextFile(output, args[args.length - 1]);
                }

                System.out.println("Finished File write\n");
            }

            else
            {
                System.out.println(output);
            }
        }

        catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage() + "\n"
                                + e.toString());
        }

        System.out.println("Exiting...");
        return;
    }
}