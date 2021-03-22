Feature: Crypto Cache API

  Scenario: Verify metrics
    Given Cache is available
    When User send metrics request
    Then System responded

  Scenario: Verify kafka
    Given Cache is available
    When OHLCV event was populated with details
      | symbolId              | periodId | fileName         |
      | BITSTAMP_SPOT_BTC_USD | 1MIN     | ohlcv-event.json |
    Then OHLCV event is available in KAFKA in 5 sec with details
      | symbolId              | periodId |
      | BITSTAMP_SPOT_BTC_USD | 1MIN     |
#    Then OHLCV event is available in cache in 5 sec with details
#      | symbolId              | periodId |
#      | BITSTAMP_SPOT_BTC_USD | 1MIN     |