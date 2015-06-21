package com.travel.views;

import java.sql.Date;

import com.bupttravel.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FootprintActivity extends Activity {
	private static int RESULT_LOAD_IMAGE = 3;
	private String desc;
	private String photoPath;
	private ImageView footPrintImageView;
	private double x;
	private double y;
	private String POI;
	private Date date;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.edit_footprint);  
        Button addFootprintImage = (Button) findViewById(R.id.add_image); 
        footPrintImageView = (ImageView) findViewById(R.id.add_footprint_image);
        final EditText descText = (EditText) findViewById(R.id.add_footprint_desc);
        Button saveFootprint = (Button) findViewById(R.id.save_footprint);  
          
        addFootprintImage.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	selectFootprintImage();
		    }  
		});
        saveFootprint.setOnClickListener(new View.OnClickListener() {  	      
		    @Override  
		    public void onClick(View v) {  
		    	desc = descText.getText().toString();
		    	
		    	if(validate())
		    	{
		    		Intent intent=new Intent();  
//	                intent.putExtra(KEY_USER_ID, et01.getText().toString());  
//	                intent.putExtra(KEY_USER_PASSWORD, et02.getText().toString());  
	                setResult(RESULT_OK, intent);  
	                finish(); 
		    	}
		    }  
		});             
    }  
	
	private boolean validate()
	{
//		Log.i("password", password);
//		Log.i("password_repeat", password_repeat);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(FootprintActivity.this); 
        dialog.setTitle("提示");//窗口名           
//        dialog.setMessage("账号不能为空！ ");  
        dialog.setPositiveButton("确定", null);  
		if(photoPath.equals("") && desc.equals(""))
		{
            dialog.setMessage("请上传图片或写下心情哦！ ");    
            dialog.show(); 
            return false;
		}
//		if(desc.equals(""))
//		{
//            dialog.setMessage("账号不能为空！ ");    
//            dialog.show(); 
//            return false;
//		}
		return true;
	}
	
	private void selectFootprintImage()
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
  
            footPrintImageView.setImageBitmap(BitmapFactory.decodeFile(photoPath)); 
        }
	}
}
