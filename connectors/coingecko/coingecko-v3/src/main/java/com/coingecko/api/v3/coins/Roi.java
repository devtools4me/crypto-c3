package com.coingecko.api.v3.coins;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@CompiledJson
public class Roi {

  private Double times;
  private String currency;
  private Double percentage;
}