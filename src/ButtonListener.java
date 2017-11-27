import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ButtonListener implements ActionListener {

    HashMap<String, Component> _map;
    JTextArea textArea;
    JFrame master;

    public ButtonListener(HashMap<String, Component> map)
    {
        _map = map;
        textArea = (JTextArea) map.get("textArea");
        master = (JFrame) map.get("master");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            String source = ((JButton) e.getSource()).getName();

            String content = ((JTextArea) _map.get("textArea")).getText();

            String keyFilePath = ((JTextField) _map.get("keyFilePathField")).getText();

            List<List<Integer>> keys;

            String password = String.valueOf(
                    ((JPasswordField) _map.get("passwordField")).getPassword()
            );

            switch (source) {
                case "lockEncryptButton": {

                    master.setCursor(
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    SwingUtilities.disableButtonInput(_map);

                    ProgressDialog progressDialog = new ProgressDialog(master);

                    _map.put("progressBar", progressDialog.progressBar);

                    try {
                        keys = IO_Utilities.readKeyFile(keyFilePath);
                    }
                    catch (Exception x)
                    {
                        SwingUtilities.enableButtonInput(_map);
                        master.setCursor(
                            Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        throw new Exception("KeyFile Not Found");
                    }

                    SwingWorker<String, String> sW =
                            new EncryptionMatrixMkIISwingWorker(
                                    _map,
                                    progressDialog,
                                    content,
                                    keys,
                                    password,
                                    true);

                    progressDialog.display();

                    sW.execute();
                }
                break;

                case "unlockDecryptButton" : {

                    master.setCursor(
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    ProgressDialog progressDialog = new ProgressDialog(master);

                    _map.put("progressBar", progressDialog.progressBar);

                    SwingUtilities.disableButtonInput(_map);

                    try {
                        keys = IO_Utilities.readKeyFile(keyFilePath);
                    }
                    catch (Exception x)
                    {
                        SwingUtilities.enableButtonInput(_map);
                        master.setCursor(
                            Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        throw new Exception("KeyFile Not Found");
                    }

                    SwingWorker<String, String> sW =
                            new EncryptionMatrixMkIISwingWorker(
                                    _map,
                                    progressDialog,
                                    content,
                                    keys,
                                    password,
                                    false);

                    progressDialog.display();

                    sW.execute();
                } break;

                case "keyFileChooser" :
                {
                    JFileChooser fileChooser = new JFileChooser(new File("."));

                    int returnval = fileChooser.showOpenDialog(_map.get("master"));

                    if(returnval == JFileChooser.APPROVE_OPTION)
                    {
                        File file = fileChooser.getSelectedFile();

                        String newKeyFilePath = file.getPath();

                        try
                        {
                            List<List<Integer>> newKeys = IO_Utilities.readKeyFile(newKeyFilePath);

                            ((JLabel) _map.get("levelDisplay")).setText(newKeys.size()/2 + "");

                            ((JTextField) _map.get("keyFilePathField")).setText(file.getPath());
                        }

                        catch(Exception x)
                        {
                            ((JTextArea) _map.get("commandArea")).append("Error loading keyfile: " + x + "\n");
                        }
                    }

                    else
                    {
                        ((JTextArea) _map.get("commandArea")).append("Load KeyFile Operation Cancelled\n");
                    }
                } break;
            }
        }

        catch(Exception x)
        {
            ((JTextArea) _map.get("commandArea")).append(
                    x.toString() + "\n"
            );
        }

        return;
    }
}
