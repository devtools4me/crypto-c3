package me.devtools4.crypto.viz;

import com.d3x.morpheus.frame.DataFrame;
import com.d3x.morpheus.viz.chart.Chart;
import com.dslplatform.json.DslJson;
import io.coinapi.rest.model.Ohlcv;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.coinapi.Ops;
import me.devtools4.csv.CsvOps;

@Slf4j
public class OhlcvRestRun {

  public static void main(String[] args) throws IOException {
    var dsl = new DslJson<>();
    try (var is = OhlcvRestRun.class.getClassLoader()
        .getResourceAsStream("ohlcv-rest-example.json");
        var isr = new InputStreamReader(is);
        var reader = new BufferedReader(isr)) {
      var events = reader.lines()
          .map(s -> Ops.fromJson(dsl, s, Ohlcv.class))
          .map(o -> Ops.event("BITSTAMP_SPOT_BTC_USD", "1MIN", o))
          .collect(Collectors.toList());
      var out = CsvOps.csv(events);
      log.info("\n{}", out);

      try (var in = new ByteArrayInputStream(out.getBytes())) {
        var df = DataFrame.read(in).csv(LocalDateTime.class, options -> {
          options.setRowKeyColumnName("timeClose");
        });
        df.out().print();

        var close = df.cols()
            .select(column -> column.key().equalsIgnoreCase("priceClose"));
        Chart.create().asHtml().withLinePlot(close, chart -> {
          chart.title().withText("BITSTAMP_SPOT_BTC_USD");
          chart.plot().axes().domain().label().withText("Date");
          chart.plot().axes().range(0).label().withText("Price");
          chart.show();
        });
      }
    }
  }
}