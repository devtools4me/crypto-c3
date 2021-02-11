package me.devtools4.crypto.cache.config;

import me.devtools4.crypto.cache.jmx.CacheOps;
import me.devtools4.crypto.cache.jmx.ManagedCacheOps;
import org.apache.ignite.Ignite;
import org.springframework.context.annotation.Bean;

public class JmxConfig {

  @Bean
  public CacheOps cacheOps(Ignite ignite) {
    return new ManagedCacheOps(ignite);
  }
}
