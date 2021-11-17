package opengameband;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import static gameband.MountPoint.GetMountPoint;

/**
 * @author Zaprit <henry@vorax.org>
 */
public class Downloader extends SwingWorker<Void, Void> {
    private static final int BUFFER_SIZE = 4096;
    private final String downloadURL;
    private final String outputFileName;
    private OpenGameband gui;

    public Downloader(OpenGameband gui, String downloadURL, String outputFileName) {
        this.gui = gui;
        this.downloadURL = downloadURL;
        this.outputFileName = outputFileName;
    }

    /**
     * Executed in background thread
     */
    @Override
    protected Void doInBackground() throws Exception {
        try {

            URL download = new URL(downloadURL);
            URLConnection connection = download.openConnection();

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            long totalBytesRead = 0;
            int percentCompleted = 0;
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
        if (!isCancelled()) {

            JOptionPane.showMessageDialog(null,
                    "File has been downloaded successfully!", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}