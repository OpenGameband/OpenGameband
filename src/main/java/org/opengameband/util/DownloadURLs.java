package org.opengameband.util;

/**
 * @author Zaprit <henry@vorax.org>
 */
public enum DownloadURLs {
    MAC("https://launcher.mojang.com/download/Minecraft.dmg", "/Minecraft.dmg"),
    LIN("https://launcher.mojang.com/download/Minecraft.tar.gz", "/Minecraft.tar.gz"),
    WIN("https://launcher.mojang.com/download/Minecraft.exe", "/Minecraft.exe");

    private String url;
    private String file;

    DownloadURLs(String url, String file) {
        this.url = url;
        this.file = file;
    }

    public String getURL() {
        return url;
    }


    public String getFile() {
        return file;
    }

    public static DownloadURLs getOSDownloadURL() {
        switch (System.getProperty("os.name").split(" ")[0]) {
            case "Windows":
                return WIN;
            case "Mac":
                return MAC;
            case "Linux":
                return LIN;
        }
        return null;
    }
}
