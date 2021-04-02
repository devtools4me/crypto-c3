package me.devtools4.crypto.cache.config;

import java.util.Collections;
import java.util.List;
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
                .setIndexes(List.of(
                    new QueryIndex("uuid")
                ))
        )), props.getBackups());
  }

  @Bean
  public CacheConfiguration<String, OhlcvEvent> ohlcvEventCacheConfiguration(IgniteProps props) {
    return configure(new CacheConfiguration<String, OhlcvEvent>(OhlcvEvent.class.getCanonicalName())
        .setTypes(String.class, OhlcvEvent.class)
        .setQueryEntities(List.of(
            new QueryEntity(String.class, OhlcvEvent.class)
                .addQueryField("symbolId", String.class.getName(), null)
//                .addQueryField("timeOpen", Instant.class.getName(), null)
//                .addQueryField("timeClose", Instant.class.getName(), null)
                .setIndexes(List.of(
                    new QueryIndex("symbolId")
//                    new QueryIndex("timeOpen"),
//                    new QueryIndex("timeClose")
                ))
        )), props.getBackups());
  }

  public static <K, V> CacheConfiguration<K, V> configure(CacheConfiguration<K, V> conf,
      Integer backups) {
    conf
        .setAtomicityMode(CacheAtomicityMode.ATOMIC)
        .setExpiryPolicyFactory(EternalExpiryPolicy::new)
        .setCacheMode(CacheMode.PARTITIONED)
        .setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
    Optional.ofNullable(backups).ifPresent(conf::setBackups);
    return conf;
  }
}