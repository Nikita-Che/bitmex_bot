package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.Signature;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureServiceImpl implements SignatureService{
    private Signature signature;
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    @Override
    public String createSignature() {
        generateSignature(signature.getApiSecret(),signature.getVerb(), signature.getPath(),signature.getExpires(),signature.getData());
        // тут создаем подпимсь и передаем ее в клиента
        return "";
    }

    private String generateSignature(String secret, String verb, String url, long expires, String data) {
        String message = verb + url + expires + data;

        String signature = "";
        try {
            Mac sha256_HMAC = Mac.getInstance(HMAC_SHA256_ALGORITHM);  //НЕ ОТСЮДА А С ПОДПИСИ БРАТЬ
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            signature = hexString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return signature;
    }
}
