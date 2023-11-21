package finalVersionBitmexBot.model;

public class Signature {
    private final String apiSecret;
    private final String verb;
    private final String path;
    private final long expires;
    private final String data;

    public Signature(String apiSecret, String verb, String path, long expires, String data) {
        this.apiSecret = apiSecret;
        this.verb = verb;
        this.path = path;
        this.expires = expires;
        this.data = data;
    }

    public void generateSignature(String apiSecret, String verb, String path, long expires, String data) {
    }
}
