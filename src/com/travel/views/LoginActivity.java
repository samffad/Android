package com.travel.views;

//import com.example.login.MainActivity;
//import com.example.login.UserInfoActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.webviewtest.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.travel.beans.BizMsg;
import com.travel.beans.User;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

//	private WebView myWebView = null;
//	private String url;
	private BizMsg<User> bizMsg;
	private User user;
	private String userName; 
	private String password;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
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
        dialog.setTitle("提示");//窗口名           
        dialog.setPositiveButton("确定", null);  
		if(userName.equals(""))
		{     
            dialog.setMessage("账号不能为空！ ");  
            dialog.show(); 
            return false;
		}
		if(password.equals(""))
		{   
            dialog.setMessage("密码不能为空！ ");   
            dialog.show(); 
            return false;
		}
		if(password.length()<6 || password.length()>20)
		{
            dialog.setMessage("密码长度为6-20位！ ");  
            dialog.show(); 
            return false;
		}
		return true;
	}
	
	private void loginPro()
	{
		pDialog = ProgressDialog.show(this, "请稍等!", "数据加载中");
		String url = URLUtil.BASE_URL + URLUtil.login;
		RequestParams params = null;
		if (params == null) {
//			String timesry=""+System.currentTimeMillis();
			params = new RequestParams();
			params.put("DName", DeviceInfoUtil.getInstance(LoginActivity.this).imei);
			params.put("UName", userName);
			params.put("pwd", password);
//			params.put(
//					"pwd",
//					PasswordUtility.EncodePwd(
//							PasswordUtility.StringMD5(password), timesry));
//			params.put("third_token", "");
//			params.put("type", "0");
//			params.put("timestamp", timesry);
		}
		AsyncHttpHelper.post(url, params, new JsonHttpResponseHandler(){
			private int status;
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, final org.json.JSONObject response) 
			{ 
				// 成功后返回一个JSONObject数据
				pDialog.dismiss();
				super.onSuccess(statusCode, headers, response);
				status = response.optInt("status");
				new HttpHandler().InvalidCode(status, new Invalid(){
					
					@Override
					public void invalidCallback(int code)
					{
						AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this); 
			            dialog.setTitle("提示");//窗口名            
			            dialog.setPositiveButton("确定", null);  
						switch (code) 
						{
							case HttpHandler.SUCCESS:
								if (!isFinishing())
								{
//									dialogLoading.dismiss();
//									Gson son = new Gson();
//									user = son.fromJson(response.toString(), User.class);
//									user.setU_name(userName);
									ObjectMapper objectMapper = new ObjectMapper();
								    String jsonString = "";
//								    //将对象转化为Json字符串
//								    try {
//								        jsonString = objectMapper.writeValueAsString(school);
//								        System.out.println("Jackson="+jsonString);
//								      } catch (JsonProcessingException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      }
								      //将json字符串解析成java对象
								    try {
								    	bizMsg = objectMapper.readValue(jsonString, BizMsg.class);
								    	Log.i("LoginJsonString =", bizMsg.toString());
//								        System.out.println(resultSchool.getStudents().get(0).getName());
								      } catch (JsonParseException e) {
								        // TODO Auto-generated catch block
								        e.printStackTrace();
								      } catch (JsonMappingException e) {
								        // TODO Auto-generated catch block
								        e.printStackTrace();
								      } catch (IOException e) {
								        // TODO Auto-generated catch block
								        e.printStackTrace();
								      }
								    if(bizMsg != null)
								    {
								    	if(!bizMsg.getDataList().isEmpty())
								    	{
								    		DBManager manager = DBManager.getInstance(LoginActivity.this);
											user = bizMsg.getDataList().get(0);
											if(user != null)
											{
												long index = manager.addUser(user);
												if (index >= 0) 
												{
//													setResult(100);
//													finish();
													Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
													Bundle bundle = new Bundle();  
													bundle.putSerializable("user", user); 
													intent.putExtras(bundle);  
											        startActivity(intent);
												} 
												else 
												{          
										            dialog.setMessage("数据库异常，请稍后重试！ ");   
										            dialog.show(); 
												}
											}
											else
											{        
									            dialog.setMessage("数据列表异常，请稍后重试！ ");  
									            dialog.show(); 
											}
								    	}
								    	else
								    	{
								    		dialog.setMessage(bizMsg.getMsg());   
								            dialog.show();
								    	}
								    }
								    else
								    {
								    	dialog.setMessage("网络异常，请稍后重试！ ");   
							            dialog.show();
								    }
								}
								break;
							case HttpHandler.FAIL:
								if (!isFinishing())
								{  
						            dialog.setMessage("系统异常，请稍后重试！ ");   
						            dialog.show();
						        }
								break;
							default:
									break;
						}
					}
				}, LoginActivity.this);
			}
			
            @Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) 
			{
				pDialog.dismiss();
				AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this); 
	            dialog.setTitle("提示");//窗口名           
	            dialog.setMessage("请求失败，请稍后再试！ ");  
	            dialog.setPositiveButton("确定", null);  
	            dialog.show();   
			}
            
            @Override
            public void onFinish() {
                pDialog.dismiss();
				AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this); 
	            dialog.setTitle("提示");//窗口名           
	            dialog.setMessage("网络错误，请稍后再试！ ");  
	            dialog.setPositiveButton("确定", null);  
	            dialog.show();   
            }
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
