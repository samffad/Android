package com.travel.db;

import com.travel.beans.User;

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
	
	public long addUser(User user)
	{
		long in = 0;
		if(getUserForDB(user.getId()) != null)
			in = deleteUserById(user.getId());
		
		if(in <0)
			return -1;
		
		ContentValues userValues = new ContentValues();
		userValues.put("id", user.getId());
		userValues.put("name", user.getName());
		userValues.put("photoPath", user.getPhotoPath());
		userValues.put("gender", user.getGender());
		return db.insert(DBController.login_tabled, null, userValues);
	}
	
	public User getUserForDB(Integer uid)
	{
		User user = null;
		Cursor c = db.query(DBController.login_tabled, null, "id=?", new String[] {  uid.toString() }, null, null, null, null);
		if(c.moveToNext())
		{
			user = new User();
			user.setId(c.getInt(c.getColumnIndex("id")));
			user.setName(c.getString(c.getColumnIndex("name")));
			user.setPhotoPath(c.getString(c.getColumnIndex("photoPath")));
			user.setGender(c.getString(c.getColumnIndex("gender")));
		}
		c.close();
		return user;
	}
	
	public long deleteUserById(Integer uid) {
		return db.delete(DBController.login_tabled, "id=?", new String[] { uid.toString() });
	}
	
	public String getUserName(Integer uid)
	{
		String username = null;
		Cursor c = db.query(DBController.login_tabled,  new String[] { "name" }, "id=?", new String[] { uid.toString() }, null, null, null, null);
		if(c.moveToNext())
		{
			username = c.getString(c.getColumnIndex("name"));
			System.out.println("username is:" + username);
		}
		c.close();
		return username;
	}
}
