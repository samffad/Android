package com.travel.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bupttravel.R;
import com.travel.beans.UserVo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DraftBoxActivity extends Activity {
	private List<Map<String, Object>> draftboxList;
	private ListView draftboxListView;
	private UserVo user;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draftbox);
		
		draftboxListView = (ListView)findViewById(R.id.list_draftbox);
		//get user info
		user = (UserVo) getIntent().getSerializableExtra("user"); 
		//get draftbox list info from sharePreferance
		getDraftbox();
		
		SimpleAdapter myTravelAdapter = new SimpleAdapter(this, draftboxList, R.layout.travel_item,
				new String[] {"title", "date", "days", "author_name", "city", "" },
				new int[] {R.id.title_travel_list, R.id.date_travel_list, R.id.days_travel_list, R.id.author_travel_list, R.id.place_travel_list });
		draftboxListView.setAdapter(myTravelAdapter);
		//TODO change background
		
		draftboxListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO get travel details
				Log.i("click position:", String.valueOf(position));
				Map<String, Object> clickedItemMap = (HashMap<String, Object>) draftboxListView.getItemAtPosition(position);
				if(clickedItemMap != null)
				{
//					ArrayList<Map<String, Object>> myTravelDetail = getTravelDetail((integer)clickedItemMap.get("tid"), (integer)clickedItemMap.get("author"));
					Intent intent = new Intent(DraftBoxActivity.this, FootprintActivity.class);
					Bundle bundle = new Bundle();  
					bundle.putSerializable("tid", (Integer)clickedItemMap.get("tid")); 
					bundle.putSerializable("author_id", (Integer)clickedItemMap.get("author_id"));
					intent.putExtras(bundle);  
			        startActivity(intent);
				}
			}
		});
	}
	
	private void getDraftbox()
	{
		
	}
}
