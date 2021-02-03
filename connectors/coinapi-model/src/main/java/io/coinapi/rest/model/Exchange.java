package io.coinapi.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Exchange extends DataVolume {

  private String exchange_id;
  private String website;
  private String name;
}