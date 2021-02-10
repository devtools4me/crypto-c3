package me.devtools4.crypto.cache.api;

import java.util.Collection;
import java.util.Optional;

public interface QueryService<T, I> {
  Collection<T> all();
  Integer size();
  Optional<T> find(I id);
}