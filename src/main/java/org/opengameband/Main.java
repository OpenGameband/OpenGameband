package org.opengameband;

import org.opengameband.gui.MainWindow;

import javax.swing.*;

/**
 * @author Zaprit
 */
public class Main {

    private static MainWindow window;

    public static MainWindow getWindow(){
        return window;
    }

    public static void main(String[] args) {


        // Thank you netBeans for teaching me this
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (System.getProperty("os.name").contentEquals("Mac OS X")){
                    try {
                        UIManager.setLookAndFeel("org.violetlib.aqua.AquaLookAndFeel");
                    } catch (Exception ex) {
                        System.err.println("setLookAndFeel failed: " + ex);
                    }
                }else{
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception ex) {
                        System.err.println("setLookAndFeel failed: " + ex);
                    }
                }

                window = new MainWindow("OpenGameband");
                window.setVisible(true);
            }
        });
    }
}
