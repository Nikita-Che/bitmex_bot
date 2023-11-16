package ilyaVersion.bitmexBot.service;

import ilyaVersion.bitmexBot.model.AuthenticationHeaders;
import ilyaVersion.bitmexBot.model.Order;
import ilyaVersion.bitmexBot.util.Signature;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class BitmexClient {
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final Signature signature = new Signature();
    private final String apiSecretKey;
    private final String apiKey;
    private final String baseUrl;
    private final boolean isReal;
    private final int EXPIRES_DELAY = 5;
    public void sendOrder(Order order) {

    }

    public void cancelOrder(String orderId) {

    }


    private AuthenticationHeaders getAuthenticationHeaders(String httpMethod, String data, String path) throws NoSuchAlgorithmException, InvalidKeyException {
        long expires = System.currentTimeMillis() / 1000 + EXPIRES_DELAY;

        String signatureStr = signature.getSignature(apiSecretKey, httpMethod + path + expires + data);

        return AuthenticationHeaders.builder()
                .apiKey(apiKey)
                .signature(signatureStr)
                .expires(Long.toString(expires))
                .build();
    }

}
