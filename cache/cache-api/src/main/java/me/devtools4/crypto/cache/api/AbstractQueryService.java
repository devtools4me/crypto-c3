package me.devtools4.crypto.cache.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class AbstractQueryService<C, I> implements QueryService<C, I> {

  @Override
  public Collection<C> all() {
    return Collections.emptyList();
  }

  @Override
  public Integer size() {
    return 0;
  }

  @Override
  public Optional<C> find(I id) {
    return Optional.empty();
  }
}