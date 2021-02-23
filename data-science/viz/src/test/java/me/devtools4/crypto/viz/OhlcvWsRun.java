package me.devtools4.crypto.viz;

import com.d3x.morpheus.frame.DataFrame;
import com.dslplatform.json.DslJson;
import io.coinapi.websocket.model.OHLCV;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.coinapi.Ops;
import me.devtools4.csv.CsvOps;

@Slf4j
public class OhlcvWsRun {

  public static void main(String[] args) throws IOException {
    var dsl = new DslJson<>();
    try (var is = OhlcvWsRun.class.getClassLoader().getResourceAsStream("ohlcv-ws-example.json");
        var isr = new InputStreamReader(is);
        var reader = new BufferedReader(isr)) {
      var events = reader.lines()
          .map(s -> Ops.fromJson(dsl, s, OHLCV.class))
          .map(Ops::event)
          .collect(Collectors.toList());
      var out = CsvOps.csv(events);
      log.info("\n{}", out);

      try (var in = new ByteArrayInputStream(out.getBytes())) {
        var df = DataFrame.read(in).csv();
        df.out().print();
      }
    }
  }
}