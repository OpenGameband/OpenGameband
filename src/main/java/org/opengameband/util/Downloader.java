package org.opengameband.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * @author Zaprit
 */
public class Downloader extends SwingWorker<Void, Void> {
    private static final int BUFFER_SIZE = 4096;
    private final String downloadURL;
    private final String outputFileName;
    private final Consumer<String> callBack;

    public Downloader(Consumer<String> callBack, String downloadURL, String outputFileName) {
        this.callBack = callBack;
        this.downloadURL = downloadURL;
        this.outputFileName = outputFileName;
    }

    /**
     * Executed in background thread
     */
    @Override
    protected Void doInBackground() {
        try {
            //  gui.downloadDone = false;
            URL download = new URL(downloadURL);
            URLConnection connection = download.openConnection();

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long totalBytesRead = 0;
            int percentCompleted;
            long fileSize = connection.getContentLength();

            while ((bytesRead = connection.getInputStream().read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentCompleted = (int) (totalBytesRead * 100 / fileSize);
                setProgress(percentCompleted);
            }

            outputStream.close();

            connection.getInputStream().close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error downloading file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            setProgress(0);
            cancel(true);
        }
        return null;
    }

    /**
     * Executed in Swing's event dispatching thread
     */
    @Override
    protected void done() {
        setProgress(0);
        callBack.accept(outputFileName);
    }
}