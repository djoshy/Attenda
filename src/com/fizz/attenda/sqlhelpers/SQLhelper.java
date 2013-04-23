package com.fizz.attenda.sqlhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLhelper extends SQLiteOpenHelper {
		private static final int SCHEMA=1;
		public static final String NAME="name";
		public static final String CREDITS="credits";
		public static final String LAB="lab";
	  public static final String DATABASE_NAME = "subjects";
	  private static final int DATABASE_VERSION = 1;

	  
	public SQLhelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
		db.beginTransaction();
		db.execSQL("CREATE TABLE SUBJECTS (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, credits REAL, lab TEXT);");
		ContentValues cv=new ContentValues();
		
		db.setTransactionSuccessful();
		}
		finally{
			db.endTransaction();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
