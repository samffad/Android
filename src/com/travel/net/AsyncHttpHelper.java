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
    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler)   //����������ȡjson�����������
    {
    	asyncHttpClinet.get(url, params,responseHandler);
    }
//	public static void get(String urlString,AsyncHttpResponseHandler res)    //��һ������url��ȡһ��string����
//    {
//		asyncHttpClinet.get(urlString, res);
//    }
//    public static void get(String urlString,RequestParams params,AsyncHttpResponseHandler res)   //url���������
//    {
//    	asyncHttpClinet.get(urlString, params,res);
//    }
//    public static void get(String urlString,JsonHttpResponseHandler res)   //������������ȡjson�����������
//    {
//    	asyncHttpClinet.get(urlString, res);
//    }
//    public static void get(String uString, BinaryHttpResponseHandler bHandler)   //��������ʹ�ã��᷵��byte����
//    {
//    	asyncHttpClinet.get(uString, bHandler);
//    }
    public static AsyncHttpClient getClient()
    {
        return asyncHttpClinet;
    }
	
}
