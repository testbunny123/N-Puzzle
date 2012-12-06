package com.example.npuzzle;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

public class HiScoresActivity extends Activity {
	
	DBHelper dbhelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_scores);
        dbhelper = new DBHelper( this );
        
        List<Score> scores = getScores();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hi_scores, menu);
        return true;
    }
    
    
    public List<Score> getScores()
    {
        dbhelper = new DBHelper( this );
        SQLiteDatabase db = dbhelper.getWritableDatabase();
       
        
        String query = "SELECT *" +
        		"FROM scores, players where scores.PLAYER_ID=players.PLAYER_ID;";
        
        Cursor cursor = db.rawQuery( query, null );
        cursor.moveToFirst();
        int foo = cursor.getCount();
        return null;
    }
}
