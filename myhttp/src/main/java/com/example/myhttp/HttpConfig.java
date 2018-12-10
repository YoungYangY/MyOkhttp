package com.example.myhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


public class HttpConfig {
	
	public static final int HTTP_RESPONSE_DATA_TYPE_JSON = 1;
	public static final int HTTP_RESPONSE_DATA_TYPE_STRING = 2;
	
	public static final int HTTP_CACHE_TYPE_NET = 1;
	public static final int HTTP_CACHE_TYPE_CACHE_NET = 2;
	public static final int HTTP_CACHE_TYPE_NET_CACHE = 3;
	
	// 网络类型
	public static final String NET_TYPE_NULL = "0";
	public static final String NET_TYPE_WIFI = "1";
	public static final String NET_TYPE_2G = "2";
	public static final String NET_TYPE_3G = "3";
	public static final String NET_TYPE_4G = "4";
	public static final String NET_TYPE_NAME_2G = "EDGE/GPRS";
	public static final String NET_TYPE_NAME_3G = "HSDPA";
	
	private static String SERIALNUMBER = null;

	private static boolean IS_CONNECTED=false;
	private static String NET_TYPE= NET_TYPE_NULL;

	public synchronized static boolean getIS_CONNECTED() {
		return IS_CONNECTED;
	}
	
	public synchronized static void setIS_CONNECTED(boolean iS_CONNECTED) {
		IS_CONNECTED = iS_CONNECTED;
	}
	
	public synchronized static String getNET_TYPE() {
		return NET_TYPE;
	}
	
	public synchronized static void setNET_TYPE(String type) {
		NET_TYPE = type;
	}
	
	public static String getNetworkInfo(Context cxt) {
		String type = HttpConfig.NET_TYPE_2G;
		ConnectivityManager connectManager = (ConnectivityManager) cxt
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			HttpConfig.setIS_CONNECTED(true);
			int typeNo = info.getType();
			if (typeNo == ConnectivityManager.TYPE_WIFI) {
				type = HttpConfig.NET_TYPE_WIFI;
			} else if (typeNo == ConnectivityManager.TYPE_MOBILE) {
				int subType = info.getSubtype();
				switch (subType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:// 移动和联通的2G
				case TelephonyManager.NETWORK_TYPE_EDGE:// 移动和联通的2G
				case TelephonyManager.NETWORK_TYPE_CDMA:// 电信的2G
					type = HttpConfig.NET_TYPE_2G;
					break;
				case TelephonyManager.NETWORK_TYPE_LTE:
					type = HttpConfig.NET_TYPE_4G;
					break;
				default:
					type = HttpConfig.NET_TYPE_3G;
					break;
				}
			}
		} else {
			type = HttpConfig.NET_TYPE_NULL;
			HttpConfig.setIS_CONNECTED(false);
		}
		HttpConfig.setNET_TYPE(type);
		return type;
	}
	
}
