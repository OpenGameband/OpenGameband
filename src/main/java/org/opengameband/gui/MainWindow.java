package org.opengameband.gui;

import org.opengameband.exceptions.LauncherFailiure;
import org.opengameband.exceptions.LauncherInstallFailure;
import org.opengameband.launcher.BasicLauncher;
import org.opengameband.launcher.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Zaprit
 */
public class MainWindow extends JFrame implements PropertyChangeListener {

    JProgressBar downloadProgress;

    public MainWindow(String title) throws HeadlessException {
        super(title);
        setBounds(0, 0, 800, 600);
        setLayout(new GridBagLayout());
        initComponents();
    }

    private void initComponents() {
        JButton startButton = new JButton("Start Minecraft");
        startButton.addActionListener(this::startButtonActionPerformed);
        add(startButton);

        downloadProgress = new JProgressBar();
        downloadProgress.setLocation(200, 200);
        add(downloadProgress);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public void startButtonActionPerformed(ActionEvent e) {
        try {
            Launcher launch = new BasicLauncher();
            if (!launch.isInstalled()) {
                launch.install();
            }else {
                launch.start();
            }
        } catch (LauncherInstallFailure | LauncherFailiure ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().contentEquals("progress")){
            downloadProgress.setValue((int)evt.getNewValue());
        }
    }
}
