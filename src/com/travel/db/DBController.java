package com.travel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "travel";
	private static final int DATABASE_VERSION = 1;
	public static final String login_tabled = "login_table";
	private static String login_table = "CREATE TABLE IF NOT EXISTS "
			+ login_tabled + "" + "(id INTEGER PRIMARY KEY, " + "name VARCHAR(50), "
			+ "photoPath VARCHAR(100)," + "gender INTEGER)";
	
	public DBController(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(login_table);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}
}
