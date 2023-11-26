package finalVersionBitmexBot.model.authentification;

public class AuthenticationHeaders {
    private long expires;
    private String apiKey;
    private String apiSecret;

    public AuthenticationHeaders() {
        expires = System.currentTimeMillis() / 1000 + 5;
        apiKey = "DI-FVmnjsNvWGcJLEyVxqncH";
        apiSecret = "bv3Z35DKSh7No26QZfYGsx75QBwo8KasCpkD2hKDJ5yLmd7v";
        // TODO: 26.11.2023 тут нужно убрать хардкод и закинуть все в пропертиес.
        //  Пропертиес созданы, внизу метод по загрузке но он не пашет
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    //    public AuthenticationHeaders() {
//        loadProperties("properties.props");
//    }
//
//    public void loadProperties(String filePath) {
//        Properties props = new Properties();
//        try {
//            props.load(new FileInputStream(filePath));
//            this.apiKey = props.getProperty("apiKey");
//            this.apiSecret = props.getProperty("apiSecret");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
