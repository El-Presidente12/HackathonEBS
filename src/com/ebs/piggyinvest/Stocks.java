package com.ebs.piggyinvest;

public class Stocks 
{
	public static double money = 1000;
	
	public static String[] stockNames = new String[] {
		"adidas AG",
		"Allianz" ,
		"BASF" ,
		"Bayer" ,
		"BMW" ,
		"Beiersdorf",
		"Commerzbank" ,
		"Daimler" ,
		"Dt. Bank" ,
		"Dt. Börse" ,
		"Lufthansa" ,
		"Dt. Post",
		"Dt. Telekom",
		"E.ON" ,
		"Fresenius",
		"Fresenius Medical Care",
		"Heidelberger Zement" ,
		"Henkel KGA",
		"Infineon Technologies",
		"K + S Grundstoffe",
		"Linde",
		"MAN",
		"Merck" ,
		"METRO" ,
		"Münchener Rückversicherung",
		"RWE" ,
		"SAP" ,
		"Siemens" ,
		"Thyssen Krupp" ,
		"Volkswagen VZ" };

	public static int[] stockISINs = new int[] {
		500340,
		840400,
		515100,
		575200,
		519000,
		520000,
		803200,
		710000,
		514000,
		581005,
		823212,
		555200,
		555750,
		761440,
		578563,
		578580,
		604700,
		604843,
		623100,
		716200,
		648300,
		593700,
		659990,
		725750,
		843002,
		703712,
		716460,
		723610,
		750000,
		766403

	};
	
	public static double[] stocksValues = new double[] {
		
		52.89,
		118.35,
		67.32,
		102.79,
		76.88,
		62.59,
		11.03,
		57.09,
		24.14,
		52.2,
		10.51,
		23.11,
		10.69,
		13.32,
		40.89,
		55.0,
		49.58,
		72.47,
		7.08,
		19.5,
		145.75,
		89.82,
		65.79,
		23.0,
		143.34,
		25.93,
		51.85,
		82.57,
		17.64,
		153.03

	};
	public static int[] amounts = new int[] {0,4,6,5,4,1,4,6,6,8,3,5,2,8,2,4,7,2,8,4,5,3,6,8,3,7,4,3,6,8 };
	public static double[] tradeValues = new double[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
}
