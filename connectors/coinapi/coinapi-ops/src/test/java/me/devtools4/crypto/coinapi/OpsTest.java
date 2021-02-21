package me.devtools4.crypto.coinapi;

import static org.junit.Assert.assertNotNull;

import com.dslplatform.json.DslJson;
import io.coinapi.websocket.model.OHLCV;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.crypto.dto.avro.OhlcvEvent;
import org.junit.Test;

@Slf4j
public class OpsTest {
  public static final String ohlcv = "{\"period_id\":\"1SEC\",\"time_period_start\":\"2021-02-20T08:15:32.0000000Z\",\"time_period_end\":\"2021-02-20T08:15:33.0000000Z\",\"time_open\":\"2021-02-20T08:15:32.0070680Z\",\"time_close\":\"2021-02-20T08:15:32.8258340Z\",\"price_open\":55564.3,\"price_high\":55564.3,\"price_low\":55555.99,\"price_close\":55555.99,\"volume_traded\":0.03245147,\"trades_count\":2,\"symbol_id\":\"COINBASE_SPOT_BTC_USD\",\"sequence\":32822093,\"type\":\"ohlcv\"}\n";

  @Test
  public void testFromJson() {
    OHLCV ohlcv = Ops.fromJson(new DslJson(), OpsTest.ohlcv, OHLCV.class);
    OhlcvEvent event = Ops.event(ohlcv);
    log.info("event={}", event);
    assertNotNull(event);
  }
}