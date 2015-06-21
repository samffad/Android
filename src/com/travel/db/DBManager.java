package com.travel.db;
import com.travel.beans.UserVo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static DBManager instance;
	private DBController dbController;
	private SQLiteDatabase db;
	
	public static DBManager getInstance(Context context)
	{
		if(instance == null){
			instance = new DBManager(context.getApplicationContext());
		}
		return instance;
	}
	
	private DBManager(Context context)
	{
		dbController = new DBController(context);
		db = dbController.getWritableDatabase();
	}
	
	public long addUser(UserVo user)
	{
		long in = 0;
		if(getUserForDB(user.getUid()) != null)
			in = deleteUserByUid(user.getUid());
		
		if(in <0)
			return -1;
		
		ContentValues userValues = new ContentValues();
		userValues.put("uid", user.getUid());
		userValues.put("name", user.getName());
		userValues.put("photoPath", user.getPhotoPath());
		userValues.put("gender", user.getGender());
		return db.insert(DBController.login_tabled, null, userValues);
	}
	
	public UserVo getUserForDB(Integer uid)
	{
		UserVo user = null;
		Cursor c = db.query(DBController.login_tabled, null, "uid=?", new String[] {  uid.toString() }, null, null, null, null);
		if(c.moveToNext())
		{
			user = new UserVo();
			user.setUid(c.getInt(c.getColumnIndex("uid")));
			user.setName(c.getString(c.getColumnIndex("name")));
			user.setPhotoPath(c.getString(c.getColumnIndex("photoPath")));
			user.setGender(c.getString(c.getColumnIndex("gender")));
		}
		c.close();
		return user;
	}
	
	public long deleteUserByUid(Integer uid) {
		return db.delete(DBController.login_tabled, "uid=?", new String[] { uid.toString() });
	}
	
	public String getUserName(Integer uid)
	{
		String username = null;
		Cursor c = db.query(DBController.login_tabled,  new String[] { "name" }, "uid=?", new String[] { uid.toString() }, null, null, null, null);
		if(c.moveToNext())
		{
			username = c.getString(c.getColumnIndex("name"));
			System.out.println("username is:" + username);
		}
		c.close();
		return username;
	}
}
