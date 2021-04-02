package me.devtools4.crypto.cache.client;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

@Slf4j
public class IgniteClientFactoryRun {
  public static void main(String[] args) {
    var ignite = IgniteClientFactory.ignite(new TcpDiscoverySpi()
        .setIpFinder(new TcpDiscoveryMulticastIpFinder()
            .setAddresses(Collections.singletonList("localhost"))));
    ignite.cacheNames().forEach(log::info);
  }
}