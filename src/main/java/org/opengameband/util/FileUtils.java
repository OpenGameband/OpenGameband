package org.opengameband.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author Zaprit <henry@vorax.org>
 */
public class FileUtils {
    public static void CopyDir(Path sourceDir, Path destinationDir) throws IOException {
        Files.walk(sourceDir)
                .forEach(sourcePath -> {
                    try {
                        Path targetPath = destinationDir.resolve(sourceDir.relativize(sourcePath));
                        System.out.printf("Copying %s to %s%n", sourcePath, targetPath);
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        System.out.format("I/O error: %s%n", ex);
                    }
                });
    }

}
