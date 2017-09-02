package proj.dummyexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import proj.dummyexample.Adapter.MarketListAdapter;
import proj.dummyexample.GetterSetter.MarketData;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MarketListActivity extends AppCompatActivity {
ArrayList<MarketData> arrayList=new ArrayList<>();
    MarketListAdapter marketListAdapter;
    ListView listView;
    StringBuilder stringbuilder=new StringBuilder();
    StringBuilder stringbuilder2=new StringBuilder();
    StringBuilder stringbuilder4=new StringBuilder();
    StringBuilder stringbuilder3=new StringBuilder();
    String url[]={"https://api.coinsecure.in","https://www.unocoin.com","https://www.zebapi.com","https://ethexindia.com/"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);

        listView= (ListView) findViewById(R.id.marketlist);




         //   callCoinsecure(url[0]);







    }
   /* private void callZebapi(final String s){
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getZebapi(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder2.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder2);
                    String buy=jsonobiect.getString("buy");
                    String sell=jsonobiect.getString("sell");


                    MarketData marketData=new MarketData("Zebapi",buy,sell,s);

                  arrayList.add(marketData);

                    getEthexIndia(url[3]);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    private void getEthexIndia(String s){
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getEthexIndia(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder4.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder4);
                    String buy=jsonobiect.getString("last_traded_price");
                    long last_traded_time=jsonobiect.getLong("last_traded_time_IST");
                    MarketData marketData=new MarketData("ETHEXIndia",buy,"-");
                    arrayList.add(marketData);
                    callUnocoin(url[1]);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void callCoinsecure(String s) {

        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getCoinsecureApi(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder);
                    JSONObject childobj=jsonobiect.getJSONObject("message");


                    String buy=childobj.getString("ask");
                    String sell=childobj.getString("bid");
                   MarketData marketData=new MarketData("CoinSecure",buy.substring(0, buy.length() - 2),sell.substring(0, sell.length() - 2));
                    arrayList.add(marketData);


                    callZebapi(url[2]);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    private void callUnocoin(String s){
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getUnocoinApi(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder3.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder3);
                    String buy=jsonobiect.getString("buy");
                    String sell=jsonobiect.getString("sell");
                   MarketData marketData=new MarketData("Unocoin",buy,sell);

                    arrayList.add(marketData);
                    marketListAdapter=new MarketListAdapter(arrayList,MarketListActivity.this);
                    listView.setAdapter(marketListAdapter);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }*/
}
