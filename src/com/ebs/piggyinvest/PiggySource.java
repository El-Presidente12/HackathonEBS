/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebs.piggyinvest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 *
 * @author Steffen
 */
public class PiggySource 
{
    public static ArrayList<PiggyGroup> getGroupById(int userId) throws IOException
    {
        ArrayList<PiggyGroup> groupies = new ArrayList<PiggyGroup>();
        URL url = new URL("http://109.73.51.159/kws/getGroups.php?userid="+Integer.toString(userId));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        //Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String name;
        while((name = rd.readLine()) != null)
        {
            int id = Integer.parseInt(rd.readLine());
            int startValue = Integer.parseInt(rd.readLine());
            int currentValue = Integer.parseInt(rd.readLine());
            groupies.add(new PiggyGroup(id, name, startValue, currentValue));
        }
        rd.close();
        return groupies;
    }

    public static ArrayList<PiggyAsset> getPropsByGroupId(int groupId) throws IOException
    {
        ArrayList<PiggyAsset> assets = new ArrayList<PiggyAsset>();
        URL url = new URL("http://109.73.51.159/kws/getGroupProps.php?groupid="+Integer.toString(groupId));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        //Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String name;
        while((name = rd.readLine()) != null)
        {
            int id = Integer.parseInt(rd.readLine());
            int amount = Integer.parseInt(rd.readLine());
            double tradevalue = Double.parseDouble(rd.readLine());
            double value = Double.parseDouble(rd.readLine());
            assets.add(new PiggyAsset(name, id, amount, tradevalue, value));
        }
        rd.close();
        return assets;
    }

    public static ArrayList<String> getGroupMembersByGroupId(int groupId) throws IOException
    {
        ArrayList<String> members = new ArrayList<String>();
        URL url = new URL("http://109.73.51.159/kws/getGroupMembers.php?groupid="+Integer.toString(groupId));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        //Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String name;
        while((name = rd.readLine()) != null)
        {
            members.add(name);
        }
        rd.close();
        return members;
    }

    public static ArrayList<PiggyRequest> getRequestsByGroup(int groupId) throws IOException
    {
        ArrayList<PiggyRequest> requests = new ArrayList<PiggyRequest>();
        URL url = new URL("http://109.73.51.159/kws/getRequests.php?groupid="+Integer.toString(groupId));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        //Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String reqId;
        while((reqId = rd.readLine()) != null)
        {
            int uId = Integer.parseInt(rd.readLine());
            int gId = Integer.parseInt(rd.readLine());
            boolean sell = Integer.parseInt(rd.readLine()) != 0;
            int amount = Integer.parseInt(rd.readLine());
            requests.add(new PiggyRequest(Integer.parseInt(reqId), uId, gId, sell, amount));
        }
        rd.close();
        return requests;
    }

    public static void insertPiggyVote(boolean accept, int requestId, int userId, String comment) throws IOException
    {
        String a = "0";
        if(accept)
        {
            a = "1";
        }
        //System.out.println("http://109.73.51.159/kws/insertVote.php?reqid="+Integer.toString(requestId) + "&userid=" + Integer.toString(userId) + "&comment=" + URLEncoder.encode(comment) + "&accept=" + a);
        URL url = new URL("http://109.73.51.159/kws/insertVote.php?reqid="+Integer.toString(requestId) + "&userid=" + Integer.toString(userId) + "&comment=" + URLEncoder.encode(comment) + "&accept=" + a);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setDoInput(true);
        connection.setDoOutput(false);
        //Get Response
        InputStream is = connection.getInputStream();
    }

    public static void insertPiggyRequest(boolean sell, int userId, int groupId, String comment, int amount, int stockId) throws IOException
    {
        String s = "0";
        if(sell)
        {
            s = "1";
        }
        System.out.println("http://109.73.51.159/kws/insertRequest.php?userid="+Integer.toString(userId) + "&groupid=" + Integer.toString(groupId) + "&comment=" + URLEncoder.encode(comment) + "&sell=" + s + "&amount=" + Integer.toString(amount) + "&sid=" + Integer.toString(stockId));
        URL url = new URL("http://109.73.51.159/kws/insertRequest.php?userid="+Integer.toString(userId) + "&groupid=" + Integer.toString(groupId) + "&comment=" + URLEncoder.encode(comment) + "&sell=" + s + "&amount=" + Integer.toString(amount) + "&sid=" + Integer.toString(stockId));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    
        connection.setDoInput(true);
        connection.setDoOutput(false);
        //Get Response
        InputStream is = connection.getInputStream();
    }

