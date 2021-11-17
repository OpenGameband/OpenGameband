package launcher;

/**
 * @author Zaprit <henry@vorax.org>
 */
public enum DownloadURLs {
    MAC("https://launcher.mojang.com/download/Minecraft.dmg", "/Minecraft.dmg"),
    LIN("https://launcher.mojang.com/download/Minecraft.tar.gz", "/Minecraft.tar.gz"),
    WIN("https://launcher.mojang.com/download/MinecraftInstaller.msi", "/Minecraft.msi");

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
    public static DownloadURLs getOSDownloadURL(){
        switch(System.getProperty("os.name")){
            case "Windows":
                return WIN;
            case "Mac OS X":
                return MAC;
            case "Linux":
                return LIN;
        }
        return null;
    }
}
