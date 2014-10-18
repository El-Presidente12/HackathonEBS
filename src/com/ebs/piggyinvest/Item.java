package com.ebs.piggyinvest;

public class Item 
{
	String name;
	int inis;
	double stockValue;
	int amount;
	double tradeValue;
	int id;
	
	public Item(int id, String name, int inis, double stockValue, int amount, double tradeValue)
	{
		this.id = id;
		this.name = name;
		this.inis = inis;
		this.stockValue = stockValue;
		this.amount = amount;
		this.tradeValue = tradeValue;
	}
}
