package com.crypto.c3.coinapi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class AssetIcon {

  private String asset_id;
  private String url;
}