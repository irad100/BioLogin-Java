package etc;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;


public class FileLocker {
    final static String ALL_ACCESS="rwxrwxrwx";
    final static String NO_ACCESS="---------";

    public static void lock(String strPath) throws IOException {
        encryptFiles(new File(strPath), Cipher.ENCRYPT_MODE);
        permitFiles(new File(strPath), NO_ACCESS);
    }
    public static void unlock(String strPath) throws IOException {
        permitFiles(new File(strPath), ALL_ACCESS);
        encryptFiles(new File(strPath), Cipher.DECRYPT_MODE);
    }
    public static void permitFiles(File file, String permissions) throws IOException {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f: files) {
                permitFiles(f, permissions);
            }
            Files.setPosixFilePermissions(Paths.get(file.getPath()), PosixFilePermissions.fromString(permissions));
        }
        else {
            Files.setPosixFilePermissions(Paths.get(file.getPath()), PosixFilePermissions.fromString(permissions));
        }
    }
    public static void encryptFiles(File file, int cipherMode) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f: files) {
                encryptFiles(f, cipherMode);
            }
        }
        else {
            Crypto.fileProcessor(cipherMode, "R9YRrBx6nuoY0Rp3qn5e7gg79UFHUtMT", file.getPath());
        }
    }


}
