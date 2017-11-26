import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class CreateKeyFileDialog {

    private JFrame frame;
    HashMap<String, Component> _map;

    public CreateKeyFileDialog() {
        frame = new JFrame("Create KeyFile");

        JPanel master = new JPanel();

        master.setLayout(new FlowLayout());

        JLabel keyFileNameLabel = new JLabel("Name: ");
        JTextField keyFileNameField = new JTextField();
        keyFileNameField.setName("keyFileNameField");

        JLabel levelsOfEncryptionLabel = new JLabel("Levels of Encryption: ");
        //TODO: add a slider allowing the user to choose between 2 and 10

        JLabel defaultKeyFileLabel = new JLabel("Default: ");
        //TODO: add radio buttons for yes and no

        JButton createKeyFileButton = new JButton("Create");
        createKeyFileButton.setName("createKeyFileButton");

        createKeyFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (keyFileNameField.getText().length() != 0) {
                        List<List<Integer>> key =
                                EncryptionMatrixMkII.keyFileGenerator(
                                        /* slider int */0
                                );

                        IO_Utilities.writeKeyFile(key, "./keyFile/" +
                                keyFileNameField.getText());


                    }

                    else
                    {
                        keyFileNameField.setText("Not a Valid Filename");
                    }
                }

                catch(Exception x)
                {
                    // print exception to command area
                }
            }
        });

        _map = ComponentTrackingUtility.buildComponentMap(master);
        frame.getContentPane().add(master);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    public void display()
    {
        frame.setVisible(true);
    }

    public void hide()
    {

    }
}
