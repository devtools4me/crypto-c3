package me.devtools4.crypto.coinapi.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "coinapi")
public class CoinapiProps {
  private String apiKey;
  private String wsUrl;
  private List<String> types;

  public URI wsUri() {
    try {
      return new URI(wsUrl);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("wsUrl=" + wsUrl);
    }
  }

  public String[] typesArray() {
    return types.toArray(new String[] {});
  }
}