package me.devtools4.crypto.cache.jmx;

public interface CacheOps {

  String OBJECT_NAME = "me.devtools4.crypto.cache.jmx:name=ManagedCacheOps";
  String DESCRIPTION = "Operations";

  String caches();

  String topology();

  String clear(String name);
}
