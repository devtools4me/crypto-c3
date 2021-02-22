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
public class OhlcvRun {

  public static void main(String[] args) throws IOException {
    var dsl = new DslJson<>();
    try (var is = OhlcvRun.class.getClassLoader().getResourceAsStream("ohlcv-example.json");
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

//        Chart.create().asHtml().withLinePlot(df, "DataDate", chart -> {
//          chart.title().withText("Example Time Series Chart");
//          chart.subtitle().withText("Cumulative Sum of Random Normal Data");
//          chart.plot().axes().domain().label().withText("Data Date");
//          chart.plot().axes().range(0).label().withText("Random Value");
//          chart.plot().style("Total").withLineWidth(2f).withColor(Color.BLACK);
//          chart.legend().on();
//          chart.show();
//        });
      }
    }
  }
}