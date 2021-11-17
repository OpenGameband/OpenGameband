package org.opengameband.launcher;

import jdk.jshell.spi.ExecutionControl;
import org.opengameband.*;
import org.opengameband.exceptions.LauncherFailiure;
import org.opengameband.exceptions.LauncherInstallFailure;
import org.opengameband.util.DownloadURLs;
import org.opengameband.util.Downloader;

import java.io.File;

import static org.opengameband.util.MountPoint.GetMountPoint;

/**
 * @author Zaprit
 * This is a launcher that just gets the launcher from Minecraft.net
 */
public class BasicLauncher implements Launcher {

    OpenGameband gb;

    public BasicLauncher(OpenGameband gb) {
        this.gb = gb;
    }

    @Override
    public void start() throws LauncherFailiure {
        System.out.println("Not implemented yet!");
    }

    @Override
    public String getName() {
        return "Minecraft Official Launcher";
    }

    @Override
    public void install() throws LauncherInstallFailure {
        getInstallDir().mkdirs();
        System.out.println("Downloading launcher from " + DownloadURLs.getOSDownloadURL());
        Downloader downloader = new Downloader(gb, DownloadURLs.getOSDownloadURL().getURL(), new File(getInstallDir(), DownloadURLs.getOSDownloadURL().getFile()).getAbsolutePath());
        downloader.addPropertyChangeListener(gb);
        downloader.execute();
    }

    /**
     * @return The launcher location or null if the working dir is missing.
     */
    @Override
    public File getInstallDir() {
        if (GetMountPoint().exists() && GetMountPoint().isDirectory() && GetMountPoint().canRead()) {
            switch (System.getProperty("os.name").split(" ")[0]) {
                case "Windows":
                    return new File(GetMountPoint(), "/Launchers/Official/win");
                case "Mac":
                    return new File(GetMountPoint(), "/Launchers/Official/");
                case "Linux":
                    return new File(GetMountPoint(), "/Launchers/Official/lin");
            }
        }
        return null;
    }

    @Override
    public File getGameDataDir() {
        if (GetMountPoint().exists() && GetMountPoint().isDirectory()) {
            return new File(GetMountPoint(), "/Launchers/Official/Game");
        }
        return null;
    }

    @Override
    public boolean isInstalled() {
        return getInstallDir().exists();
    }
}
