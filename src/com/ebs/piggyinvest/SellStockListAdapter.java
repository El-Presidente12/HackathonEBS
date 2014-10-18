package com.ebs.piggyinvest;

import java.util.ArrayList;
import java.util.List;

import com.ebs.piggyinvest.PiggySource.PiggyAsset;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SellStockListAdapter extends ArrayAdapter<PiggySource.PiggyAsset> 
{
public SellStockListAdapter(Context context, int textViewResourceId) 
{
    super(context, textViewResourceId);
}

public SellStockListAdapter(Context context, int resource, int[] items) {
    super(context, resource);
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {

    View v = convertView;

    if (v == null) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.listrow, null);

    }

    //PiggySource.PiggyAsset p = getItem(position);

    if (true) {

        TextView tvStockAmount = (TextView) v.findViewById(R.id.tvStockAmount);
        TextView tvStockName = (TextView) v.findViewById(R.id.tvStockName);
        TextView tvStockPrice = (TextView) v.findViewById(R.id.tvStockPrice);
        TextView tvStockPerc = (TextView) v.findViewById(R.id.tvStockPerc);
        //TextView tt3 = (TextView) v.findViewById(R.id.description);
        tvStockAmount.setText(" " + Integer.toString(Stocks.amounts[position]));
        tvStockName.setText(" " + Stocks.stockNames[position]);
        tvStockPrice.setText("$" + Double.toString(Stocks.stocksValues[position] * Stocks.amounts[position]) + " ");
        
        double diff = Stocks.stocksValues[position] - Stocks.tradeValues[position];
        double perc = Utils.round(diff / Stocks.stocksValues[position] * Stocks.amounts[position] / 100.0, 2);
        diff = Utils.round(diff, 2);
        tvStockPerc.setText("$" + Double.toString(diff) + " | " + Double.toString(perc) + "% ");
        /*final int i = position;
         v.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				
				MainActivity.that.group.get(0).getAssets().get(i).setAmount(0);
				
				SharedPreferences prefs = MainActivity.that.getSharedPreferences("PIGDATA", 0);
		    	SharedPreferences.Editor edit = prefs.edit();
		    	edit.putString("JSET", Utils.groupsToJSONArray(MainActivity.that.group).toString());
		    	edit.apply();
			}});*/
        
    }

    return v;

	}
}
