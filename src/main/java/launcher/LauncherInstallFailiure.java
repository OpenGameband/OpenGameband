package launcher;

public class LauncherInstallFailiure extends Exception{
    public LauncherInstallFailiure() {
        super();
    }

    public LauncherInstallFailiure(String message) {
        super(message);
    }

    public LauncherInstallFailiure(String message, Throwable cause) {
        super(message, cause);
    }

    public LauncherInstallFailiure(Throwable cause) {
        super(cause);
    }

    public LauncherInstallFailiure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
