package me.devtools4.crypto.cache.thinclient;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.client.SslMode;
import org.apache.ignite.configuration.ClientConfiguration;

@Slf4j
public class IgniteThinClientRun {
  public static void main(String[] args) {
    var conf = new ClientConfiguration()
        .setAddresses("localhost:10800")
        .setSslMode(SslMode.DISABLED);
    IgniteClient ignite = Ignition.startClient(conf);
    ignite.cacheNames().forEach(log::info);

    ClientCache<String, OhlcvEvent> client = ignite
        .cache(OhlcvEvent.class.getCanonicalName());
    var service = new OhlcvEventQueryService(client);
    log.info("size={}", service.size());

    client.put(UUID.randomUUID().toString(), OhlcvEvent.newBuilder()
        .build());
    log.info("size={}", service.size());
  }
}