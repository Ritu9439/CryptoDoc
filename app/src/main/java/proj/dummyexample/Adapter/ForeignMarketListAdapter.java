package proj.dummyexample.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import proj.dummyexample.GetterSetter.MarketData;
import proj.dummyexample.R;

/**
 * Created by sai on 29/8/17.
 */

public class ForeignMarketListAdapter extends BaseAdapter {

    ArrayList<MarketData> marketDatas;
    Context context;
    int color[]={Color.GRAY,Color.DKGRAY};

    public ForeignMarketListAdapter(ArrayList<MarketData> marketDatas, Context context) {
        this.marketDatas = marketDatas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return marketDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return marketDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        view=layoutInflater.inflate(R.layout.itemlist,null);


        if (i == 0) {
            view.setBackgroundColor(Color.DKGRAY);
        }
        else if (i % 2 == 1) {
            view.setBackgroundColor(Color.GRAY);
        }
        else if (i % 2 == 0) {
            view.setBackgroundColor(Color.DKGRAY);
        }
        TextView tvmarket,tvbuy,tvsell;
        ImageView img;
        img=view.findViewById(R.id.img);
        tvmarket=view.findViewById(R.id.tvmarket);
        tvbuy=view.findViewById(R.id.tvbuy);
        tvsell=view.findViewById(R.id.tvsell);
        MarketData marketData=marketDatas.get(i);


        String sell=marketData.getSell();
        if(sell.indexOf("-")>=0){
            tvsell.setTextColor(Color.RED);
            img.setVisibility(View.VISIBLE);
        }

       else {
            tvsell.setTextColor(Color.GREEN);
            img.setImageResource(R.mipmap.ic_up);
            img.setVisibility(View.VISIBLE);
        }
        tvmarket.setText(marketData.getMarket().trim());
        tvbuy.setText(marketData.getBuy().trim());
        tvsell.setText(marketData.getSell().trim());




        return view;
    }
}