    public static ArrayList<PiggyVote> getVotesByRequest(int requestId) throws IOException
    {
        ArrayList<PiggyVote> votes = new ArrayList<PiggyVote>();
        URL url = new URL("http://109.73.51.159/kws/getVotes.php?reqid="+Integer.toString(requestId));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        //Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String comment;
        while((comment = rd.readLine()) != null)
        {
            int uId = Integer.parseInt(rd.readLine());
            int rId = Integer.parseInt(rd.readLine());
            boolean accept = Integer.parseInt(rd.readLine()) != 0;
            votes.add(new PiggyVote(uId, rId, accept, comment));
        }
        rd.close();
        return votes;
    }

    public static class PiggyVote
    {
        private int userId;
        private int requestId;
        private boolean accept;
        private String comment;

        public PiggyVote(int userId, int requestId, boolean accept, String comment)
        {
            this.userId = userId;
            this.requestId = requestId;
            this.accept = accept;
            this.comment = comment;
        }

        public boolean isAccept()
        {
            return accept;
        }

        public String getComment()
        {
            return comment;
        }

        public int getRequestId()
        {
            return requestId;
        }

        public int getUserId()
        {
            return userId;
        }


    }

    public static class PiggyRequest
    {
        private int requestId;
        private int userId;
        private int groupId;
        private boolean sell;
        private int amount;

        public PiggyRequest(int requestId, int userId, int groupId, boolean sell, int amount)
        {
            this.requestId = requestId;
            this.userId = userId;
            this.groupId = groupId;
            this.sell = sell;
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public int getGroupId() {
            return groupId;
        }

        public int getRequestId() {
            return requestId;
        }

        public boolean isSell() {
            return sell;
        }

        public int getUserId() {
            return userId;
        }


    }

    public static class PiggyAsset
    {
        private String name;
        private int propId;
        private int amount;
        private double tradeValue;
        private double currentValue;

        public PiggyAsset(String name, int propId, int amount, double tradeValue, double currentValue)
        {
            this.name = name;
            this.propId = propId;
            this.amount = amount;
            this.tradeValue = tradeValue;
            this.currentValue = currentValue;
        }

        public int getAmount()
        {
            return amount;
        }
        public void setAmount(int amount)
        {
        	this.amount = amount;
        }

        public double getCurrentValue()
        {
            return currentValue;
        }

        public String getName()
        {
            return name;
        }

        public int getPropId()
        {
            return propId;
        }

        public double getTradeValue()
        {
            return tradeValue;
        }

        public double getTotalTradeValue()
        {
            return tradeValue * (double)amount;
        }

        public double getTotalCurrentValue()
        {
            return currentValue * (double)amount;
        }

    }

    public static class PiggyGroup
    {
        private int id;
        private String name;
        private int startvalue;
        private int currentvalue;
        private ArrayList<String> members = new ArrayList<String>();
        private ArrayList<PiggyAsset> assets = new ArrayList<PiggyAsset>();

        public PiggyGroup(int id, String name, int startvalue, int currentvalue) throws IOException
        {
            this.id = id;
            this.name = name;
            this.startvalue = startvalue;
            this.currentvalue = currentvalue;
            this.members = getGroupMembersByGroupId(this.id);
            this.assets = getPropsByGroupId(id);
        }
        
        public PiggyGroup(int id, String name, int startvalue, int currentvalue, ArrayList<PiggyAsset> assets, ArrayList<String> members) throws IOException
        {
            this.id = id;
            this.name = name;
            this.startvalue = startvalue;
            this.currentvalue = currentvalue;
            this.members = members;
            this.assets = assets;
        }

        public int getCurrentvalue()
        {
            return currentvalue;
        }

        public int getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }

        public int getStartvalue()
        {
            return startvalue;
        }

        public ArrayList<String> getMembers()
        {
            return this.members;
        }
        
        public ArrayList<PiggyAsset> getAssets()
        {
            return this.assets;
        }
        
        public void setAssets(ArrayList<PiggyAsset> assets)
        {
        	this.assets = assets;
        }
    }
}
