package proj.dummyexample;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by Administrator on 17-08-2017.
 */

public interface MyInterface {

    @GET("/v1/exchange/ticker")
    void getCoinsecureApi(Callback<Response> responseCallback);

    @GET("/trade?all")
    void getUnocoinApi(Callback<Response> responseCallback);


    @GET("/api/v1/market/ticker/btc/inr")
    void getZebapi(Callback<Response> responseCallback);

    @GET("/api/ticker")
    void getEthexIndia(Callback<Response> responseCallback);


    @GET("/v2/tickers?symbols=tBTCUSD,tLTCUSD,tXRPUSD,tBCHUSD,tETHUSD")
    void getBitfineIndia(Callback<Response> responseCallback);
}
