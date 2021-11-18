package org.opengameband;

import org.opengameband.exceptions.LauncherFailiure;
import org.opengameband.exceptions.LauncherInstallFailure;
import org.opengameband.launcher.BasicLauncher;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenGameband implements PropertyChangeListener {
    private JPanel panel;
    private JProgressBar downloadProgress;
    private JButton downloadMinecraftButton;

    private static boolean isMacOs;
    public BasicLauncher launcher;

    public boolean downloadDone;

    public OpenGameband() {

        if (isMacOs) {
            Desktop.getDesktop().setAboutHandler(e ->
                    JOptionPane.showMessageDialog(null, "OpenGameband Version 0.1.0\n" +
                                    "LAF: " + UIManager.getLookAndFeel().getName() +
                                    "\nRunning on java" + System.getProperty("java.version") + "\n" +
                                    "Copyright Henry Asbridge 2021",
                            "About OpenGameband", JOptionPane.PLAIN_MESSAGE));
            Desktop.getDesktop().setPreferencesHandler(e -> openSettings());

        }
        downloadProgress.putClientProperty("JProgressBar.style", "circular");
        downloadProgress.putClientProperty("JComponent.sizeVariant", "mini");

        launcher = new BasicLauncher(this);

        if(launcher.isInstalled()){
            downloadMinecraftButton.setText("Start minecraft launcher");
        }

        downloadMinecraftButton.addActionListener(e -> {
            try {
                if(!launcher.isInstalled()){
                    launcher.install();
                }
                else {
                    launcher.start();
                }
            } catch (LauncherInstallFailure | LauncherFailiure ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to download minecraft", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().contentEquals("progress")) {
            downloadProgress.setValue((int) evt.getNewValue());
        }
    }

    private void openSettings() {
        throw new UnsupportedOperationException("Not Yet Implemented");
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        if (System.getProperty("os.name", "").startsWith("Mac OS")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Gameband");
            UIManager.setLookAndFeel(new org.violetlib.aqua.AquaLookAndFeel());
            isMacOs = true;
        } else {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            OpenGameband form = new OpenGameband();
            form.panel.setSize(800, 600);
            form.panel.putClientProperty("Aqua.backgroundStyle", "vibrantDark");
            form.panel.putClientProperty("Aqua.windowStyle", "transparentTitleBar");
            JFrame frame = new JFrame("OpenGameband");

            frame.setSize(800, 600);
            frame.setResizable(false);
            frame.setContentPane(form.panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}