import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

public class CreateKeyFileDialog {

    private JDialog frame;
    HashMap<String, Component> _map;

    public CreateKeyFileDialog(HashMap<String, Component> map) {

        _map = map;

        frame = new JDialog(((JFrame)_map.get("master")), "Create KeyFile");

        JPanel master = new JPanel();

        master.setLayout(new FlowLayout());

        JLabel keyFileNameLabel = new JLabel("Name: ");
        JTextField keyFileNameField = new JTextField(20);
        keyFileNameField.setName("keyFileNameField");

        JLabel levelsOfEncryptionLabel = new JLabel("Levels of Encryption: ");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
        slider.setMajorTickSpacing(4);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        // TODO: make the default setting actually do something
        JLabel defaultKeyFileLabel = new JLabel("Default: ");
        JRadioButton yesRadioButton = new JRadioButton("Yes");
        JRadioButton noRadioButton = new JRadioButton("No");

        JButton createKeyFileButton = new JButton("Create");
        createKeyFileButton.setName("createKeyFileButton");

        createKeyFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (keyFileNameField.getText().length() != 0) {

                        SwingWorker<Void,Void> swingWorker = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                List<List<Integer>> key =
                                        EncryptionMatrixMkII.keyFileGenerator(
                                                slider.getValue()*2
                                        );

                                IO_Utilities.writeKeyFile(key,
                                        keyFileNameField.getText());

                                frame.setVisible(false);
                                frame.dispose();

                                return null;
                            }

                        };

                        swingWorker.execute();
                    }

                    else
                    {
                        keyFileNameField.setText("Not a Valid Filename");
                    }
                }

                catch(Exception x)
                {
                    ((JTextArea) _map.get("commandArea")).append("Creating KeyFile Error: " + x + "\n");
                }
            }
        });

        master.add(keyFileNameLabel);
        master.add(keyFileNameField);
        master.add(levelsOfEncryptionLabel);
        master.add(slider);
        master.add(defaultKeyFileLabel);
        master.add(yesRadioButton);
        master.add(noRadioButton);
        master.add(createKeyFileButton);

        _map = ComponentTrackingUtility.buildComponentMap(master);
        frame.getContentPane().add(master);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(_map.get("master"));
        frame.pack();

        //TODO: enable and disable buttons

        /* frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.enableButtonInput(_map);
            }
        }); */
    }

    public void display()
    {
        frame.setVisible(true);
    }

    public void hide()
    {
        frame.setVisible(false);
        frame.dispose();
    }
}
