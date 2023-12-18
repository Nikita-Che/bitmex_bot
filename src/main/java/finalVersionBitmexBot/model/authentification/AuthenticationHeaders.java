package finalVersionBitmexBot.model.authentification;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter
public class AuthenticationHeaders {
    private final long expires = System.currentTimeMillis() / 1000 + 5;
    private final String apiKey;
    private final String apiSecret;

    public AuthenticationHeaders() {
        Properties properties = new Properties();
        try {
//            properties.load(new FileInputStream("D:\\Java\\repo\\bitmex_bot\\properties.props"));
            properties.load(new FileInputStream("C:\\Users\\nikita\\Desktop\\GitHub\\bitmex_bot\\properties.props"));

            apiKey = properties.getProperty("apiKey");
            apiSecret = properties.getProperty("apiSecret");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
