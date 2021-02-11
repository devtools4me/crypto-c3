package me.devtools4.crypto.cache.jmx;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.function.BinaryOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.cluster.ClusterNode;
import org.jetbrains.annotations.NotNull;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@Slf4j
@ManagedResource(objectName = CacheOps.OBJECT_NAME, description = CacheOps.DESCRIPTION)
public class ManagedCacheOps implements CacheOps {

  private final Ignite ignite;

  public ManagedCacheOps(Ignite ignite) {
    this.ignite = ignite;
  }

  @Override
  @ManagedOperation
  public String caches() {
    Collection<String> names = ignite.cacheNames();
    String res = names.stream().reduce("", reduce("\n"));
    log.info("caches={}", res);
    return res;
  }

  @Override
  @ManagedOperation
  public String topology() {
    IgniteCluster cluster = ignite.cluster();
    String res = cluster.topology(cluster.topologyVersion())
        .stream()
        .map(ManagedCacheOps::str)
        .reduce("", reduce("\n"));
    log.info("topology={}", res);
    return res;
  }

  @Override
  @ManagedOperation
  public String clear(String name) {
    if (ignite.cacheNames().contains(name)) {
      ignite.cache(name).clear();
      return name + " cache has been cleaned";
    } else {
      return "No cache with name=" + name;
    }
  }

  private static String str(ClusterNode x) {
    return ImmutableList.of(
        x.id(),
        x.version(),
        x.isLocal(),
        x.isClient(),
        x.isDaemon(),
        x.hostNames(),
        x.addresses())
        .stream()
        .map(String::valueOf)
        .reduce("", reduce(", "));
  }

  @NotNull
  private static BinaryOperator<String> reduce(String separator) {
    return (s1, s2) -> s1.isEmpty() ? s2 : s1 + separator + s2;
  }
}
