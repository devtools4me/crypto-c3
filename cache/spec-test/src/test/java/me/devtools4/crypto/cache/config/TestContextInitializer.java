package me.devtools4.crypto.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.SocketUtils;

@Slf4j
public class TestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext ctx) {
    String pairs = "ignite.client.port=" + SocketUtils.findAvailableTcpPort();
    log.info("{}", pairs);

    TestPropertyValues.of(pairs)
        .applyTo(ctx.getEnvironment());
  }
}