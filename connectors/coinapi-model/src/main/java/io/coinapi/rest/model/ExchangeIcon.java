package io.coinapi.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ExchangeIcon {

  private String exchange_id;
  private String url;
}