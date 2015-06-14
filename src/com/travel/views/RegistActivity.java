package com.travel.views;

import java.io.IOException;

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
import com.travel.net.AsyncHttpHelper;
import com.travel.net.HttpHandler.Invalid;
import com.travel.util.DeviceInfoUtil;
import com.travel.util.URLUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class RegistActivity extends Activity {
	private BizMsg<User> bizMsg;
	private User user;
	private String userName; 
	private String password;
	private String password_repeat;
	private String gender = "NA";
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		
		final EditText userNameText = (EditText) findViewById(R.id.userName_regist);
		final EditText passwordText = (EditText) findViewById(R.id.password_regist);
		final EditText passwordRepeatText = (EditText) findViewById(R.id.password_regist_repeat);
		final RadioGroup genderCheckbox =(RadioGroup) findViewById(R.id.sex_regist);
		genderCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				// TODO Auto-generated method stub
//				String tip = (checkedId == R.id.male)
				gender = checkedId == R.id.male?"M":"F";
			}
		});
		Button regist = (Button) findViewById(R.id.submit_regist);  
		regist.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	userName = userNameText.getText().toString(); 
		    	password = passwordText.getText().toString();  
		    	password_repeat = passwordRepeatText.getText().toString(); 
		    	//veritf the user input
		    	if(validate())
		    	{
		    		//upload user regist info
		    		registPro(); 
		    	}
		    }
		});  
	}
	
	private boolean validate()
	{
		Log.i("password", password);
		Log.i("password_repeat", password_repeat);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this); 
        dialog.setTitle("提示");//窗口名           
//        dialog.setMessage("账号不能为空！ ");  
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
		if(password_repeat.equals(""))
		{
            dialog.setMessage("重复密码不能为空！ ");  
            dialog.show(); 
            return false;
		}
		if(gender.equals("NA"))
		{
            dialog.setMessage("请选择性别！ ");  
            dialog.show(); 
            return false;
		}
		if(!password_repeat.equals(password))
		{
            dialog.setMessage("两次密码不匹配！ "); 
            dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int which) { 
                	final EditText passwordText = (EditText) findViewById(R.id.password_regist);
            		final EditText passwordRepeatText = (EditText) findViewById(R.id.password_regist_repeat);
                	passwordText.setText("");
                	passwordRepeatText.setText("");
                }  
            });  
            dialog.show(); 
            return false;
		}
		return true;
	}
	
	private void registPro()
	{
		pDialog = ProgressDialog.show(this, "请稍等!", "数据加载中");
		String url = URLUtil.BASE_URL + URLUtil.register;
		RequestParams params = null;
		if (params == null) {
			params = new RequestParams();
			params.put("DName", DeviceInfoUtil.getInstance(RegistActivity.this).imei);
			params.put("UName", userName);
			params.put("pwd", password);
			params.put("gender", gender);
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
						AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this); 
			            dialog.setTitle("提示");//窗口名            
			            dialog.setPositiveButton("确定", null);  
						switch (code) 
						{
							case HttpHandler.SUCCESS:
								if (!isFinishing())
								{    
						            dialog.setMessage("注册成功！ ");   
						            dialog.show(); 
//									Gson son = new Gson();
//									user = son.fromJson(response.toString(), User.class);
//									user.setName(userName);
//									DBManager manager = DBManager.getInstance(RegistActivity.this);
//									long index = manager.addUser(user);
//									if (index >= 0) 
//									{
//										Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
//								        startActivity(intent);
//									} 
//									else 
//									{
//							            dialog.setMessage("系统异常，请稍后重试！ ");  
//							            dialog.show(); 
//									}
						            ObjectMapper objectMapper = new ObjectMapper();
								    String jsonString = "";
								    try {
								    	bizMsg = objectMapper.readValue(jsonString, BizMsg.class);
								    	Log.i("LoginJsonString =", bizMsg.toString());
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
								    		DBManager manager = DBManager.getInstance(RegistActivity.this);
											user = bizMsg.getDataList().get(0);
											if(user != null)
											{
												long index = manager.addUser(user);
												if (index >= 0) 
												{
													Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
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
				}, RegistActivity.this);
			}
			
            @Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) 
			{
				pDialog.dismiss();
				AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this); 
	            dialog.setTitle("提示");//窗口名           
	            dialog.setMessage("请求失败，请稍后再试！ ");  
	            dialog.setPositiveButton("确定", null);  
	            dialog.show();   
			}
            
            @Override
            public void onFinish() {
                pDialog.dismiss();
				AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this); 
	            dialog.setTitle("提示");//窗口名           
	            dialog.setMessage("网络错误，请稍后再试！ ");  
	            dialog.setPositiveButton("确定", null);  
	            dialog.show();   
            }
        });
	}
}
