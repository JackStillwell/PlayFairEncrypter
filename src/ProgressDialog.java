import javafx.scene.control.ProgressBar;

import javax.swing.*;

public class ProgressDialog {

    public JProgressBar progressBar;
    JDialog dialog;

    public ProgressDialog(JFrame parent)
    {
        dialog = new JDialog(parent, "Progress");

        progressBar =  new JProgressBar(0,100);

        progressBar.setStringPainted(true);

        dialog.add(progressBar);

        dialog.setLocationRelativeTo(parent);
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void display()
    {
        dialog.setVisible(true);
    }

    public void hide()
    {
        dialog.setVisible(false);
        dialog.dispose();
    }
}
