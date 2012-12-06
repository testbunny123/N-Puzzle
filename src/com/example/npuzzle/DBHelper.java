package com.example.npuzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "s.db";
	private static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_CREATE_SCORES = 
			"CREATE TABLE scores ( " +
			"SCORE_ID integer primary key autoincrement, " +
			"PLAYER_ID integer, " +
			"SCORE integer" +
			");";

	private static final String DATABASE_CREATE_PLAYERS = "CREATE TABLE players " +
			"( PLAYER_ID integer primary key autoincrement, " +
			"HANDLE text" +
			");"; 
				
	public DBHelper( Context context )
	{
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);	
	}
	
	@Override
	public void onCreate( SQLiteDatabase database ) {
	    database.execSQL(DATABASE_CREATE_SCORES );
	    database.execSQL( DATABASE_CREATE_PLAYERS );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate( db );
	}

    public void addScore( Score score )
    {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put( "'PLAYER_ID'", 1 );
		values.put("'SCORE'", 23432);
		db.insert( "scores", null, values);
		
		db.close(); // Closing database connection
    }
}
