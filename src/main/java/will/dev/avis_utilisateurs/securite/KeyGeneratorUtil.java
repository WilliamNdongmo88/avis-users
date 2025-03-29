package will.dev.avis_utilisateurs.securite;

import org.apache.commons.codec.binary.Hex;
import java.security.SecureRandom;

public class KeyGeneratorUtil {
    public static String generateEncryptionKey(int byteLength) {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[byteLength];
        random.nextBytes(keyBytes);
        return Hex.encodeHexString(keyBytes);
    }
}
