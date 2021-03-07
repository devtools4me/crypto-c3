package me.devtools4.crypto.cache.api.impl;

import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.apache.ignite.client.ClientCache;

public class OhlcvEventQueryService extends AbstractQueryService<String, OhlcvEvent> {

  public OhlcvEventQueryService(ClientCache<String, OhlcvEvent> client) {
    super(client, OhlcvEvent.class);
  }
}