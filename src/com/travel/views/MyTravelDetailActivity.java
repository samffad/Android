package com.travel.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bupttravel.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.travel.net.AsyncHttpHelper;
import com.travel.net.HttpHandler;
import com.travel.util.URLUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyTravelDetailActivity extends Activity {
	private ArrayList<Map<String, Object>> footprintList;
	private ListView footprintListView;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.footprint_list);
		

//		user = (UserVo) getIntent().getSerializableExtra("user"); 
		Integer tid= (Integer) getIntent().getSerializableExtra("tid");
		Integer author = (Integer) getIntent().getSerializableExtra("author");  
		if(tid != null && author != null)
		{
			footprintList = getTravelDetail(tid.intValue(), author.intValue());
			
			//get footprint photos
			SimpleAdapter footprintAdapter = new SimpleAdapter(this, footprintList, R.layout.footprint_item,
					new String[] {"footprintPhoto","desc","date","location" },
					new int[] {R.id.footprint_image, R.id.footprint_desc, R.id.footprint_date,R.id.footprint_loc});
			footprintListView.setAdapter(footprintAdapter);
		}
		else {
			AlertDialog.Builder dialog = new AlertDialog.Builder(MyTravelDetailActivity.this); 
			dialog.setTitle("��ʾ");//������            
			dialog.setPositiveButton("ȷ��", null); 
			dialog.setMessage("���¼�����ԣ� ");
			dialog.show();
		}
		
		
	}
	
	private ArrayList<Map<String, Object>> getTravelDetail(int tid, int uid)
	{
		String url = URLUtil.BASE_URL + URLUtil.traveldetail;
		pDialog = ProgressDialog.show(this, "���Ե�!", "���ݼ�����");
		final ArrayList<Map<String, Object>> footprintList = new ArrayList<Map<String, Object>>();
		
		RequestParams params = null;
		if (params == null) {
			params = new RequestParams();
			params.put("Uid", uid);
			params.put("Tid", tid);
			AsyncHttpHelper.get(url, params, new JsonHttpResponseHandler(){
				private int code;
				@Override
				public void onSuccess(int statusCode, org.apache.http.Header[] headers, final org.json.JSONObject response) 
				{ 
					// �ɹ��󷵻�һ��JSONObject����
					pDialog.dismiss();
					super.onSuccess(statusCode, headers, response);
					code = response.optInt("code");
						
					AlertDialog.Builder dialog = new AlertDialog.Builder(MyTravelDetailActivity.this); 
					dialog.setTitle("��ʾ");//������            
					dialog.setPositiveButton("ȷ��", null); 
					switch (code)
					{
					case HttpHandler.SUCCESS:
					if (!isFinishing()){
						JSONArray jsonArray = response.optJSONArray("dataList");
						for (int i = 0; i < jsonArray.length(); i++) {
							try {
								// ��ȡ�����һ��JSONObject����
								JSONObject obj = jsonArray.getJSONObject(i);  
								Map<String, Object> footprint = new HashMap<String, Object>();
								footprint.put("pid", obj.optInt("pid"));
								footprint.put("desc", obj.getString("desc"));
//								travel.put("author", user.getUid());
								footprint.put("x", obj.optInt("x"));
								footprint.put("y", obj.optInt("y"));
								footprint.put("location", obj.getString("location"));
								footprint.put("date", obj.getString("date"));
								footprint.put("footprintPhoto", obj.getString("footprintPhoto"));
								footprintList.add(footprint);
							} catch (JSONException e) {
						            e.printStackTrace();
						    }
						}
					}
					else{
						dialog.setMessage("�μ���Ϣ��ȡ�쳣�����Ժ����ԣ� ");
						dialog.show();
					}
					break;
					case HttpHandler.FAIL:
						if (!isFinishing()){
							try {
								String msg = response.getString("msg");
						    	dialog.setMessage(msg);
						    	dialog.show();
						    } catch (JSONException e) {
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
					AlertDialog.Builder dialog = new AlertDialog.Builder(MyTravelDetailActivity.this); 
			        dialog.setTitle("��ʾ");//������           
			        dialog.setMessage("����ʧ�ܣ����Ժ����ԣ� ");  
			        dialog.setPositiveButton("ȷ��", null);  
			        dialog.show();   
				}
		            
		        @Override
		        public void onFinish() {}
		    });
		}	
		return footprintList;
	}
}
