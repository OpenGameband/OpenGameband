package org.opengameband.launcher;

import org.opengameband.exceptions.LauncherFailiure;
import org.opengameband.exceptions.LauncherInstallFailure;

import java.io.File;

public interface Launcher {
    void start() throws LauncherFailiure;

    String getName();

    void install() throws LauncherInstallFailure;

    File getInstallDir();

    File getGameDataDir();

    File getLauncherDir();

    boolean isInstalled();
}
