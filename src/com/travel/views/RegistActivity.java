package com.travel.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bupttravel.R;
//import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.travel.beans.BizMsg;
import com.travel.beans.UserVo;
import com.travel.db.DBManager;
import com.travel.net.HttpHandler;
import com.travel.net.AsyncHttpHelper;
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

public class RegistActivity extends Activity {
//	private BizMsg<UserVo> bizMsg;
	private UserVo user;
	private String userName; 
	private String password;
	private String password_repeat;
	private String gender = "F";
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
        dialog.setTitle("��ʾ");//������           
//        dialog.setMessage("�˺Ų���Ϊ�գ� ");  
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
		if(password_repeat.equals(""))
		{
            dialog.setMessage("�ظ����벻��Ϊ�գ� ");  
            dialog.show(); 
            return false;
		}
//		if(gender.equals("NA"))
//		{
//            dialog.setMessage("��ѡ���Ա� ");  
//            dialog.show(); 
//            return false;
//		}
		if(!password_repeat.equals(password))
		{
            dialog.setMessage("�������벻ƥ�䣡 "); 
            dialog.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {  
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
		pDialog = ProgressDialog.show(this, "���Ե�!", "���ݼ�����");
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
						AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this); 
			            dialog.setTitle("��ʾ");//������            
			            dialog.setPositiveButton("ȷ��", null);  
						switch (code) 
						{
							case HttpHandler.SUCCESS:
								if (!isFinishing())
								{    
						            dialog.setMessage("ע��ɹ��� ");   
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
//							            dialog.setMessage("ϵͳ�쳣�����Ժ����ԣ� ");  
//							            dialog.show(); 
//									}
//						            ObjectMapper objectMapper = new ObjectMapper();
//								    String jsonString = "";
//						            List dataList = response.get.getJSONArray("dataList");
//								    try {
								    	
//								    	BizMsg<UserVo> data = (BizMsg<UserVo>)objectMapper.readValue(response.toString(), BizMsg.class);
								    	// = (UserVo)data.getDataList().get(0);
								    	JSONArray jsonArray = response.optJSONArray("dataList");
								    	for (int i = 0; i < jsonArray.length(); i++) {  
					                        try {  
					                            // ��ȡ�����һ��JSONObject����  
					                            JSONObject obj = jsonArray.getJSONObject(i);  
					                            //JSONObject����get(����������)��getString(����������),getInt(����������)�ȷ�������ȡָ����������ֵ  
//					                            System.out.println("���" + obj.getString("uid")  
//					                                    + "--------������" + obj.getString("name")  
//					                                    + "--------�ǳƣ�" + obj.getString("nickname")  
//					                                    + "--------�Ա�" + obj.getString("gender")  
//					                                    + "--------ͷ��" + obj.getString("photoPath"));  
					                            user = new UserVo(obj.optInt("uid"), obj.getString("name"),
					                            		obj.getString("nickname"), obj.getString("gender"), obj.getString("photoPath"));
//					                            RegistActivity.this.user = new UserVo(1, obj.getString("name"),
//					                            		obj.getString("nickname"), obj.getString("gender"), obj.getString("photoPath"));
					                        } catch (JSONException e) {  
					                            // TODO Auto-generated catch block  
					                            e.printStackTrace();  
					                        }  
					                    }  
//								    	ArrayList<UserVo> userList = (ArrayList)response.get("dataList");
//								    	BizMsg<User> bizmsg_user = objectMapper.readValue(response, (BizMsg<User>).class);
//								    	User user = objectMapper.readValue(new File("user.json"), User.class);
//								    	user = objectMapper.readValue(response.getString("dataList"), BizMsg<User> );
//								    	Log.i("LoginJsonString =", bizMsg.toString());
//								      } 
//								    catch (JSONException e) {
//											// TODO: handle exception
//											e.printStackTrace();
//										}
//								    catch (JsonParseException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      } catch (JsonMappingException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      } catch (IOException e) {
//								        // TODO Auto-generated catch block
//								        e.printStackTrace();
//								      }
//								    if(bizMsg != null)
//								    {
//								    	if(!bizMsg.getDataList().isEmpty())
//								    	{
//								    		DBManager manager = DBManager.getInstance(RegistActivity.this);
//											user = bizMsg.getDataList().get(0);
//											if(user != null)
//											{
//												long index = manager.addUser(user);
//												if (index >= 0) 
//												{
//													Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
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
								    	if(RegistActivity.this.user != null)
									    {
									    		DBManager manager = DBManager.getInstance(RegistActivity.this);
													long index = manager.addUser(RegistActivity.this.user);
													if (index >= 0) 
													{
														Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
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
//				}, RegistActivity.this);
			}
			
            @Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) 
			{
				pDialog.dismiss();
				AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this); 
	            dialog.setTitle("��ʾ");//������           
	            dialog.setMessage("����ʧ�ܣ����Ժ����ԣ� ");  
	            dialog.setPositiveButton("ȷ��", null);  
	            dialog.show();   
			}
            
            @Override
            public void onFinish() { }
        });
		Log.i("end", userName);
	}
}
