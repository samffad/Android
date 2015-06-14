package com.travel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class DeviceInfoUtil {
	public String appVersion;
	public String appName;
	
	public String imei;
	public String imsi;
	public String phone;
	public int screenSizeWidth;
	public int screenSizeHeight;
	public String Operator;
	public String manufacturer;
	public String phoneType;
	public String SDKVersion;
	public String Ip;
//	public int networkType;
	
//	public String systemVersion = String.valueOf(Build.VERSION.SDK_INT);
//	public String system = Build.MODEL + " : " + Build.DEVICE;
	private static DeviceInfoUtil instance;
	
	public static DeviceInfoUtil getInstance(Context context) 
	{
		if (instance == null) {
			instance = new DeviceInfoUtil(context.getApplicationContext());
		}
		return instance;
	}
	
	private DeviceInfoUtil(Context context) {
		initAppInfo(context);
		initDeviceId(context);
		initScreenInfo(context);
		initIp(context);
	}
	
	/**
	 * Quit.
	 */
	public static void quit() {
		instance = null;
	}
	
	private void initAppInfo(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			String appName = context.getPackageName();
			PackageInfo info = manager.getPackageInfo(appName, 0);
			appVersion = String.valueOf(info.versionName);
			this.appName = appName;
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	private String getDeviceId(TelephonyManager tm,Context context){
		String imei_code=tm.getDeviceId();
		
		String m_szDevIDShort = "35" + 
		Build.BOARD.length()%10 + 
		Build.BRAND.length()%10 + 
		Build.CPU_ABI.length()%10 + 
		Build.DEVICE.length()%10 + 
		Build.DISPLAY.length()%10 + 
		Build.HOST.length()%10 + 
		Build.ID.length()%10 + 
		Build.MANUFACTURER.length()%10 + 
		Build.MODEL.length()%10 + 
		Build.PRODUCT.length()%10 + 
		Build.TAGS.length()%10 + 
		Build.TYPE.length()%10 + 
		Build.USER.length()%10 ; 
		
		String m_szAndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
		String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
		
		String m_szBTMAC = "";
		try {
			BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter      
			m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();      
			m_szBTMAC = m_BluetoothAdapter.getAddress();
		} catch (Exception e) {
			m_szBTMAC = "";
			//TODO log it
		}
		

		String m_szLongID = imei_code + m_szDevIDShort 
			    + m_szAndroidID+ m_szWLANMAC + m_szBTMAC;      
			// compute md5     
			 MessageDigest m = null;   
			try {
			 m = MessageDigest.getInstance("MD5");
			 } catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();   
			}    
			m.update(m_szLongID.getBytes(),0,m_szLongID.length());   
			// get md5 bytes   
			byte p_md5Data[] = m.digest();   
			// create a hex string   
			String m_szUniqueID = new String();   
			for (int i=0;i<p_md5Data.length;i++) {   
			     int b =  (0xFF & p_md5Data[i]);    
			// if it is a single digit, make sure it have 0 in front (proper padding)    
			    if (b <= 0xF) 
			        m_szUniqueID+="0";    
			// add number to string    
			    m_szUniqueID+=Integer.toHexString(b); 
			   }   // hex string to uppercase   
			m_szUniqueID = m_szUniqueID.toUpperCase();

		return m_szUniqueID;
	}
	
	private void initDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		imei = getDeviceId(tm, context);
		phone=tm.getLine1Number();
		imsi = tm.getSubscriberId();
		manufacturer =android.os.Build.MANUFACTURER;
		phoneType=android.os.Build.MODEL;
		SDKVersion=android.os.Build.VERSION.RELEASE;
		String operator = tm.getSimOperator();

		if(operator!=null){

		if(operator.equals("46000") || operator.equals("46002")|| operator.equals("46007")){

		//中国移动
         Operator="移动";
		}else if(operator.equals("46001")){

		//中国联通
		 Operator="联通";

		}else if(operator.equals("46003")){

		//中国电信
		Operator="电信";

		}else{
			Operator="其他";
		}
		}
	}
	
	private void initScreenInfo(Context context) {
		WindowManager mWm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Point size = new Point();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
			mWm.getDefaultDisplay().getSize(size);
		    screenSizeWidth = size.x;
		    screenSizeHeight = size.y; 
		}else{
		    Display d = mWm.getDefaultDisplay(); 
		    screenSizeWidth = d.getWidth(); 
		    screenSizeHeight = d.getHeight(); 
		}
		Log.i("Width:"+String.valueOf(screenSizeWidth), "Height"+String.valueOf(screenSizeHeight));
//		AppLogger.d("Width:"+String.valueOf(screenSizeWidth)+","+"Height"+String.valueOf(screenSizeHeight));
	}
	
	/**
	 * Gets the current net type.
	 *
	 * @param context the context
	 * @return the current net type
	 */
	public static String getCurrentNetType(Context context) { 
			    String type = ""; 
			    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
			    NetworkInfo info = cm.getActiveNetworkInfo(); 
			    if (info == null) { 
			        type = "null"; 
		    } else if (info.getType() == ConnectivityManager.TYPE_WIFI) { 
			        type = "wifi"; 
			    } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) { 
			        int subType = info.getSubtype(); 
			        if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS 
			                || subType == TelephonyManager.NETWORK_TYPE_EDGE) { 
			            type = "2g"; 
			        } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA 
			                || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0 
			                || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) { 
			            type = "3g"; 
			        } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准 
			            type = "4g"; 
			        } 
			    } 
			    return type; 
			}
	
	/**
	 * Inits the ip.
	 *
	 * @param context the context
	 */
	public void initIp(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        //判断wifi是否开启  
        if (!wifiManager.isWifiEnabled()) {  
        wifiManager.setWifiEnabled(true);    
        }  
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
        int ipAddress = wifiInfo.getIpAddress();   
        Ip = intToIp(ipAddress);   
    }     
    private String intToIp(int i) {       
         
        return (i & 0xFF ) + "." +       
        ((i >> 8 ) & 0xFF) + "." +       
        ((i >> 16 ) & 0xFF) + "." +       
        ( i >> 24 & 0xFF) ;  
     }   
}
