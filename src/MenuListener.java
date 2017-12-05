import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MenuListener implements MouseListener {

    HashMap<String, Component> _map;

    public MenuListener(HashMap<String, Component> map)
    {
        _map = map;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(((JMenuItem)e.getSource()).getName())
        {
            case "selectKeyFileButton" :
            {
                JFileChooser fileChooser = new JFileChooser(new File("."));

                int returnval = fileChooser.showOpenDialog(_map.get("master"));

                if(returnval == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();

                    String keyFilePath = file.getPath();

                    try
                    {
                        List<List<Integer>> keys = IO_Utilities.readKeyFile(keyFilePath);

                        ((JLabel) _map.get("levelDisplay")).setText(keys.size()/2 + "");

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

            case "createKeyFileButton" :
            {
                CreateKeyFileDialog dialog = new CreateKeyFileDialog(_map);

                dialog.display();

                dialog.frame.addWindowListener(new WindowAdapter() {
                                           @Override
                                           public void windowClosed(WindowEvent e) {
                                               SwingUtilities.enableButtonInput(_map);
                                               _map.get("lockEncryptButton").setEnabled(false);
                                               _map.get("unlockDecryptButton").setEnabled(false);

                                           }
                                       }
                );
            SwingUtilities.disableButtonInput(_map);
        } break;

            case "loadButton" :
            {
                JFileChooser fileChooser = new JFileChooser(new File("."));

                int returnval = fileChooser.showOpenDialog(_map.get("master"));

                if(returnval == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();

                    String filePath = file.getPath();

                    try
                    {
                        String input = IO_Utilities.readTextFile(filePath);

                        ((JTextArea) _map.get("textArea")).setText(input);

                        ((JTextArea) _map.get("commandArea")).append("file loaded successfully\n");
                    }

                    catch(Exception x)
                    {
                        ((JTextArea) _map.get("commandArea")).append("Error loading file: " + x + "\n");
                    }
                }

                else
                {
                    ((JTextArea) _map.get("commandArea")).append("Load File Operation Cancelled\n");
                }
            } break;

            case "saveButton" :
            {
                JFileChooser fileChooser = new JFileChooser(new File("."));

                int returnval = fileChooser.showSaveDialog(_map.get("master"));

                if(returnval == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();

                    String filePath = file.getPath();

                    try
                    {
                        String input = ((JTextArea) _map.get("textArea")).getText();

                        IO_Utilities.writeTextFile(input, filePath);

                        ((JTextArea) _map.get("commandArea")).append("file saved successfully\n");
                    }

                    catch(Exception x)
                    {
                        ((JTextArea) _map.get("commandArea")).append("Error saving file: " + x + "\n");
                    }
                }

                else
                {
                    ((JTextArea) _map.get("commandArea")).append("Save File Operation Cancelled\n");
                }
            } break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
