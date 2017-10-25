/*
    Jack Stillwell

    ver 0.1

    14.10.2017
*/


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class MainWindow
{
    static final boolean DEBUG = true;

    private Component createComponents()
    {
        JPanel master = new JPanel();

        JFileChooser fileChooser = new JFileChooser();

        return master;
    }

    public static void main(String[] args) {

        if(args.length > 1 || DEBUG) // handle command line use
        {
	    	for(String a : args)
	    	{
		    	System.out.println(a);
	    	}

            String toEncrypt = "hello world!";

            List<Integer> asciiArray = EncryptionUtilities
                                        .stringToAsciiArray(toEncrypt);

	    	List<AsciiPair> asciiPairArray = EncryptionUtilities
		    				.asciiArrayToAsciiPairArray(asciiArray);

            // Multiply desired levels of encryption by 2 to obtain enough keys
            List<List<Integer>> keys = EncryptionMatrixMkII
                                        .keyFileGenerator(Integer.parseInt("10"));

	    	List<AsciiPair> encryptedAsciiPairArray = EncryptionMatrixMkII
		    					.cyclePlayFairFoursquareCipher(
								asciiPairArray,
								keys,
                                true);

	    	List<Integer> encryptedAsciiArray = EncryptionUtilities
	    						.asciiPairArrayToAsciiArray(
								encryptedAsciiPairArray);

	    	System.out.println("Original: " + encryptedAsciiArray);

	    	System.out.println("Original: " +
                    EncryptionUtilities
                            .asciiArrayToBinaryArray(encryptedAsciiArray).toString());

	    	List<Integer> xoredArray = EncryptionMatrixMkII.xorCipher(
					        "password",
                            EncryptionUtilities.asciiArrayToBinaryArray(
                                    encryptedAsciiArray)
                        );

	    	try {
                IO_Utilities.writeTextFile(xoredArray.toString(), "testfile");

                // or

                IO_Utilities.writeBinaryFile(xoredArray,
                        "testfileBinary");
            }

            catch(Exception e)
            {
                System.out.println("FUCK" + e.getLocalizedMessage());
            }

            String encrypted;

            try{
	    	    encrypted = IO_Utilities.readTextFile("testfile");

	    	    // or

                xoredArray = IO_Utilities.readBinaryFile("testfileBinary.dpfe");
            }

            catch(Exception e)
            {
                System.out.println("FUCK" + e.getLocalizedMessage());
            }

            List<Integer> decryptedBinaryArray =
                    EncryptionMatrixMkII.xorCipher("password",
                            xoredArray);

	    	System.out.println("Decrypted: " + decryptedBinaryArray.toString());

	    	List<AsciiPair> decryptedAsciiPairArray = EncryptionUtilities
                    .asciiArrayToAsciiPairArray(
                            EncryptionUtilities.binaryArrayToAsciiArray(
                                    decryptedBinaryArray
                            )

                    );

	    	System.out.println("Decrypted: " + decryptedAsciiPairArray);

	    	decryptedAsciiPairArray = EncryptionMatrixMkII
                    .cyclePlayFairFoursquareCipher(
                            decryptedAsciiPairArray,
                            keys,
                            false
                    );

	    	String decrypted = EncryptionUtilities
                    .asciiArrayToString(
                            EncryptionUtilities
                                    .asciiPairArrayToAsciiArray(decryptedAsciiPairArray)
                    );

	    	System.out.println(decrypted);

	    	return;
        }

        JFrame frame = new JFrame("DontPlayFair");

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
		 *
		 */
        HashMap<String, Component> componentMap =
                new ComponentTrackingUtility()
                        .buildComponentMap(frame.getContentPane());

        // TODO: all component listener assignment here


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
