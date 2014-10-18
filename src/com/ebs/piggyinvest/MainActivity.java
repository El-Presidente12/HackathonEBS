package com.ebs.piggyinvest;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.ebs.piggyinvest.PiggySource.PiggyAsset;
import com.ebs.piggyinvest.PiggySource.PiggyGroup;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseInstallation;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseException;


public class MainActivity extends ActionBarActivity 
{
	public static MainActivity that;
	ArrayList<PiggyGroup> group;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        Parse.initialize(this, "Bz22LR8dxTgBhe7UwdMvsMug5wb9irjvbe520GCl", "qXyQnHKQPYp2RRJhiidgDkxMfGpFA9X2lCJ1NGe2");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        HashMap<String, Object> params = new HashMap<String, Object>();
        //params.put("movie", "The Matrix");
        ParseCloud.callFunctionInBackground("hello", params, new FunctionCallback<String>() {
           public void done(String ratings, ParseException e) {
               if (e == null) 
               {
                  Toast.makeText(MainActivity.this, ratings, Toast.LENGTH_LONG).show();
               }
           }
        });
        
        that = this;
 
       
        setMain(null);
        
    }
    
    public void searchClick(View v)
    {
    	ArrayList<Item> items = new ArrayList<Item>();
    	String keyword = ((EditText)findViewById(R.id.editText1)).getText().toString().toLowerCase();
    	
    	for(int i = 0; i < Stocks.stockNames.length; i++)
    	{
    		if(Stocks.stockNames[i].toLowerCase().contains(keyword) || Integer.toString(Stocks.stockISINs[i]).contains(keyword))
    		{
    			items.add(new Item(i, Stocks.stockNames[i], Stocks.stockISINs[i], Stocks.stocksValues[i], Stocks.amounts[i], Stocks.tradeValues[i]));
    		}
    	}
    	
		
		StockListAdapter adapter = new StockListAdapter(this, R.layout.listrow, items);
		((ListView)findViewById(R.id.listView1)).setAdapter(adapter);
    }
    
    public void clickMain(View v)
    {
    	setContentView(R.layout.activity_main);
    	setMain(null);
    }
    
    public void clickSell(View v)
    {
    	
    	try
    	{
    	
    	
		setContentView(R.layout.sell);
    	
		ArrayList<Item> items = new ArrayList<Item>();
		for(int i = 0; i < Stocks.amounts.length; i++)
		{
			items.add(new Item(i, Stocks.stockNames[i], Stocks.stockISINs[i], Stocks.stocksValues[i], Stocks.amounts[i], Stocks.tradeValues[i]));
		}
		
    	StockListAdapter adapter = new StockListAdapter(this, R.layout.listrow, items);
		((ListView)findViewById(R.id.listView1)).setAdapter(adapter);
		((ListView)findViewById(R.id.listView1)).setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) 
			{
				//Toast.makeText(MainActivity.this, "works", Toast.LENGTH_LONG).show();
				final ArrayList<Item> items = new ArrayList<Item>();
				for(int i = 0; i < Stocks.amounts.length; i++)
				{
					items.add(new Item(i, Stocks.stockNames[i], Stocks.stockISINs[i], Stocks.stocksValues[i], Stocks.amounts[i], Stocks.tradeValues[i]));
				}
				if(items.get(arg2).amount < 1)
				{
					return;
				}
				
				setContentView(R.layout.sell_2);
				
				findViewById(R.id.btSell).setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) 
					{
						if( Integer.parseInt(((EditText)findViewById(R.id.etSellAmount)).getText().toString()) >  items.get(arg2).amount)
						{
							Toast.makeText(MainActivity.this, "you can't sell more then you own", Toast.LENGTH_LONG).show();
						}
						else
						{
							Stocks.money += Integer.parseInt(((EditText)findViewById(R.id.etSellAmount)).getText().toString()) * items.get(arg2).stockValue;
							Stocks.amounts[items.get(arg2).id] -= Integer.parseInt(((EditText)findViewById(R.id.etSellAmount)).getText().toString());
							clickMain(null);
						}
						
					}});
				
				
			}});
    	}
    	catch(Exception e)
    	{
    		String err = "";
			for(int i = 0; i < e.getStackTrace().length; i++)
			{
				err+= e.getStackTrace()[i].toString();
			}
			Toast.makeText(this, err, Toast.LENGTH_LONG).show();
    	}
    }
    
    public void setMain(View v)
    {
    	try
    	{
    		EditText et = ((EditText)findViewById(R.id.editText1));
    		int uId;
    		if(et != null)
    		{
    			uId = Integer.parseInt(et.getText().toString());
    		}
    		else
    		{
    			uId = getSharedPreferences("PIGDATA", 0).getInt("UID", -1);
    		}
    		setContentView(R.layout.activity_main);
    	
    		SharedPreferences prefs = MainActivity.that.getSharedPreferences("PIGDATA", 0);
    		group = Utils.jsonArrayToGroup(new JSONArray(prefs.getString("JSET", "")));
    		
    		ArrayList<PiggyAsset> assets = group.get(0).getAssets();
    		int money = group.get(0).getCurrentvalue();
    		
    		for(int i = 0; i < assets.size(); i++)
    		{
    			money += assets.get(i).getTotalCurrentValue();
    		}
    		
    		((TextView)findViewById(R.id.tvGroupDetail)).setText("5 | $" + Integer.toString(money) );
    		
    		((TextView)findViewById(R.id.tvCurrShare)).setText("$" + Double.toString(Utils.round((double)money * 0.2, 2) ));
    		((TextView)findViewById(R.id.tvDayChange)).setText("$" + Double.toString(Utils.round((double)money * 0.2  - 1000, 2)) + " | " + Double.toString(Utils.round(((double)money * 0.2  - 1000) / 100.0, 2)) + "%");
    		
    		((TextView)findViewById(R.id.tvProfit)).setText("$" + Double.toString(Utils.round((double)money * 0.2  - 1000, 2)));
    		((TextView)findViewById(R.id.tvProfitPerc)).setText(Double.toString(Utils.round(((double)money * 0.2  - 1000) / 100.0, 2)) + "%");
    		
    		ArrayList<Item> items = new ArrayList<Item>();
    		for(int i = 0; i < Stocks.amounts.length; i++)
    		{
    			items.add(new Item(i, Stocks.stockNames[i], Stocks.stockISINs[i], Stocks.stocksValues[i], Stocks.amounts[i], Stocks.tradeValues[i]));
    		}
    		
    		StockListAdapter adapter = new StockListAdapter(this, R.layout.listrow, items);
    		((ListView)findViewById(R.id.listView1)).setAdapter(adapter);
    		//Toast.makeText(this, Integer.toString(getSharedPreferences("PIGDATA", 0).getInt("UID", -1)), Toast.LENGTH_LONG).show();
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    
    public void clickBuy(View v)
    {
    	setContentView(R.layout.search);
    	ArrayList<Item> items = new ArrayList<Item>();
		for(int i = 0; i < Stocks.amounts.length; i++)
		{
			items.add(new Item(i, Stocks.stockNames[i], Stocks.stockISINs[i], Stocks.stocksValues[i], Stocks.amounts[i], Stocks.tradeValues[i]));
		}
		
		StockListAdapter adapter = new StockListAdapter(this, R.layout.listrow, items);
		((ListView)findViewById(R.id.listView1)).setAdapter(adapter);
    	((ListView)findViewById(R.id.listView1)).setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) 
			{
				//Toast.makeText(MainActivity.this, "works", Toast.LENGTH_LONG).show();
				final ArrayList<Item> items = new ArrayList<Item>();
				for(int i = 0; i < Stocks.amounts.length; i++)
				{
					items.add(new Item(i, Stocks.stockNames[i], Stocks.stockISINs[i], Stocks.stocksValues[i], Stocks.amounts[i], Stocks.tradeValues[i]));
				}
				
				
				
				setContentView(R.layout.buy);
				
				findViewById(R.id.btBuy).setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) 
					{
						if( Integer.parseInt(((EditText)findViewById(R.id.etBuyAmount)).getText().toString()) * items.get(arg2).stockValue >  Stocks.money)
						{
							Toast.makeText(MainActivity.this, "you can't by for more money than you have", Toast.LENGTH_LONG).show();
						}
						else
						{
							Stocks.money -= Integer.parseInt(((EditText)findViewById(R.id.etBuyAmount)).getText().toString()) * items.get(arg2).stockValue;
							Stocks.amounts[items.get(arg2).id] += Integer.parseInt(((EditText)findViewById(R.id.etBuyAmount)).getText().toString());
							Stocks.stocksValues[items.get(arg2).id] = items.get(arg2).stockValue;
							clickMain(null);
						}
						
					}});
				
			}});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
