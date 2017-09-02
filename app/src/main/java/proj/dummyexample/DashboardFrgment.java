package proj.dummyexample;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import proj.dummyexample.Adapter.MarketListAdapter;
import proj.dummyexample.GetterSetter.MarketData;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static proj.dummyexample.R.id.sell;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFrgment extends Fragment {

    ProgressBar progress;
    SwipeRefreshLayout swiperefresh;

    ArrayList<MarketData> arrayList=new ArrayList<>();
    MarketListAdapter marketListAdapter;
    ListView listView;
    StringBuilder stringbuilder=new StringBuilder();
    StringBuilder stringbuilder2=new StringBuilder();
    StringBuilder stringbuilder4=new StringBuilder();
    StringBuilder stringbuilder3=new StringBuilder();
    String url[]={"https://api.coinsecure.in","https://www.unocoin.com","https://www.zebapi.com","https://ethexindia.com/"};
    LinearLayout l1,l2;
    public DashboardFrgment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_dashboard_frgment, container, false);
        listView= (ListView) v.findViewById(R.id.marketlist);
        swiperefresh= (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        progress= (ProgressBar) v.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);



listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        MarketData marketData=arrayList.get(i);
        Toast.makeText(getActivity(), ""+marketData.getMarket(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(),DetailedActivity.class);
        intent.putExtra("url",marketData.getUrl());
        intent.putExtra("market",marketData.getMarket());
        startActivity(intent);
    }
});
        callCoinsecure(url[0]);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                callCoinsecure(url[0]);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void callZebapi(final String s){
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
    private void getEthexIndia(final String s){
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
                    MarketData marketData=new MarketData("ETHEXIndia",buy,"-",s);
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


    private void callCoinsecure(final String s) {

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
                    MarketData marketData=new MarketData("CoinSecure",buy.substring(0, buy.length() - 2),sell.substring(0, sell.length() - 2),s);
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
    private void callUnocoin(final String s){
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
                    MarketData marketData=new MarketData("Unocoin",buy,sell,s);

                    arrayList.add(marketData);
                    swiperefresh.setRefreshing(false);
                    progress.setVisibility(View.GONE);
                    marketListAdapter=new MarketListAdapter(arrayList,getActivity());
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
    }
}
