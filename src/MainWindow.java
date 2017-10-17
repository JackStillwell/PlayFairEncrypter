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
    private Component createComponents()
    {
        JPanel master = new JPanel();

        JFileChooser fileChooser = new JFileChooser();

        return master;
    }

    public static void main(String[] args) {

        if(args.length != 1) // handle command line use
        {
	    for(String a : args)
	    {
		    System.out.println(a);
	    }

            String toEncrypt = args[0];

            List<Integer> asciiArray = EncryptionUtilities
                                        .StringToAsciiArray(toEncrypt);

	    for(int i : asciiArray)
	    {
	    	System.out.print(i + " ");
	    } System.out.println();

	    List<AsciiPair> asciiPairArray = EncryptionUtilities
		    				.AsciiArrayToAsciiPairArray(asciiArray);

	    for(AsciiPair a : asciiPairArray)
	    {
	    	System.out.print(a + " ");
	    } System.out.println();

            // Multiply desired levels of encryption by 2 to obtain enough keys
            List<List<Integer>> keys = EncryptionMatrixMkII
                                        .keyFileGenerator(Integer.parseInt(args[1])*2);

	    List<AsciiPair> encryptedAsciiPairArray = EncryptionMatrixMkII
		    					.cyclePlayFairFoursquareCipher(
								asciiPairArray,
								keys);

	    List<Integer> encryptedAsciiArray = EncryptionUtilities
	    						.asciiPairArrayToAsciiArray(
								asciiPairArray);

	    String encrypted = EncryptionUtilities
		    		.AsciiArrayToString(encryptedAsciiArray);

	    System.out.println(encrypted);

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
