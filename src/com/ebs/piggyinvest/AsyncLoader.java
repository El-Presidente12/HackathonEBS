package com.ebs.piggyinvest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ebs.piggyinvest.PiggySource.PiggyGroup;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class AsyncLoader extends AsyncTask<String, Void, String> 
{
	private int userId;



	public AsyncLoader(int userId)
	{
		this.userId = userId;
	}
	
    protected String doInBackground(String... urls) 
    {
        try 
        {
        	ArrayList<PiggyGroup> pgs = PiggySource.getGroupById(userId);
        	return Utils.groupsToJSONArray(pgs).toString();
		} 
        catch (Exception e) 
		{
			// TODO Auto-generated catch block
        	String err = "";
			for(int i = 0; i < e.getStackTrace().length; i++)
			{
				err+= e.getStackTrace()[i].toString();
			}
			return "";
		}
    }



    protected void onPostExecute(String result) 
    {
        if(result.equals(""))
        {
        	return;
        }
        //Toast.makeText(MainActivity.that, "-finished-", Toast.LENGTH_LONG).show();
        SharedPreferences prefs = MainActivity.that.getSharedPreferences("PIGDATA", 0);
    	SharedPreferences.Editor edit = prefs.edit();
    	edit.putInt("UID", userId);
    	edit.putString("JSET", result);
    	edit.apply();
    }
}
