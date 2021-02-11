package me.devtools4.crypto.cache.config;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.Optional;
import javax.cache.expiry.EternalExpiryPolicy;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import me.devtools4.crypto.dto.avro.TradeEvent;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.QueryIndex;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;

public class CacheConfigs {

  @Bean
  public CacheConfiguration<String, TradeEvent> tradeEventCacheConfiguration(IgniteProps props) {
    return configure(new CacheConfiguration<String, TradeEvent>(TradeEvent.class.getCanonicalName())
        .setTypes(String.class, TradeEvent.class)
        .setQueryEntities(Collections.singleton(
            new QueryEntity(String.class, TradeEvent.class)
                .addQueryField("uuid", String.class.getName(), null)
                .setIndexes(ImmutableList.of(
                    new QueryIndex("uuid")
                ))
        )), props.getBackups());
  }

  @Bean
  public CacheConfiguration<Long, OhlcvEvent> ohlcvEventCacheConfiguration(IgniteProps props) {
    return configure(new CacheConfiguration<Long, OhlcvEvent>(OhlcvEvent.class.getCanonicalName())
        .setTypes(Long.class, OhlcvEvent.class)
        .setQueryEntities(Collections.singleton(
            new QueryEntity(Long.class, OhlcvEvent.class)
                .addQueryField("sequence", Long.class.getName(), null)
                .setIndexes(ImmutableList.of(
                    new QueryIndex("sequence")
                ))
        )), props.getBackups());
  }

  public static <K, V> CacheConfiguration<K, V> configure(CacheConfiguration<K, V> conf, Integer backups) {
    conf
        .setAtomicityMode(CacheAtomicityMode.ATOMIC)
        .setExpiryPolicyFactory(EternalExpiryPolicy::new)
        .setCacheMode(CacheMode.PARTITIONED)
        .setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
    Optional.ofNullable(backups).ifPresent(conf::setBackups);
    return conf;
  }
}