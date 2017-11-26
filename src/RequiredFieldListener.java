import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class RequiredFieldListener implements KeyListener {

    HashMap<String, Component> _map;

    public RequiredFieldListener(HashMap<String, Component> map)
    {
        _map = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        String input = ((JTextArea) _map.get("textArea")).getText();
        char[] password = ((JPasswordField) _map.get("passwordField")).getPassword();

        if(input.length() > 0 && password.length > 0) {

            _map.get("lockEncryptButton").setEnabled(true);
            _map.get("unlockDecryptButton").setEnabled(true);

        }

        else
        {
            _map.get("lockEncryptButton").setEnabled(false);
            _map.get("unlockDecryptButton").setEnabled(false);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
