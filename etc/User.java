package etc;

import jlibfprint.PrintData;

import java.util.UUID;

public class User {
    private String name;
    private UUID face;
    private PrintData fingerprint;
    private UUID voice;

    public User() { name = System.getProperty("user.name"); }

    public String getName() {
        return name;
    }

    public UUID getFace() {
        return face;
    }

    public PrintData getFingerprint() {
        return fingerprint;
    }

    public UUID getVoice() {
        return voice;
    }

    public void setFace(UUID face) { this.face = face; }

    public void setFingerprint(PrintData fingerprint) {
        this.fingerprint = fingerprint;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVoice(UUID voice) {
        this.voice = voice;
    }
}
