/*
     * Jack Stillwell
     * jes448@drexel.edu
     * CS338:GUI, Assignment 1
*/


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComponentTrackingUtility {
    /*
     * A component retrieval utility acquired from
     * http://www.java2s.com/Code/Java/Swing-JFC/GetAllComponentsinacontainer.htm
     */
    static java.util.List<Component> getAllComponents(final Container c) {

        Component[] comps = c.getComponents();
        java.util.List<Component> compList = new ArrayList<Component>();

        for (Component comp : comps) {

            compList.add(comp);

            if(comp instanceof MenuElement)
            {
                compList.addAll(getAllComponents((MenuElement) comp));
            }

            else if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }

        return compList;
    }

    static java.util.List<Component> getAllComponents(final MenuElement c) {

        MenuElement[] comps = c.getSubElements();
        java.util.List<Component> compList = new ArrayList<Component>();

        for (MenuElement comp : comps) {

            compList.add((Component) comp);

            if(comp != null)
            {
                compList.addAll(getAllComponents((MenuElement) comp));
            }
        }

        return compList;
    }

    /*
     * basic idea taken from
     * https://stackoverflow.com/questions/4958600/get-a-swing-component-by-name
     */
    public static HashMap<String, Component> buildComponentMap(Container frame)
    {
        HashMap<String, Component> toReturn = new HashMap<String, Component>();

        List<Component> components = getAllComponents(frame);

        for (Component component : components) {
            if (component.getName() != null) {
                toReturn.put(component.getName(), component);
            }
        }

        return toReturn;
    }
}
