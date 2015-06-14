package com.travel.net;

import android.util.Log;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.travel.views.UserInfoActivity;

public class ImageHelper {
	private AsyncHttpHelper helper;
	private static ImageHelper instance;
	
	public static ImageHelper getInstance()
	{
		if(instance == null)
			instance = new ImageHelper();
		return instance;
	}
	
	private ImageHelper()
	{
		helper = new AsyncHttpHelper();
	}
	
//	public void getImage(String url)
//	{
//		helper.get(url, new BinaryHttpResponseHandler() {
//			
//			@Override
//			public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] binaryData) 
//			{		
//				Log.i("onSuccess:" + statusCode, " binaryData:" + binaryData.length);
//				UserInfoActivity.this.updateImage(binaryData);
//			}
//			
//			@Override
//			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] binaryData, Throwable error) 
//			{
//				Log.i("onFailure:" + error, " statusCode:" + statusCode);
//			}
//		});
//	}
	
}
