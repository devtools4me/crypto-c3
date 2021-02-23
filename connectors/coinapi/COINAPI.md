# CoinAPI

```
curl https://rest.coinapi.io/v1/exchanges \
  --request GET \
  --header "X-CoinAPI-Key: 76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D"

curl https://rest.coinapi.io/v1/exchanges/BINANCE \
  --request GET \
  --header "X-CoinAPI-Key: 76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D"
  
curl https://rest.coinapi.io/v1/assets \
  --request GET \
  --header "X-CoinAPI-Key: 76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D"

curl https://rest.coinapi.io/v1/ohlcv/periods \
  --request GET \
  --header "X-CoinAPI-Key: 76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D"
 
curl https://rest.coinapi.io/v1/ohlcv/BITSTAMP_SPOT_BTC_USD/latest?period_id=1MIN \
  --request GET \
  --header "X-CoinAPI-Key: 76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D"

curl https://rest.coinapi.io/v1/ohlcv/BTC/USD/history?period_id=1MIN&time_start=2021-02-23T00:00:00 \
  --request GET \
  --header "X-CoinAPI-Key: 76E7E6A1-AED9-4AC0-BFC1-E1C53B45666D"
```

## Build

```
mvn clean install
```

## Run

```
me.devtools4.crypto.coinapi.CryptoCoinapiApp
-Dspring.config.location=file:/app/conf/application.yml
-Dlogging.config=file:/app/conf/logback.xml
```
