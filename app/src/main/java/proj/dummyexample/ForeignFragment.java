package proj.dummyexample;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import proj.dummyexample.Adapter.ForeignMarketListAdapter;
import proj.dummyexample.Adapter.MarketListAdapter;
import proj.dummyexample.GetterSetter.MarketData;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForeignFragment extends Fragment {
    ProgressBar progress;
    SwipeRefreshLayout swiperefresh;

    ArrayList<MarketData> arrayList=new ArrayList<>();
    ForeignMarketListAdapter marketListAdapter;
    ListView listView;
    StringBuilder stringbuilder=new StringBuilder();
    StringBuilder stringbuilder2=new StringBuilder();
    StringBuilder stringbuilder4=new StringBuilder();
    StringBuilder stringbuilder3=new StringBuilder();
    String url[]={"https://api.bitfinex.com/"};
    LinearLayout l1,l2;

    public ForeignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_foreign, container, false);
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
        getBitfinexIndia(url[0]);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                getBitfinexIndia(url[0]);
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


    private void getBitfinexIndia(final String s){
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getBitfineIndia(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder3.append(output);
                    }
                    Toast.makeText(getActivity(), ""+stringbuilder3, Toast.LENGTH_SHORT).show();


                    Log.d("ssss",""+stringbuilder3);
                    JSONArray jsonArray=new JSONArray(""+stringbuilder3);
                    for (int i=0;i<jsonArray.length();i++) {

                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                       // JSONArray jsonArrayy=jsonArray.getJSONArray(1);



                       MarketData marketData = new MarketData(jsonArray1.getString(0).substring(1), jsonArray1.getString(1), jsonArray1.getString(5), s);

                        arrayList.add(marketData);
                    }
                   swiperefresh.setRefreshing(false);
                    progress.setVisibility(View.GONE);
                    marketListAdapter=new ForeignMarketListAdapter(arrayList,getActivity());
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
