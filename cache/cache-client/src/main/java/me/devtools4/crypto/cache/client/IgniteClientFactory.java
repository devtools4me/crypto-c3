package me.devtools4.crypto.cache.client;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.failure.StopNodeOrHaltFailureHandler;
import org.apache.ignite.spi.discovery.DiscoverySpi;

public class IgniteClientFactory {

  public static Ignite ignite(DiscoverySpi spi) {
    var conf = new IgniteConfiguration()
        .setPeerClassLoadingEnabled(true)
        //.setSslContextFactory()
        .setDiscoverySpi(spi)
        .setClientMode(true)
        .setFailureHandler(new StopNodeOrHaltFailureHandler());
    return Ignition.start(conf);
  }

  public static <K, V> IgniteCache<K, V> cache(Ignite ignite, CacheConfiguration<K, V> conf) {
    return ignite.getOrCreateCache(conf);
  }

  public static <K, V> CacheConfiguration<K, V> config(String name) {
    return new CacheConfiguration<>(name);
  }
}