package finalVersionBitmexBot.model;

import finalVersionBitmexBot.model.authentification.AuthenticationHeaders;
import finalVersionBitmexBot.model.order.Verb;

public class Signature {
    private AuthenticationHeaders apiSecret;
    private Verb verb;
    private String path;
    private long expires;
    private String data;
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
}
