package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.authentification.AuthenticationCipher;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import finalVersionBitmexBot.model.util.Endpoints;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureServiceImpl implements SignatureService {
    private final AuthenticationHeaders authenticationHeaders = new AuthenticationHeaders();
    private static final Logger logger = LogManager.getLogger(SignatureServiceImpl.class);

    @Override
    public String createSignature(String verb, String url, String data) {
        String signature =
                generateSignature(authenticationHeaders.getApiSecret(),
                        verb,
                        url,
                        authenticationHeaders.getExpires(),
                        data);
        return signature;
    }

    @Override
    public String createSignatureForOrdersList(String verb, String url, String data) {
        String signature =
                generateSignatureForOrderList(authenticationHeaders.getApiSecret(),
                        verb,
                        url,
                        authenticationHeaders.getExpires(),
                        data);
        return signature;
    }

    private String generateSignature(String secret, String verb, String url, long expires, String data) {
        String message = verb + url + expires + data;

        String signature = "";
        try {
            Mac sha256_HMAC = Mac.getInstance(AuthenticationCipher.HMAC_SHA256_ALGORITHM);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), AuthenticationCipher.HMAC_SHA256_ALGORITHM);
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
            logger.error(e.getMessage());
        }

        return signature;
    }

    private String generateSignatureForOrderList(String secret, String verb, String url, long expires, String data) {
        String path = url;
        String query = "";
        try {
            java.net.URL parsedURL = new java.net.URL(url);
            path = parsedURL.getPath();
            query = parsedURL.getQuery();
        } catch (java.net.MalformedURLException e) {
            logger.error(e.getMessage());
        }

        if (query != null && !query.isEmpty()) {
            path = path + "?" + query;
        }

        if (data != null && !data.isEmpty()) {
            data = URLEncoder.encode(data, StandardCharsets.UTF_8);
        }

        String message = verb + path + expires + data;

        String signature = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(message.getBytes());
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
            logger.error(e.getMessage());
        }

        return signature;
    }

    @Override
    public String generateSignatureWebSocket() {
//        try {
//            String message = "GET" + Endpoints.BASE_TEST_URL_WEBSOCKET_REALTIME + authenticationHeaders.getExpires();
//            Mac sha256Hmac = Mac.getInstance(AuthenticationCipher.HMAC_SHA256_ALGORITHM);
//            SecretKeySpec secretKeySpec = new SecretKeySpec(authenticationHeaders.getApiSecret().getBytes(), AuthenticationCipher.HMAC_SHA256_ALGORITHM);
//            sha256Hmac.init(secretKeySpec);
//            byte[] signatureBytes = sha256Hmac.doFinal(message.getBytes());
//            return authenticationHeaders.getApiKey() + ":" + authenticationHeaders.getExpires() + ":" + Base64.getEncoder().encodeToString(signatureBytes);
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            logger.error(e.getMessage());
//            return null;
//        }
        try {
            String message = "GET" + Endpoints.BASE_TEST_URL_WEBSOCKET_REALTIME + authenticationHeaders.getExpires();
            Mac sha256Hmac = Mac.getInstance(AuthenticationCipher.HMAC_SHA256_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(authenticationHeaders.getApiSecret().getBytes(), AuthenticationCipher.HMAC_SHA256_ALGORITHM);
            sha256Hmac.init(secretKeySpec);
            byte[] signatureBytes = sha256Hmac.doFinal(message.getBytes());

            // Конвертировать байты подписи в строку шестнадцатеричных символов
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : signatureBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
