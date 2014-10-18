package com.ebs.piggyinvest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StockListAdapter extends ArrayAdapter<Item> {

private List<Item> items;

public StockListAdapter(Context context, int textViewResourceId) 
{
    super(context, textViewResourceId);
}

public StockListAdapter(Context context, int resource, List<Item> items) 
{
    super(context, resource, items);
    this.items = items;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {

    View v = convertView;

    if (v == null) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.listrow, null);

    }
    
    Item item = items.get(position);

    //PiggySource.PiggyAsset p = getItem(position);

    if (true) {

        TextView tvStockAmount = (TextView) v.findViewById(R.id.tvStockAmount);
        TextView tvStockName = (TextView) v.findViewById(R.id.tvStockName);
        TextView tvStockPrice = (TextView) v.findViewById(R.id.tvStockPrice);
        TextView tvStockPerc = (TextView) v.findViewById(R.id.tvStockPerc);
        //TextView tt3 = (TextView) v.findViewById(R.id.description);
        tvStockAmount.setText(" " + Integer.toString(item.amount));
        tvStockName.setText(" " + item.name);
        tvStockPrice.setText("$" + Double.toString(item.stockValue) + " ");
        
        double diff = item.amount * item.stockValue - item.amount * item.tradeValue;
        double perc = Utils.round(diff / item.amount * item.tradeValue / 100.0, 2);
        diff = Utils.round(diff, 2);
        tvStockPerc.setText("$" + Double.toString(diff) + " | " + Double.toString(perc) + "% ");
        
    }

    return v;

	}
}
