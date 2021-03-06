Feature: Crypto Cache API

  Scenario: Verify metrics
    Given Cache is available
    When User send metrics request
    Then System responded

  Scenario: Verify kafka
    Given Cache is available
    When OHLCV event was populated with details
    | symbolId              |
    | BITSTAMP_SPOT_BTC_USD |
    Then OHLCV event is available in 5 sec with details
    | symbolId              |
    | BITSTAMP_SPOT_BTC_USD |