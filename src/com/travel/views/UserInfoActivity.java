package com.travel.views;

import com.example.webviewtest.R;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.travel.beans.User;
import com.travel.net.AsyncHttpHelper;
//import com.travel.net.ImageHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity{
	private User user;
	private TextView userName;
	private ImageView userPic;
	private ImageView userGender;
	private Button addTravel;
	private Button myTravel;
	private Button draftbox;
	private Button searchTravel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);

		userName = (TextView) findViewById(R.id.userName_UserInfo);
		userPic = (ImageView) findViewById(R.id.userImage_UserInfo);
		userGender = (ImageView) findViewById(R.id.userGender_UserInfo);
		addTravel = (Button) findViewById(R.id.addTravel_UserInfo);
		myTravel = (Button) findViewById(R.id.myTravel_UserInfo);
		draftbox = (Button) findViewById(R.id.draftbox_UserInfo);
		searchTravel = (Button) findViewById(R.id.searchTravel_UserInfo);
        user = (User) getIntent().getSerializableExtra("user");
        
        userName.setText(user.getName());
        getUserImage(user.getPhotoPath());
        if(user.getGender().equals("F"))
        	userGender.setImageResource(R.drawable.gender_female);
        else
        	userGender.setImageResource(R.drawable.gender_male);  
        addTravel.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	Intent intent = new Intent(UserInfoActivity.this, AddTravelActivity.class);
		    	startActivity(intent);
		    }  
		});  
        myTravel.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	getMyTravel();
		    }  
		});  
        draftbox.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	getDraftbox();
		    }  
		});  
        searchTravel.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	Intent intent = new Intent(UserInfoActivity.this, SearchTravelActivity.class);
		    	startActivity(intent);
		    }  
		});  
	}
	
	private void getUserImage(String url)
	{
		AsyncHttpHelper.get(url, new BinaryHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] binaryData) 
			{		
				Log.i("onSuccess:" + statusCode, " binaryData:" + binaryData.length);
				UserInfoActivity.this.updateImage(binaryData);
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] binaryData, Throwable error) 
			{
				Log.i("onFailure:" + error, " statusCode:" + statusCode);
			}
		});
	}
	
	public void updateImage(byte[] data) 
	{
		Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length);
		userPic.setImageBitmap(b);
	}
	
	private void getMyTravel()
	{
		
	}
	
	private void getDraftbox()
	{
		
	}
//	private void searchTravel()
//	{
//		
//	}
}
