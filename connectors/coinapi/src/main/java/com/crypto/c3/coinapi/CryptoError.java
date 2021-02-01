package com.crypto.c3.coinapi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CryptoError extends RuntimeException {

  private String message;
}