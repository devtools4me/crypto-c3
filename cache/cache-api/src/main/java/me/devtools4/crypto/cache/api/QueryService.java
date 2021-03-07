package me.devtools4.crypto.cache.api;

import java.util.Optional;
import java.util.stream.Stream;

public interface QueryService<K, V> {

  Stream<V> all();

  Integer size();

  Optional<V> find(K key);

  Stream<V> find(String query, Object... args);
}