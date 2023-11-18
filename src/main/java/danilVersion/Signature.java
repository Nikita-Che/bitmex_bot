package danilVersion;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Signature {
    String verb;
    String path;
    String data;
    long expires;
    String secret = "bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v";

    public Signature(String verb, String path, String data, long expires) {
        this.verb = verb;
        this.path = path;
        this.data = data;
        this.expires = expires;
    }

    public String generateSignature() {
        String signature = "";
        try {
            String toSign = verb + path + expires + data;

            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] hashBytes = mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            signature = stringBuilder.toString();

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return signature;
    }
}
