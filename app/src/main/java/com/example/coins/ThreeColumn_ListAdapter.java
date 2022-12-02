package com.example.coins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<Coin> {
    private LayoutInflater mInflater;
    private ArrayList<Coin> coins;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter (Context context, int textViewResourceId, ArrayList<Coin> coins) {
        super(context, textViewResourceId, coins);
        this.coins = coins;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView (int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Coin coin = coins.get(position);

        if(coin != null){
            TextView name = (TextView) convertView.findViewById(R.id.coin);
            TextView price = (TextView) convertView.findViewById(R.id.price);
            TextView marketCap = (TextView) convertView.findViewById(R.id.marketCap);

            if (name != null){
                name.setText(coin.getSymbol());
            }
            if (price != null){
                price.setText(coin.getPrice());
            }
            if (marketCap != null){
                marketCap.setText(coin.getMarketCap());
            }
        }
        return convertView;
    }
}
