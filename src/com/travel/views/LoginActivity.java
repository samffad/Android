package com.travel.views;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.travel.views.RegistActivity;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.bupttravel.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.travel.beans.BizMsg;
import com.travel.beans.UserVo;
import com.travel.db.DBManager;
import com.travel.net.HttpHandler;
import com.travel.net.HttpHandler.Invalid;
import com.travel.net.AsyncHttpHelper;
import com.travel.util.DeviceInfoUtil;
import com.travel.util.URLUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private AMap aMap;

//	private WebView myWebView = null;
//	private String url;
//	private BizMsg<UserVo> bizMsg;
	private UserVo user;
	private String userName; 
	private String password;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//--------------------
        final MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// ����Ҫд
        if (aMap == null) {
            aMap = mapView.getMap();
        }
		//--------------------
//		url = URLUtil.login;
		final EditText userNameText = (EditText) findViewById(R.id.userName_Login);
		final EditText passwordText = (EditText) findViewById(R.id.password_Login);
		Button login = (Button) findViewById(R.id.submit_Login);  
		login.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	userName = userNameText.getText().toString(); 
		    	password = passwordText.getText().toString();  
		    	
		    	Log.i("userName=", userName);
		    	Log.i("password=", password);
		    	
		    	// check user name and password is valid
		        if(validate())
		        {
		        	loginPro();
		        }
		    }  
		});  
		Button regist = (Button) findViewById(R.id.regist_Login);  
		regist.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	//Intent intent = new Intent
		    	Intent intent = new Intent(LoginActivity.this, RegistActivity.class);//UserInfoActivity.class);
		        startActivity(intent);
		    }
		});  
		
//		myWebView = (WebView) findViewById(R.id.webview);
//		WebSettings websettings = myWebView.getSettings();
//		websettings.setJavaScriptEnabled(true);
//		
//		myWebView.setWebViewClient(new WebViewClient());
//		
//		myWebView.loadUrl("http://www.baidu.com");
		
