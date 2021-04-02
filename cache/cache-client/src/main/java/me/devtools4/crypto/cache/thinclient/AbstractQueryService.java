package me.devtools4.crypto.cache.thinclient;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.cache.Cache;
import javax.cache.Cache.Entry;

import io.micrometer.core.annotation.Timed;
import me.devtools4.crypto.cache.api.QueryService;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.query.QueryCursor;
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
  @Timed(value = "all", percentiles = {0.5, 0.95, 0.99})
  public Stream<V> all(Set<K> keys) {
    return client.getAll(keys)
        .values()
        .stream();
  }

  @Override
  @Timed(value = "size", percentiles = {0.5, 0.95, 0.99})
  public Integer size() {
    return client.size(CachePeekMode.ALL);
  }

  @Override
  @Timed(value = "find", percentiles = {0.5, 0.95, 0.99})
  public Optional<V> find(K key) {
    return Optional.ofNullable(client.get(key));
  }

  @Override
  @Timed(value = "findAll", percentiles = {0.5, 0.95, 0.99})
  public Map<K, V> findAll(Set<K> keys) {
    return client.getAll(keys);
  }

  @Override
  @Timed(value = "query", percentiles = {0.5, 0.95, 0.99})
  public Stream<V> query(String query, Object... args) {
    QueryCursor<Entry<K, V>> q = client
        .query(new SqlQuery<K, V>(type, query).setArgs(args));
    return StreamSupport
        .stream(q.spliterator(), false)
        .map(Cache.Entry::getValue);
  }
}