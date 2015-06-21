package com.travel.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bupttravel.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.travel.beans.UserVo;
//import com.travel.db.DBManager;
import com.travel.net.AsyncHttpHelper;
import com.travel.net.HttpHandler;
import com.travel.util.URLUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyTravelListActivity extends Activity {
	private List<Map<String, Object>> myTravelList;
	private ListView myTravelListView;
	private UserVo user;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_travel);
		
		myTravelListView = (ListView)findViewById(R.id.list_my_travel);
		//get user info
		user = (UserVo) getIntent().getSerializableExtra("user"); 
		//get my travel list info
		getMyTravel();
		
		SimpleAdapter myTravelAdapter = new SimpleAdapter(this, myTravelList, R.layout.travel_item,
				new String[] {"title", "date", "days", "author_name", "city", "" },
				new int[] {R.id.title_travel_list, R.id.date_travel_list, R.id.days_travel_list, R.id.author_travel_list, R.id.place_travel_list });
		myTravelListView.setAdapter(myTravelAdapter);
		//TODO change background
		
		myTravelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO get travel details
				Log.i("click position:", String.valueOf(position));
				Map<String, Object> clickedItemMap = (HashMap<String, Object>) myTravelListView.getItemAtPosition(position);
				if(clickedItemMap != null)
				{
//					ArrayList<Map<String, Object>> myTravelDetail = getTravelDetail((integer)clickedItemMap.get("tid"), (integer)clickedItemMap.get("author"));
					Intent intent = new Intent(MyTravelListActivity.this, MyTravelDetailActivity.class);
					Bundle bundle = new Bundle();  
					bundle.putSerializable("tid", (Integer)clickedItemMap.get("tid")); 
					bundle.putSerializable("author", (Integer)clickedItemMap.get("author"));
					intent.putExtras(bundle);  
			        startActivity(intent);
				}
			}
		});
	}
	
	private void getMyTravel()
	{
		String url = URLUtil.BASE_URL + URLUtil.mytravel;
		pDialog = ProgressDialog.show(this, "请稍等!", "数据加载中");
//		myTravelList = new ArrayList<Map<String, Object>>();
		if (user != null) {
			RequestParams params = null;
			if (params == null) {
				params = new RequestParams();
//				params.put("DName", DeviceInfoUtil.getInstance(UserInfoActivity.this).imei);
				params.put("Uid", user.getUid());
//				params.put("pwd", password);
				AsyncHttpHelper.get(url, params, new JsonHttpResponseHandler(){
					private int code;
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, final org.json.JSONObject response) 
					{ 
						// 成功后返回一个JSONObject数据
						pDialog.dismiss();
						super.onSuccess(statusCode, headers, response);
						code = response.optInt("code");
						
						AlertDialog.Builder dialog = new AlertDialog.Builder(MyTravelListActivity.this); 
					    dialog.setTitle("提示");//窗口名            
					    dialog.setPositiveButton("确定", null); 
					    switch (code) 
					    {
					    	case HttpHandler.SUCCESS:
							if (!isFinishing())
							{
								JSONArray jsonArray = response.optJSONArray("dataList");
								for (int i = 0; i < jsonArray.length(); i++) {
									try {
										// 获取具体的一个JSONObject对象
										JSONObject obj = jsonArray.getJSONObject(i);  
										Map<String, Object> travel = new HashMap<String, Object>();
										travel.put("tid", obj.optInt("tid"));
										travel.put("title", obj.getString("title"));
										travel.put("author_id", obj.getString("author_id"));
										travel.put("author_name", obj.getString("author_name"));
										travel.put("days", obj.optInt("days"));
//										travel.put("date", obj.getString("date"));
//										String[] dataFormat = new String[]{"yyyy/MM/dd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}; 
										String dateFormat = "yyyy-MM-dd";
										SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
										Date date = null;
										try {
											date = sdf.parse(obj.getString("date"));
										} catch (ParseException e) {
											e.printStackTrace();
										}
										travel.put("date", date);
										travel.put("city", obj.getString("city"));
										travel.put("backgroundPhoto", obj.getString("backgroundPhoto"));
										myTravelList.add(travel);
//										private List<Map<String, Object>> myTravelList = new ArrayList<Map<String, Object>>();
//										Travel travel = new Travel(obj.getString("title"), user, obj.optInt("tid"), obj.optInt("days"),
//												obj.getString("date"), obj.getString("place"), obj.getString("backgroundPhotoString"));
						            } catch (JSONException e) {
						            	// TODO Auto-generated catch block
						            	e.printStackTrace();
						            }
								}
							}
							else
							{
								dialog.setMessage("用户信息获取异常，请稍后重试！ ");
								dialog.show();
							}
					    	break;
						    case HttpHandler.FAIL:
						    	if (!isFinishing())
						    	{  
						    		try {
						    			String msg = response.getString("msg");
						    			dialog.setMessage(msg);
						    			dialog.show();
						    			} catch (JSONException e) {
						    				// TODO: handle exception
						    				e.printStackTrace();
						    			}
						    		}
						    	break;
						    default:
						    	break;
						}
					}
					
		            @Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) 
					{
						pDialog.dismiss();
						AlertDialog.Builder dialog = new AlertDialog.Builder(MyTravelListActivity.this); 
			            dialog.setTitle("提示");//窗口名           
			            dialog.setMessage("请求失败，请稍后再试！ ");  
			            dialog.setPositiveButton("确定", null);  
			            dialog.show();   
					}
		            
		            @Override
		            public void onFinish() {}
				});
			}			
		}
	}
}
