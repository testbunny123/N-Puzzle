package com.example.npuzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends Activity
{

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
	super.onCreate( savedInstanceState );
	setContentView( R.layout.activity_title );

	Button play_button = (Button) findViewById( R.id.play_button );
	Button scores_button = (Button) findViewById( R.id.scores_button );
	Button about_button = (Button) findViewById( R.id.about_button );

	scores_button.setOnClickListener( new View.OnClickListener( )
	{

	    public void onClick( View v )
	    {
		Intent intent = new Intent( v.getContext( ),
			HiScoresActivity.class );
		Score totalScore = new Score( );
		intent.putExtra( "totalScore", totalScore );
		startActivity( intent );
	    }
	} );

	about_button.setOnClickListener( new View.OnClickListener( )
	{
	    public void onClick( View v )
	    {
		Intent intent = new Intent( v.getContext( ),
			AboutActivity.class );
		startActivity( intent );
	    }
	} );

	play_button.setOnClickListener( new View.OnClickListener( )
	{
	    public void onClick( View v )
	    {
		Intent intent = new Intent( v.getContext( ), Puzzle.class );
		Score totalScore = new Score( );
		intent.putExtra( "totalScore", totalScore );
		startActivity( intent );
	    }
	} );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
	getMenuInflater( ).inflate( R.menu.activity_title, menu );
	return true;
    }
}
