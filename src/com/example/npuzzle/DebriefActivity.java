package com.example.npuzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DebriefActivity extends Activity implements View.OnClickListener
{

    private int moves;
    private int total_score;
    private boolean maxed;
    private Score totalScore;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
	Bundle extras = getIntent( ).getExtras( );
	float time = extras.getLong( "time" ) / 1000f;
	maxed = extras.getBoolean( "maxed" );
	moves = extras.getInt( "moves" );
	totalScore = (Score) extras.getSerializable( "totalScore" );

	super.onCreate( savedInstanceState );
	setContentView( R.layout.activity_debrief );

	totalScore.increase( time, moves );

	// Wire up TextView for time
	TextView time_value_tv = (TextView) findViewById( R.id.time_value_tv );
	String time_formatted = String.format( "%.2f", time );
	time_value_tv.setText( time_formatted + " sec" );

	// Wire up TextView for moves used
	TextView moves_tv = (TextView) findViewById( R.id.moves_tv );
	moves_tv.setText( Integer.toString( moves ) );

	// Wire up TextView for score
	TextView score_tv = (TextView) findViewById( R.id.score_tv );
	score_tv.setText( totalScore.getScore( ) + " points" );

	// Wire up Next Stage button
	Button nextStage = (Button) findViewById( R.id.next_stage_btn );

	nextStage.setOnClickListener( this );
    }

    public void onClick( View v )
    {
	Intent intent = new Intent( (Context) v.getContext( ), Puzzle.class );
	intent.putExtra( "totalScore", totalScore );

	// If difficulty is maxed, then game is over
	if ( maxed )
	{
	    intent = new Intent( v.getContext( ), HiScoresActivity.class );
	    intent.putExtra( "totalScore", totalScore );
	}

	startActivity( intent );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
	getMenuInflater( ).inflate( R.menu.activity_debrief, menu );
	return true;
    }
}
