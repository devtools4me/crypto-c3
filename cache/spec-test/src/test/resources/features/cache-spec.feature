Feature: Crypto Cache API

  Scenario: Verify metrics
    Given Cache is available
    When User send metrics request
    Then System responded
