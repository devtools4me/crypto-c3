package me.devtools4.crypto.cache.config;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.cache.config.IgniteProps.Client;
import me.devtools4.crypto.cache.config.IgniteProps.Discovery;
import me.devtools4.crypto.cache.config.IgniteProps.Discovery.Type;
import me.devtools4.crypto.cache.config.IgniteProps.Persistence;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConnectorConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.failure.StopNodeFailureHandler;
import org.apache.ignite.spi.discovery.DiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.kubernetes.TcpDiscoveryKubernetesIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
public class IgniteConfig {

  @Value("${spring.application.name:crypto-cache}")
  private String applicationName;

  @Bean
  public Ignite ignite(IgniteProps props, List<CacheConfiguration> configs) {
    log.info("props={}", props);
    IgniteConfiguration conf = new IgniteConfiguration()
        .setIgniteInstanceName(applicationName)
        .setPeerClassLoadingEnabled(true)
        .setDiscoverySpi(discoverySpi(props.getDiscovery()))
        .setClientMode(false)
        .setFailureHandler(new StopNodeFailureHandler());
    Optional.ofNullable(props.getClient())
        .map(IgniteConfig::clientConnectorConf)
        .ifPresent(conf::setClientConnectorConfiguration);
    Optional.ofNullable(props.getMetricsLogFreq())
        .ifPresent(conf::setMetricsLogFrequency);
    Optional.ofNullable(props.getSystemWorkerBlockedTimeout())
        .ifPresent(props::setSystemWorkerBlockedTimeout);
    Optional.ofNullable(props.getPersistence())
        .map(IgniteConfig::dataStorageConf)
        .ifPresent(conf::setDataStorageConfiguration);

    Ignite ignite = Ignition.start(conf);
    ignite.cluster()
        .active(true);

    ignite.getOrCreateCaches(configs);

    return ignite;
  }

  private static ClientConnectorConfiguration clientConnectorConf(Client props) {
    return new ClientConnectorConfiguration()
        .setPort(props.getPort());
  }

  @Bean
  public IgniteProps igniteProps() {
    return new IgniteProps();
  }

  public static DiscoverySpi discoverySpi(Discovery props) {
    TcpDiscoverySpi spi = new TcpDiscoverySpi();
    Optional.ofNullable(props)
        .filter(x -> Type.K8S == x.getType())
        .ifPresent(x -> {
          TcpDiscoveryKubernetesIpFinder f = new TcpDiscoveryKubernetesIpFinder();
          f.setServiceName(x.getServiceName());
          f.setNamespace(x.getNamespace());
          f.setMasterUrl(x.getMasterUrl());
          f.setAccountToken(x.getAccountToken());
          spi.setIpFinder(f);
        });
    return spi;
  }

  public static DataStorageConfiguration dataStorageConf(Persistence persistence) {
    DataStorageConfiguration conf = new DataStorageConfiguration();
    conf.getDefaultDataRegionConfiguration()
        .setPersistenceEnabled(true);
    conf.setStoragePath(persistence.getStoragePath());
    conf.setSystemRegionInitialSize(persistence.getRegionInitSize());
    return conf;
  }
}