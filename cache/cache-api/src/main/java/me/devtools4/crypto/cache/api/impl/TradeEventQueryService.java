package me.devtools4.crypto.cache.api.impl;

import me.devtools4.crypto.dto.avro.TradeEvent;
import org.apache.ignite.client.ClientCache;

public class TradeEventQueryService extends AbstractQueryService<String, TradeEvent> {

  public TradeEventQueryService(ClientCache<String, TradeEvent> client) {
    super(client, TradeEvent.class);
  }
}