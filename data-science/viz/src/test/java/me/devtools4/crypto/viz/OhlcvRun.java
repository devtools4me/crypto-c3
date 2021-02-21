package me.devtools4.crypto.viz;

import com.dslplatform.json.DslJson;
import io.coinapi.websocket.model.OHLCV;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.coinapi.Ops;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import me.devtools4.csv.CsvOps;

@Slf4j
public class OhlcvRun {

  public static void main(String[] args) throws IOException {
    var dsl = new DslJson<>();
    try (var is = OhlcvRun.class.getClassLoader().getResourceAsStream("ohlcv-example.json");
        var isr = new InputStreamReader(is);
        var reader = new BufferedReader(isr))
    {
      var events = reader.lines()
          .map(s -> Ops.fromJson(dsl, s, OHLCV.class))
          .map(Ops::event)
          .collect(Collectors.toList());
      var sw = new StringWriter();
      CsvOps.csv(events, OhlcvEvent.class, new String[] {}, sw);
      var out = sw.toString();
      log.info("{}", out);
    }
  }
}