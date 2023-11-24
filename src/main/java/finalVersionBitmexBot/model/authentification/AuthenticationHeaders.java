package finalVersionBitmexBot.model.authentification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class AuthenticationHeaders {
    private long expires = System.currentTimeMillis() / 1000 + 5;
    private String apiKey;
    private String apiSecret;

    public AuthenticationHeaders() {
        loadProperties("properties.props");
    }

    public void loadProperties(String filePath) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(filePath));
            this.apiKey = props.getProperty("apiKey");
            this.apiSecret = props.getProperty("apiSecret");
        } catch (IOException e) {
            e.printStackTrace();
        }
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


}
