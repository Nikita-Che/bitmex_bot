package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.Signature;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureServiceImpl implements SignatureService{
    private final Signature signature = new Signature();
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    AuthenticationHeaders authenticationHeaders = new AuthenticationHeaders();

    @Override
    public String createSignature(String verb, String url, String data) {
        // 26.11.2023 взять данные из параметров которые тебе передали из битмекс клиента и передать их в метод generateSignature

        String s = generateSignature(authenticationHeaders.getApiSecret(),
                verb,
                url,
                authenticationHeaders.getExpires(),
                data);
        return s;
    }

    private String generateSignature(String secret, String verb, String url, long expires, String data) {
        String message = verb + url + expires + data;

        String signature = "";
        try {
            Mac sha256_HMAC = Mac.getInstance(HMAC_SHA256_ALGORITHM);  //НЕ ОТСЮДА А С ПОДПИСИ БРАТЬ или вообще сделать класс шифр енамом. и выбирать шифр оттуда
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
