package com.ebs.piggyinvest;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ebs.piggyinvest.PiggySource.PiggyAsset;
import com.ebs.piggyinvest.PiggySource.PiggyGroup;

public class Utils 
{
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static JSONArray groupsToJSONArray(ArrayList<PiggyGroup> pGroups)
	{
		JSONArray jPGroups = new JSONArray();
		
		for(int i = 0; i < pGroups.size(); i++)
		{
			JSONObject group = new JSONObject();
			group.put("id", pGroups.get(i).getId());
			group.put("name", pGroups.get(i).getName());
			group.put("startvalue", pGroups.get(i).getStartvalue());
			group.put("currentvalue", pGroups.get(i).getCurrentvalue());
			
			JSONArray jAssets = new JSONArray();
			ArrayList<PiggyAsset> pAssets = pGroups.get(i).getAssets();
			for(int j = 0; j < pAssets.size(); j++)
			{
				PiggyAsset pAsset = pAssets.get(j);
				JSONObject jAsset = new JSONObject();
				jAsset.put("name", pAsset.getName());
				jAsset.put("propId", pAsset.getPropId());
				jAsset.put("amount", pAsset.getAmount());
				jAsset.put("tradeValue", pAsset.getTotalTradeValue());
				jAsset.put("currentValue", pAsset.getTotalCurrentValue());
				jAssets.put(jAsset);
			}
			group.put("assets", jAssets);
			
			JSONArray jMembers = new JSONArray();
			ArrayList<String> pMembers = new ArrayList<String>();
			for(int j = 0; j < pMembers.size(); j++)
			{
				jMembers.put(pMembers.get(j));
			}
			group.put("member", jMembers);
			jPGroups.put(group);
		}
		return jPGroups;
	}
	
	public static ArrayList<PiggyGroup> jsonArrayToGroup(JSONArray jPGroups) throws IOException
	{
		ArrayList<PiggyGroup> pGroups = new ArrayList<PiggyGroup>();
		
		for(int i = 0; i < jPGroups.length(); i++)
		{
			JSONObject jGroup = jPGroups.getJSONObject(i);
			int id = jGroup.getInt("id");
			String name = jGroup.getString("name");
			int startvalue = jGroup.getInt("startvalue");
			int currentvalue = jGroup.getInt("currentvalue");
			
			
			
			JSONArray jAssets = jGroup.getJSONArray("assets");
			
			ArrayList<PiggyAsset> pAssets = new ArrayList<PiggyAsset>();
			/*for(int j = 0; j < jAssets.length(); j++)
			{
				JSONObject jAsset = jAssets.getJSONObject(j);
				
				String assetName = jAsset.getString("name");
				int propId = jAsset.getInt("propId");
				int amount = jAsset.getInt("amount");
				double tradeValue = jAsset.getDouble("tradeValue");
				double currentValue = jAsset.getDouble("currentValue");
				pAssets.add(new PiggyAsset(assetName, propId, amount, tradeValue, currentValue));
			}*/
			/*pAssets.add(new PiggyAsset(Stocks.stockNames[3], 1, 5, 30.0, 31.2));
			pAssets.add(new PiggyAsset(Stocks.stockNames[4], 2, 10, 350.0, 315.3));
			pAssets.add(new PiggyAsset(Stocks.stockNames[6], 3, 8, 302.0, 312.2));
			pAssets.add(new PiggyAsset(Stocks.stockNames[1], 4, 13, 70.0, 81.5));
			pAssets.add(new PiggyAsset(Stocks.stockNames[7], 5, 2, 60.0, 49.9));
			pAssets.add(new PiggyAsset(Stocks.stockNames[17], 6, 1, 10.0, 11.8));*/
			
			JSONArray jMembers = jGroup.getJSONArray("member"); 
			ArrayList<String> members = new ArrayList<String>();
			for(int j = 0; j < jMembers.length(); j++)
			{
				members.add(jMembers.getString(j));
			}
			
			PiggyGroup pGroup = new PiggyGroup(id, name, startvalue, currentvalue, pAssets, members);
			
			
			pGroups.add(pGroup);
		}
		return pGroups;
	}
	

}
