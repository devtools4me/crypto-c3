package me.devtools4.crypto.cache.api.impl;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.cache.Cache;
import javax.cache.Cache.Entry;
import me.devtools4.crypto.cache.api.QueryService;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.client.ClientCache;

public class AbstractQueryService<K, V> implements QueryService<K, V> {

  private final ClientCache<K, V> client;
  private final Class<V> type;

  public AbstractQueryService(ClientCache<K, V> client, Class<V> type) {
    this.client = client;
    this.type = type;
  }

  @Override
  public Stream<V> all() {
    return client
        .query(new ScanQuery<K, V>(null))
        .getAll()
        .stream()
        .map(Cache.Entry::getValue);
  }

  @Override
  public Integer size() {
    return client.size(CachePeekMode.ALL);
  }

  @Override
  public Optional<V> find(K key) {
    return Optional.ofNullable(client.get(key));
  }

  @Override
  public Stream<V> find(String query, Object... args) {
    QueryCursor<Entry<K, V>> q = client
        .query(new SqlQuery<K, V>(type, query).setArgs(args));
    return StreamSupport
        .stream(q.spliterator(), false)
        .map(Cache.Entry::getValue);
  }
}