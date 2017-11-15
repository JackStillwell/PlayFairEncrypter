import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class SwingUtilities {

    public static void disableButtonInput(HashMap<String, Component> map)
    {

        Set<String> toDisable = map.keySet();

        toDisable.remove("master");
        toDisable.remove("commandArea");
        toDisable.remove("File");

        for(String k : toDisable)
        {
            map.get(k).setEnabled(false);
        }
    }

    public static void enableButtonInput(HashMap<String, Component> map)
    {
        Set<String> toEnable = map.keySet();

        toEnable.remove("master");
        toEnable.remove("commandArea");
        toEnable.remove("File");

        for(String k : toEnable)
        {
            map.get(k).setEnabled(true);
        }
    }
}
