package org.opengameband.exceptions;

public class LauncherInstallFailure extends Exception {
    public LauncherInstallFailure() {
        super();
    }

    public LauncherInstallFailure(String message) {
        super(message);
    }

    public LauncherInstallFailure(String message, Throwable cause) {
        super(message, cause);
    }

    public LauncherInstallFailure(Throwable cause) {
        super(cause);
    }

    public LauncherInstallFailure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
