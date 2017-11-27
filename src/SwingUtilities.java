import java.awt.*;
import java.util.*;
import java.util.List;

public class SwingUtilities {

    public static void disableButtonInput(HashMap<String, Component> map)
    {

        List<String> toDisable = new ArrayList<String>();

        toDisable.add("unlockDecryptButton");
        toDisable.add("lockEncryptButton");
        toDisable.add("textArea");
        toDisable.add("fileMenu");
        toDisable.add("keyFilePathField");
        toDisable.add("keyFileChooser");
        toDisable.add("passwordField");
        //toDisable.add("helpMenu");
        //toDisable.add("aboutMenu");

        for(String k : toDisable)
        {
            map.get(k).setEnabled(false);
        }
    }

    public static void enableButtonInput(HashMap<String, Component> map)
    {
        List<String> toEnable = new ArrayList<>();

        toEnable.add("unlockDecryptButton");
        toEnable.add("lockEncryptButton");
        toEnable.add("textArea");
        toEnable.add("fileMenu");
        toEnable.add("keyFilePathField");
        toEnable.add("keyFileChooser");
        toEnable.add("passwordField");
        //toEnable.add("helpMenu");
        //toEnable.add("aboutMenu");

        for(String k : toEnable)
        {
            map.get(k).setEnabled(true);
        }
    }
}
