package me.devtools4.crypto.cache.config;

import me.devtools4.crypto.cache.api.impl.OhlcvEventQueryService;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.SslMode;
import org.apache.ignite.configuration.ClientConfiguration;
import org.springframework.context.annotation.Bean;

public class TestConfig {
  @Bean
  private OhlcvEventQueryService ohlcvEventQueryService(ClientCache<String, OhlcvEvent> client) {
    return new OhlcvEventQueryService(client);
  }

  @Bean
  public ClientCache<String, OhlcvEvent> ohlcvEventCacheClient(ClientConfiguration conf) {
    return Ignition.startClient(conf)
        .cache(OhlcvEvent.class.getCanonicalName());
  }

  @Bean
  public ClientConfiguration clientConfiguration(IgniteProps props) {
    return new ClientConfiguration()
        .setAddresses(props.getClient().getUrl())
        .setSslClientCertificateKeyStorePassword("")
        .setSslClientCertificateKeyStorePath("")
        .setSslTrustCertificateKeyStorePassword("")
        .setSslTrustCertificateKeyStorePath("")
        .setSslMode(SslMode.DISABLED);
  }
}