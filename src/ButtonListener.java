import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class ButtonListener implements ActionListener {

    HashMap<String, Component> _map;

    public ButtonListener(HashMap<String, Component> map)
    {
        _map = map;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            String source = ((JButton) e.getSource()).getName();

            String content = ((JTextArea) _map.get("textArea")).getText();

            String keyFilePath = ((JTextField) _map.get("keyFilePathField")).getText();

            List<List<Integer>> keys = IO_Utilities.readKeyFile(keyFilePath);

            String password = String.valueOf(
                    ((JPasswordField) _map.get("passwordField")).getPassword()
            );

            switch (source) {
                case "lockEncryptButton": {

                    ((JTextArea) _map.get("textArea")).setText(
                            EncryptionMatrixMkII.encryptSequence(content,keys,password));
                }
                break;

                case "unlockDecryptButton" : {

                    ((JTextArea) _map.get("textArea")).setText(
                           EncryptionMatrixMkII.decryptSequence(content,keys,password));

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
