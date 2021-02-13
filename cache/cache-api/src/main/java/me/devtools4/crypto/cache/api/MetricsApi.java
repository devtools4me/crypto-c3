package me.devtools4.crypto.cache.api;

import feign.RequestLine;

public interface MetricsApi {

  @RequestLine("GET /actuator/prometheus")
  String metrics();
}