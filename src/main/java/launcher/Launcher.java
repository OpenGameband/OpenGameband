package launcher;

import java.io.File;

public interface Launcher {
    void start() throws LauncherFailiure;
    String getName();
    void install() throws LauncherInstallFailiure;
    File getInstallDir();
    File getGameDataDir();
    boolean isInstalled();
}
