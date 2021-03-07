package me.devtools4.crypto.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ignite")
public class IgniteProps {

  private Client client;
  private Integer backups;
  private Long metricsLogFreq;
  private Long systemWorkerBlockedTimeout;
  private Persistence persistence;
  private Discovery discovery;

  @Data
  public static class Client {

    private String url;
    private Integer port;
  }

  @Data
  public static class Persistence {

    private String storagePath;
    private Long regionInitSize;
  }

  @Data
  public static class Discovery {

    public enum Type {
      LOCAL,
      K8S
    }

    private Type type = Type.LOCAL;
    private String serviceName;
    private String namespace;
    private String masterUrl;
    private String accountToken;
  }
}