package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.authentification.AuthenticationCipher;
import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureServiceImpl implements SignatureService{
    AuthenticationHeaders authenticationHeaders = new AuthenticationHeaders();

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
            e.printStackTrace();
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
            e.printStackTrace();
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
        }

        return signature;
    }
}
