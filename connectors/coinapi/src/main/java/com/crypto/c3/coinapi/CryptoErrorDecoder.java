package com.crypto.c3.coinapi;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

public class CryptoErrorDecoder implements ErrorDecoder {

  final Decoder decoder;
  final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

  public CryptoErrorDecoder(Decoder decoder) {
    this.decoder = decoder;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    CryptoError error = new CryptoError();
    error.setMessage(defaultDecoder.decode(methodKey, response).getMessage());
    return error;
  }
}