package com.travel.views;

import com.bupttravel.R;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
//import com.travel.beans.User;
import com.travel.beans.UserVo;
import com.travel.db.DBManager;
import com.travel.net.AsyncHttpHelper;
import com.travel.util.DeviceInfoUtil;
import com.travel.util.URLUtil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity{
	private static int RESULT_LOAD_IMAGE = 1;
	
	private UserVo user;
	private String photoPath;
	private TextView userName;
	private ImageView userPic;
	private ImageView userGender;
	private ImageButton changeUserImage;
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
		changeUserImage = (ImageButton) findViewById(R.id.changeUserImage_UserInfo);
		addTravel = (Button) findViewById(R.id.addTravel_UserInfo);
		myTravel = (Button) findViewById(R.id.myTravel_UserInfo);
		draftbox = (Button) findViewById(R.id.draftbox_UserInfo);
		searchTravel = (Button) findViewById(R.id.searchTravel_UserInfo);
		
        user = (UserVo) getIntent().getSerializableExtra("user");        
        if(user !=null)
        {
        	userName.setText(user.getName());
        	if(user.getPhotoPath() != null)
        		getUserImage(user.getPhotoPath());
            if(user.getGender().equals("F"))
            	userGender.setImageResource(R.drawable.gender_female);
            else
            	userGender.setImageResource(R.drawable.gender_male); 
        }
        changeUserImage.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				selectUserImage();
				uploadUserImage();
			}
		});
        addTravel.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	Intent intent = new Intent(UserInfoActivity.this, AddTravelActivity.class);
		    	Bundle bundle = new Bundle();  
				bundle.putSerializable("user", UserInfoActivity.this.user); 
				intent.putExtras(bundle);  
		    	startActivity(intent);
		    }  
		});  
        myTravel.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	Intent intent = new Intent(UserInfoActivity.this, MyTravelListActivity.class);
				Bundle bundle = new Bundle();  
				bundle.putSerializable("user", UserInfoActivity.this.user); 
				intent.putExtras(bundle);  
		        startActivity(intent);
		    }  
		});  
        draftbox.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	Intent intent = new Intent(UserInfoActivity.this, MyTravelListActivity.class);
				Bundle bundle = new Bundle();  
				bundle.putSerializable("user", UserInfoActivity.this.user); 
				intent.putExtras(bundle);  
		        startActivity(intent);
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
	
	private void selectUserImage()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
        super.onActivityResult(requestCode, resultCode, data);
  
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) 
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
  
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
  
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            photoPath = cursor.getString(columnIndex);
            cursor.close();
  
            userPic.setImageBitmap(BitmapFactory.decodeFile(photoPath));
            user.setPhotoPath(photoPath);           
        }
  
    }
	
	public void uploadUserImage() 
	{
		if(user.getPhotoPath() != null)
		{
			//update db info
			DBManager manager = DBManager.getInstance(UserInfoActivity.this);
			long index = manager.addUser(user);
			if (index >= 0) 
			{
				Log.i("userimage", user.getPhotoPath());
			}
			
			//upload to backend
			
		}
		
	}
	
	public void updateImage(byte[] data) 
	{
		Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length);
		userPic.setImageBitmap(b);
	}
	
//	private void searchTravel()
//	{
//		
//	}
}
