package gameband;

import java.io.File;

/**
 * @author Zaprit <henry@vorax.org>
 */
public class MountPoint {
    public MountPoint() {
    }

    /**
     * This gets the working directory of OpenGameband
     * TODO: Add the ability to change this as it may be needed later
     */
    public static File GetMountPoint(){
        return new File(MountPoint.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }
}
