import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Builder {

    public static Component buildRightSide()
    {
        JPanel master = new JPanel();

        // Build all compoenents

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

    public static Component buildMaster()
    {
        JPanel master = new JPanel();

        master.setLayout(new BorderLayout(10,10));

        master.add(buildRightSide(), BorderLayout.EAST);

        master.add(new JScrollBar(), BorderLayout.CENTER);

        master.add(new JTextArea(), BorderLayout.SOUTH);

        master.add(new JMenuBar(), BorderLayout.NORTH);

        return master;
    }

}
