import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Builder {


    /* Commonly used GridBagConstraints */
    private static GridBagConstraints GridBagFillHorizontal()
    {
        GridBagConstraints toReturn = new GridBagConstraints();
        toReturn.weightx = 1;
        toReturn.fill = GridBagConstraints.HORIZONTAL;

        return toReturn;
    }

    public static Component buildRightSide()
    {
        JPanel master = new JPanel();

        // Build all components

        JButton lockEncryptButton = new JButton("Encrypt");
        lockEncryptButton.setName("lockEncryptButton");
        JButton unlockDecryptButton = new JButton("Decrypt");
        unlockDecryptButton.setName("unlockDecryptButton");

        JLabel passwordLabel = new JLabel("Password");

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setName("passwordField");

        JLabel keyFileLabel = new JLabel("KeyFile");

        JButton keyFileChooser = new JButton("Choose KeyFile");
        keyFileChooser.setName("keyFileChooser");

        JTextField keyFilePathField = new JTextField();
        keyFilePathField.setEditable(false);
        keyFilePathField.setName("keyFilePathField");

        JLabel levelLabel = new JLabel("Level");

        JLabel levelDisplay = new JLabel();
        levelDisplay.setName("levelDisplay");

        // build buttons
        JPanel encryptDecryptButtonPanel = new JPanel();

        encryptDecryptButtonPanel.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints fillHorizontal = GridBagFillHorizontal();
        fillHorizontal.insets = new Insets(0,5,0,5);

        encryptDecryptButtonPanel.add(lockEncryptButton, fillHorizontal);
        encryptDecryptButtonPanel.add(unlockDecryptButton, fillHorizontal);

        // build password

        JPanel passwordPanel = new JPanel();

        passwordPanel.setLayout(new GridBagLayout());

        passwordPanel.add(passwordLabel, fillHorizontal);
        passwordPanel.add(passwordField, fillHorizontal);

        // build KeyFile

        JPanel keyFilePanel = new JPanel();

        keyFilePanel.setLayout(new BorderLayout(10,10));

        keyFilePanel.add(keyFileLabel, BorderLayout.NORTH);

        JPanel keyFilePathPanel = new JPanel();
        keyFilePathPanel.setLayout(new GridBagLayout());

        keyFilePathPanel.add(keyFileChooser, fillHorizontal);
        keyFilePathPanel.add(keyFilePathField, fillHorizontal);

        keyFilePanel.add(keyFilePathPanel, BorderLayout.CENTER);

        // build KeyFile Level

        JPanel keyFileLevelPanel = new JPanel();

        keyFileLevelPanel.setLayout(new BorderLayout(10,10));

        keyFileLevelPanel.add(levelLabel, BorderLayout.CENTER);
        keyFileLevelPanel.add(levelDisplay, BorderLayout.EAST);

        keyFilePanel.add(keyFileLevelPanel, BorderLayout.SOUTH);

        // build master

        master.setLayout(new GridLayout(3,1, 10, 10));

        master.add(encryptDecryptButtonPanel);
        master.add(passwordPanel);
        master.add(keyFilePanel);

        return master;
    }

    public static Component buildTextArea()
    {
        JPanel master = new JPanel(new GridLayout(1,1));

        JTextArea textArea = new JTextArea(40,80);
        textArea.setName("textArea");

        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        JScrollPane textScrollPane = new JScrollPane(textArea);

        master.add(textScrollPane);

        return master;
    }

    public static Component buildCommandArea()
    {
        JPanel master = new JPanel(new GridLayout(1,1));

        JTextArea textArea = new JTextArea(10,80);
        textArea.setName("commandArea");

        textArea.setText("Command Area\n");

        textArea.setEditable(false);

        textArea.setWrapStyleWord(true);

        textArea.setLineWrap(true);

        JScrollPane textScrollPane = new JScrollPane(textArea);

        master.add(textScrollPane);

        return master;
    }

    public static Component buildMenuBar()
    {
        JPanel master = new JPanel(new GridLayout(1, 6));

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("fileMenu");

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setName("helpMenu");

        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setName("aboutMenu");

        JMenuItem loadButton = new JMenuItem("Load File");
        loadButton.setName("loadButton");

        JMenuItem saveButton = new JMenuItem("Save File");
        saveButton.setName("saveButton");

        JMenuItem selectKeyfile = new JMenuItem("Select KeyFile");
        selectKeyfile.setName("selectKeyFileButton");

        JMenuItem createKeyfile = new JMenuItem("Create Keyfile");
        createKeyfile.setName("createKeyFileButton");

        fileMenu.add(loadButton);
        fileMenu.add(saveButton);
        fileMenu.add(selectKeyfile);
        fileMenu.add(createKeyfile);

        menuBar.add(fileMenu);

        //TODO: readd and implement help and about menus
        //menuBar.add(helpMenu);
        //menuBar.add(aboutMenu);

        master.add(menuBar);

        return master;
    }

    public static Component buildMaster()
    {
        JPanel master = new JPanel();

        JPanel rightSide = (JPanel) buildRightSide();

        JPanel textArea = (JPanel) buildTextArea();

        JPanel commandArea = (JPanel) buildCommandArea();

        JPanel menuBar = (JPanel) buildMenuBar();

        master.setLayout(new BorderLayout(10,10));

        master.add(rightSide, BorderLayout.EAST);

        master.add(textArea, BorderLayout.CENTER);

        master.add(commandArea, BorderLayout.SOUTH);

        master.add(menuBar, BorderLayout.NORTH);

        return master;
    }
}
