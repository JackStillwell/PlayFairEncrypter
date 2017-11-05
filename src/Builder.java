import javax.swing.*;
import java.awt.*;

public class Builder {

    public static Component buildRightSide()
    {
        JPanel master = new JPanel();

        // Build all components

        JButton lockEncryptButton = new JButton("Encrypt");
        JButton unlockDecryptButton = new JButton("Decrypt");

        JLabel passwordLabel = new JLabel("Password");

        JPasswordField passwordField = new JPasswordField();

        JLabel keyFileLabel = new JLabel("KeyFile");

        JLabel keyFileChooser = new JLabel("FileChooser");

        JTextField keyFilePathField = new JTextField();

        JLabel levelLabel = new JLabel("Level");

        JLabel levelDisplay = new JLabel();

        // build buttons
        JPanel encryptDecryptButtonPanel = new JPanel();

        encryptDecryptButtonPanel.setLayout(
                new GridLayout(1,2,10,10)
        );

        encryptDecryptButtonPanel.add(lockEncryptButton);
        encryptDecryptButtonPanel.add(unlockDecryptButton);

        // build password

        JPanel passwordPanel = new JPanel();

        passwordPanel.setLayout(new GridLayout(2,1,10,10));

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // build KeyFile

        JPanel keyFilePanel = new JPanel();

        keyFilePanel.setLayout(new BorderLayout(10,10));

        keyFilePanel.add(keyFileChooser, BorderLayout.WEST);
        keyFilePanel.add(keyFilePathField, BorderLayout.CENTER);

        // build KeyFile Level

        JPanel keyFileLevelPanel = new JPanel();

        keyFileLevelPanel.setLayout(new BorderLayout(10,10));

        keyFileLevelPanel.add(levelLabel, BorderLayout.CENTER);
        keyFileLevelPanel.add(levelDisplay, BorderLayout.EAST);

        // build master

        master.setLayout(new GridLayout(5,1, 10, 10));

        master.add(encryptDecryptButtonPanel);
        master.add(passwordPanel);
        master.add(keyFileLabel);
        master.add(keyFilePanel);
        master.add(keyFileLevelPanel);

        return master;
    }

    public static Component buildTextArea()
    {
        JScrollPane textScrollPane = new JScrollPane();

        JTextArea textArea = new JTextArea(80,80);

        textArea.setText("Text Area");

        textScrollPane.add(textArea);

        return textScrollPane;
    }

    public static Component buildCommandArea()
    {
        JScrollPane textScrollPane = new JScrollPane();

        JTextArea textArea = new JTextArea(10,80);

        textArea.setText("Command Area");

        textArea.setEditable(false);

        textScrollPane.add(textArea);

        return textScrollPane;
    }

    public static Component buildMenuBar()
    {
        JPanel master = new JPanel();

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem helpMenu = new JMenuItem("Help");

        JMenuItem aboutMenu = new JMenuItem("About");

        JMenuItem loadButton = new JMenuItem("Load File");

        JMenuItem saveButton = new JMenuItem("Save File");

        JMenuItem selectKeyfile = new JMenuItem("Select KeyFile");

        JMenuItem createKeyfile = new JMenuItem("Create Keyfile");

        fileMenu.add(loadButton);
        fileMenu.add(saveButton);
        fileMenu.add(selectKeyfile);
        fileMenu.add(createKeyfile);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(aboutMenu);

        return master;
    }

    public static Component buildMaster()
    {
        JPanel master = new JPanel();

        master.setLayout(new BorderLayout(10,10));

        master.add(buildRightSide(), BorderLayout.EAST);

        master.add(buildTextArea(), BorderLayout.CENTER);

        master.add(buildCommandArea(), BorderLayout.SOUTH);

        master.add(buildMenuBar(), BorderLayout.NORTH);

        return master;
    }
}
