package com.coingecko.api.v3.coins;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@CompiledJson
public class Coin {

  private String id;
  private String symbol;
  private String name;
}