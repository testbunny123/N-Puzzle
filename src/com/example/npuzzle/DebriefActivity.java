package com.example.npuzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DebriefActivity extends Activity implements View.OnClickListener {
	
	private int moves;
	private boolean maxed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        float time = extras.getLong( "time" ) / 1000f;        
        maxed = extras.getBoolean( "maxed" ); 
        moves = extras.getInt( "moves" );
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debrief);
        
        // Wire up TextView for time
        TextView time_value_tv = ( TextView ) findViewById( R.id.time_value_tv );
        String time_formatted = String.format( "%.2f", time );
        time_value_tv.setText( time_formatted + " sec" );
        
        // Wire up TextView for moves used
        TextView moves_tv = ( TextView ) findViewById( R.id.moves_tv );
        moves_tv.setText( Integer.toString( moves ) );
        
        // Wire up TextView for score
        int score = 8200 / ( ( int ) time + moves );
        TextView score_tv = ( TextView ) findViewById( R.id.score_tv );
        score_tv.setText( Integer.toString( score ) + " points" );
        
        // Wire up Next Stage button
        Button nextStage = ( Button ) findViewById( R.id.next_stage_btn );
        
        	
        nextStage.setOnClickListener( this );
    }
        	
	public void onClick(View v) {
		Intent intent = new Intent( ( Context ) v.getContext(), Puzzle.class );
		
        if ( maxed )
        	intent = new Intent( v.getContext(), HiScoresActivity.class );
		startActivity( intent );
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_debrief, menu);
        return true;
    }
}
