package me.devtools4.csv;

import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CsvOpsTest {

  @Value
  @Builder
  public static class Example {

    private String symbolId;
    private Integer sequence;
    private OffsetDateTime timePeriodStart;
    private Double priceOpen;
  }

  @Test
  public void testCsv() {
    var sw = new StringWriter();
    CsvOps.csv(List.of(Example.builder()
        .symbolId("symbolId")
        .sequence(12345)
        .timePeriodStart(Instant.now().atOffset(ZoneOffset.UTC))
        .priceOpen(123.123)
        .build()), sw);
    String s = sw.toString();
    assertNotNull(s);
    log.info("\n{}", s);
  }
}