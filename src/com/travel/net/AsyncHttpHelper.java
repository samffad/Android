package com.travel.net;

import java.util.Map;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class AsyncHttpHelper {
	private static AsyncHttpClient asyncHttpClinet = new AsyncHttpClient();
	
	public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) 
	{
		asyncHttpClinet.post(url, params, responseHandler);
	}
	public static void get(String url, ResponseHandlerInterface callback)
	{
		asyncHttpClinet.get(url, callback);
	}
    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler)   //带参数，获取json对象或者数组
    {
    	asyncHttpClinet.get(url, params,responseHandler);
    }
//	public static void get(String urlString,AsyncHttpResponseHandler res)    //用一个完整url获取一个string对象
//    {
//		asyncHttpClinet.get(urlString, res);
//    }
//    public static void get(String urlString,RequestParams params,AsyncHttpResponseHandler res)   //url里面带参数
//    {
//    	asyncHttpClinet.get(urlString, params,res);
//    }
//    public static void get(String urlString,JsonHttpResponseHandler res)   //不带参数，获取json对象或者数组
//    {
//    	asyncHttpClinet.get(urlString, res);
//    }
//    public static void get(String uString, BinaryHttpResponseHandler bHandler)   //下载数据使用，会返回byte数据
//    {
//    	asyncHttpClinet.get(uString, bHandler);
//    }
    public static AsyncHttpClient getClient()
    {
        return asyncHttpClinet;
    }
	
}
