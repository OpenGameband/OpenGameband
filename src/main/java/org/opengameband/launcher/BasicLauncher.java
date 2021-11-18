package org.opengameband.launcher;

import org.opengameband.*;
import org.opengameband.exceptions.LauncherFailiure;
import org.opengameband.util.DownloadURLs;
import org.opengameband.util.Downloader;
import org.opengameband.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.opengameband.util.MountPoint.GetMountPoint;

/**
 * @author Zaprit
 * This is a launcher that just gets the launcher from Minecraft.net
 */
public class BasicLauncher implements Launcher {

    public BasicLauncher() {
    }

    @Override
    public void start() throws LauncherFailiure {
        try {
            switch (System.getProperty("os.name").split(" ")[0]) {
                case "Mac": {
                    Runtime.getRuntime().exec(new String[]{GetMountPoint().getAbsolutePath() + "/Launchers/Official/Minecraft.app/Contents/MacOS/launcher",
                            "--workDir", getGameDataDir().getAbsolutePath()});
                }
                case "Windows": {
                    Runtime.getRuntime().exec(new String[]{getInstallDir().getAbsolutePath() + "\\Minecraft.exe",
                            "--workDir", getGameDataDir().getAbsolutePath()});
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new LauncherFailiure();
        }
    }

    @Override
    public String getName() {
        return "Minecraft Official Launcher";
    }

    @Override
    public void install() {

        if (!getInstallDir().mkdirs() && !getInstallDir().exists()) {
            System.out.println("Not all subdirectories were created, some directories may already exist, or there might be permissions issues");
        }
        System.out.println("Downloading launcher from " + Objects.requireNonNull(DownloadURLs.getOSDownloadURL()).getURL());
        Downloader downloader = new Downloader(this::extract, DownloadURLs.getOSDownloadURL().getURL(), new File(getInstallDir(), DownloadURLs.getOSDownloadURL().getFile()).getAbsolutePath());
        downloader.addPropertyChangeListener(Main.getWindow());
        downloader.execute();
    }

    private void extract(String file) {
        switch (System.getProperty("os.name").split(" ")[0]) {
            case "Mac":
                try {
                    Process p = Runtime.getRuntime().exec("hdiutil attach " + file);
                    byte[] inputBytes = p.getInputStream().readAllBytes();
                    String stdin = new String(inputBytes);
                    System.out.println(stdin);
                    System.out.println(stdin.substring(stdin.indexOf("/dev/disk")));
                    Thread.sleep(1000);
                    FileUtils.CopyDir(Paths.get("/Volumes/Minecraft/Minecraft.app"), getInstallDir().toPath());

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();

                }
                break;
            case "Linux":
                throw new RuntimeException("Not Yet Implemented");
        }
    }

    @Override
    public File getLauncherDir() {
        return new File(GetMountPoint(), "/Launchers/Official");
    }

    /**
     * @return The launcher location or null if the working dir is missing.
     */
    @Override
    public File getInstallDir() {
        if (GetMountPoint().exists() && GetMountPoint().isDirectory() && GetMountPoint().canRead()) {
            switch (System.getProperty("os.name").split(" ")[0]) {
                case "Windows":
                    return new File(getLauncherDir(), "\\win");
                case "Mac":
                    return new File(GetMountPoint(), "/Launchers/Official/Minecraft.app");
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
        return getInstallDir().exists() && getInstallDir().listFiles().length > 0;
    }
}