//		String htmlString = "<h1>Title</h1><p>This is HTML text<br /><i>Formatted in italics</i><br />Anothor Line</p>"; 
//		myWebView.loadData(htmlString, "text/html", "utf-8"); 

	}

	private boolean validate()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this); 
        dialog.setTitle("��ʾ");//������           
        dialog.setPositiveButton("ȷ��", null);  
		if(userName.equals(""))
		{     
            dialog.setMessage("�˺Ų���Ϊ�գ� ");  
            dialog.show(); 
            return false;
		}
		if(password.equals(""))
		{   
            dialog.setMessage("���벻��Ϊ�գ� ");   
            dialog.show(); 
            return false;
		}
		if(password.length()<6 || password.length()>20)
		{
            dialog.setMessage("���볤��Ϊ6-20λ�� ");  
            dialog.show(); 
            return false;
		}
		return true;
	}
	
	private void loginPro()
	{
		pDialog = ProgressDialog.show(this, "���Ե�!", "���ݼ�����");
		String url = URLUtil.BASE_URL + URLUtil.login;
		RequestParams params = null;
		if (params == null) {
//			String timesry=""+System.currentTimeMillis();
			params = new RequestParams();
			params.put("DName", DeviceInfoUtil.getInstance(LoginActivity.this).imei);
			params.put("UName", userName);
			params.put("pwd", password);
		}
		AsyncHttpHelper.post(url, params, new JsonHttpResponseHandler(){
			private int code;
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, final org.json.JSONObject response) 
			{ 
				// �ɹ��󷵻�һ��JSONObject����
				pDialog.dismiss();
				super.onSuccess(statusCode, headers, response);
				code = response.optInt("code");
//				new HttpHandler().InvalidCode(status, new Invalid(){
//					
//					@Override
//					public void invalidCallback(int code)
//					{
						AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this); 
			            dialog.setTitle("��ʾ");//������            
			            dialog.setPositiveButton("ȷ��", null);  
						switch (code) 
						{
							case HttpHandler.SUCCESS:
								if (!isFinishing())
								{
//									dialogLoading.dismiss();
//									Gson son = new Gson();
//									user = son.fromJson(response.toString(), User.class);
//									user.setU_name(userName);
//									ObjectMapper objectMapper = new ObjectMapper();
//								    String jsonString = "";
//								    //������ת��ΪJson�ַ���
//								    try {
//								        jsonString = objectMapper.writeValueAsString(school);
//								        System.out.println("Jackson="+jsonString);
//								      } catch (JsonProcessingException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      }
								      //��json�ַ���������java����
//								    try {
//								    	bizMsg = objectMapper.readValue(jsonString, BizMsg.class);
//								    	Log.i("LoginJsonString =", bizMsg.toString());
////								        System.out.println(resultSchool.getStudents().get(0).getName());
//								      } catch (JsonParseException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      } catch (JsonMappingException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      } catch (IOException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      }
									JSONArray jsonArray = response.optJSONArray("dataList");
							    	for (int i = 0; i < jsonArray.length(); i++) {  
				                        try {  
				                            // ��ȡ�����һ��JSONObject����  
				                            JSONObject obj = jsonArray.getJSONObject(i);  
				                            //JSONObject����get(����������)��getString(����������),getInt(����������)�ȷ�������ȡָ����������ֵ  
//				                            System.out.println("���" + obj.getString("uid")  
//				                                    + "--------������" + obj.getString("name")  
//				                                    + "--------�ǳƣ�" + obj.getString("nickname")  
//				                                    + "--------�Ա�" + obj.getString("gender")  
//				                                    + "--------ͷ��" + obj.getString("photoPath"));  
				                            user = new UserVo(obj.optInt("uid"), obj.getString("name"),
				                            		obj.getString("nickname"), obj.getString("gender"), obj.getString("photoPath"));
//				                            LoginActivity.this.user = new UserVo(1, obj.getString("name"),
//				                            		obj.getString("nickname"), obj.getString("gender"), obj.getString("photoPath"));
				                        } catch (JSONException e) {  
				                            // TODO Auto-generated catch block  
				                            e.printStackTrace();  
				                        }  
				                    }  
//								    if(bizMsg != null)
//								    {
//								    	if(!bizMsg.getDataList().isEmpty())
//								    	{
//								    		DBManager manager = DBManager.getInstance(LoginActivity.this);
//											user = bizMsg.getDataList().get(0);
//											if(user != null)
//											{
//												long index = manager.addUser(user);
//												if (index >= 0) 
//												{
////													setResult(100);
////													finish();
//													Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
//													Bundle bundle = new Bundle();  
//													bundle.putSerializable("user", user); 
//													intent.putExtras(bundle);  
//											        startActivity(intent);
//												} 
//												else 
//												{          
//										            dialog.setMessage("���ݿ��쳣�����Ժ����ԣ� ");   
//										            dialog.show(); 
//												}
//											}
//											else
//											{        
//									            dialog.setMessage("�����б��쳣�����Ժ����ԣ� ");  
//									            dialog.show(); 
//											}
//								    	}
//								    	else
//								    	{
//								    		dialog.setMessage(bizMsg.getMsg());   
//								            dialog.show();
//								    	}
//								    }
//								    else
//								    {
//								    	dialog.setMessage("�����쳣�����Ժ����ԣ� ");   
//							            dialog.show();
//								    }
							    	if(LoginActivity.this.user != null)
								    {
								    		DBManager manager = DBManager.getInstance(LoginActivity.this);
												long index = manager.addUser(LoginActivity.this.user);
												if (index >= 0) 
												{
//													Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
//											        startActivity(intent);
//													setResult(100);
//												finish();
												Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
												Bundle bundle = new Bundle();  
												bundle.putSerializable("user", LoginActivity.this.user); 
												intent.putExtras(bundle);  
										        startActivity(intent);
												} 
												else 
												{          
										            dialog.setMessage("���ݿ��쳣�����Ժ����ԣ� ");   
										            dialog.show(); 
												}
								    	
								    }
							    else
							    {
							    	dialog.setMessage("�û���Ϣ��ȡ�쳣�����Ժ����ԣ� ");   
						            dialog.show();
							    }
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
//					}
//				}, LoginActivity.this);
			}
			
            @Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) 
			{
				pDialog.dismiss();
				AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this); 
	            dialog.setTitle("��ʾ");//������           
	            dialog.setMessage("����ʧ�ܣ����Ժ����ԣ� ");  
	            dialog.setPositiveButton("ȷ��", null);  
	            dialog.show();   
			}
            
            @Override
            public void onFinish() {}
        });
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
