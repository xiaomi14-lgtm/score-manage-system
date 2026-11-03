import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class GenHash {
    static final int ITERATIONS = 10000;
    static final int KEY_LENGTH = 256;
    static final int SALT_LENGTH = 16;

    static String hash(String password) throws Exception {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] h = factory.generateSecret(spec).getEncoded();
        return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(h);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("admin123=" + hash("admin123"));
        System.out.println("123456=" + hash("123456"));
    }
}
