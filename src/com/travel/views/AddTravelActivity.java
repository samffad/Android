package com.travel.views;

import java.util.ArrayList;
import java.util.Map;
import java.util.zip.Inflater;

import com.bupttravel.R;
import com.travel.beans.Travel;
import com.travel.beans.UserVo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AddTravelActivity extends Activity {
	private static int RESULT_LOAD_IMAGE = 1;
	private static int RESULT_ADD_FOOTPRINT = 2;
	
	private ArrayList<Map<String, Object>> footprintList = new ArrayList<Map<String, Object> >();
	private ListView footprintListView;
	private Travel travel;
	private UserVo user;
//	private MyAdapter adapter;
	private SimpleAdapter adapter;
	private String backgroundPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_travel);
		user = (UserVo) getIntent().getSerializableExtra("user"); 
		final EditText titleText = (EditText) findViewById(R.id.title_addTravel);		
		Button save = (Button) findViewById(R.id.save_addTravel);  
		Button upload = (Button) findViewById(R.id.upload_addTravel); 
		Button addTravel = (Button) findViewById(R.id.addFootprint_addTravel); 
		Button addBackground = (Button) findViewById(R.id.addBackground_addTravel); 
		footprintListView = (ListView) findViewById(R.id.list_footprint);
		
		//TODO get draft info from sharePreferance
		//----
		//将草稿箱的东西导入footprintList
		//----

		adapter = new SimpleAdapter(this, footprintList, R.layout.footprint_item,
				new String[] {"image", "desc", "date", "loc" },
				new int[] {R.id.footprint_image, R.id.footprint_desc, R.id.footprint_date, R.id.footprint_loc });
		footprintListView.setAdapter(adapter); 
		
		save.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	travel.setTitle(titleText.getText().toString());
		    	travel.setAuthor_id(user.getUid());
		    	
		        if(validate())
		        {
		        	savePro();
		        }
		    }  
		});   
		upload.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	if(validate())
		        {
		        	uploadPro();
		        }
		    }
		}); 
		addTravel.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {
		    	//open a FootPrintActivity to edit a new footprint
		    	Intent intent = new Intent(AddTravelActivity.this, FootprintActivity.class);  
                //发送意图标示为REQUSET=1  
                startActivityForResult(intent, RESULT_ADD_FOOTPRINT);
//		    	adapter.notifyDataSetChanged(); 
		    }  
		}); 
		addBackground.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	selectBackgroundImage();
		    }  
		}); 
//        add = (Button) findViewById(R.id.add);  
//        adapter = new MyAdapter(this);  
//        listview.setAdapter(adapter);  
//          
//        add.setOnClickListener(new OnClickListener() {  
//            @Override  
//            public void onClick(View arg0) {  
//                // TODO Auto-generated method stub  
//                adapter.arr.add("");  
//                adapter.notifyDataSetChanged();  
//            }  
//        });  
		
	}
	
	private boolean validate()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(AddTravelActivity.this); 
        dialog.setTitle("提示");//窗口名           
        dialog.setPositiveButton("确定", null);  
		if(travel.getTitle().equals(""))
		{     
            dialog.setMessage("请填写游记标题！ ");  
            dialog.show(); 
            return false;
		}
//		if(password.equals(""))
//		{   
//            dialog.setMessage("密码不能为空！ ");   
//            dialog.show(); 
//            return false;
//		}
		return true;
	}
	
	private void savePro()
	{
		//TODO save to sharePreferance
	}
	
	private void uploadPro()
	{
		//TODO upload to server
	}
	
	private void selectBackgroundImage()
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
            backgroundPath = cursor.getString(columnIndex);
            cursor.close();
  
            Bitmap bitmap = BitmapFactory.decodeFile(backgroundPath);
            View summaryView = (View) findViewById(R.id.summary_addTravel);
            BitmapDrawable iconDrawable = new BitmapDrawable(bitmap);
            summaryView.setBackgroundDrawable(iconDrawable);
            
//            mBackGround  = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.background)).getBitmap(); //获取背景图片
//            userPic.setImageBitmap(BitmapFactory.decodeFile(backgroundPath));
//            user.setPhotoPath(photoPath);           
        }
  
        if (requestCode == RESULT_ADD_FOOTPRINT && resultCode == RESULT_OK && null != data) 
        {
                     
        }
    }
}
