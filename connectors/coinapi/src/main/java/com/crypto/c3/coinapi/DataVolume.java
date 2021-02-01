package com.crypto.c3.coinapi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataVolume extends CryptoData {

  private Double volume_1hrs;
  private Double volume_1hrs_usd;
  private Double volume_1day;
  private Double volume_1day_usd;
  private Double volume_1mth;
  private Double volume_1mth_usd;
}