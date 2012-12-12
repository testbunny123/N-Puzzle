package com.example.npuzzle;

import android.app.AlertDialog.Builder;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HiScoresActivity extends Activity
{
    private Cursor cursor;
    private SQLiteDatabase db;
    private TextView players[] = new TextView[ 7 ];
    private TextView scores[] = new TextView[ 7 ];
    private Score totalScore;
    private DBHelper dbhelper;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
	super.onCreate( savedInstanceState );
	setContentView( R.layout.activity_hi_scores );

	Bundle extras = getIntent( ).getExtras( );
	totalScore = (Score) extras.getSerializable( "totalScore" );

	initTextFields( );
	dbhelper = new DBHelper( this );

	// Check if score is high score
	List<Score> scores_list = getScores( );
	checkPlayerScore( scores_list );

	// Update score list again in case. Write scores to the screen.
	List<Score> updated = getScores( );
	writeScores( updated );

	Button back_button = (Button) findViewById( R.id.back_button );
	back_button.setOnTouchListener( new OnTouchListener( )
	{

	    public boolean onTouch( View v, MotionEvent event )
	    {
		Intent intent = new Intent( v.getContext( ),
			TitleActivity.class );
		startActivity( intent );
		return false;
	    }

	} );
    }

    private void checkPlayerScore( List<Score> scores_list )
    {
	int minScore = scores_list.get( scores_list.size( ) - 1 ).getScore( );
	int playerScore = totalScore.getScore( );
	if ( playerScore > minScore )
	{
	    final String handle;
	    AlertDialog.Builder alert = new AlertDialog.Builder( this );
	    alert.setTitle( "New high score!" );
	    alert.setMessage( "Enter your name" );
	    final EditText input = new EditText( this );
	    alert.setView( input );

	    alert.setPositiveButton( "Ok",
		    new DialogInterface.OnClickListener( )
		    {
			public void onClick( DialogInterface dialog,
				int whichButton )
			{
			    String name = input.getText( ).toString( );
			    dbhelper.addPlayer( name );
			    dbhelper.addScore( totalScore,
				    dbhelper.getPid( name ) );
			}
		    } );

	    alert.show( );

	}
    }

    private void writeScores( List<Score> scores_list )
    {

	Score current;
	for ( int i = 0; i < Math.min( players.length, scores_list.size( ) ); i++ )
	{
	    current = scores_list.get( i );
	    players[ i ].setText( current.getPlayer( ) );
	    scores[ i ].setText( Integer.toString( current.getScore( ) ) );
	}
    }

    private void initTextFields( )
    {
	players[ 0 ] = (TextView) findViewById( R.id.player1 );
	players[ 1 ] = (TextView) findViewById( R.id.player2 );
	players[ 2 ] = (TextView) findViewById( R.id.player3 );
	players[ 3 ] = (TextView) findViewById( R.id.player4 );
	players[ 4 ] = (TextView) findViewById( R.id.player5 );
	players[ 5 ] = (TextView) findViewById( R.id.player6 );
	players[ 6 ] = (TextView) findViewById( R.id.player7 );

	scores[ 0 ] = (TextView) findViewById( R.id.score1 );
	scores[ 1 ] = (TextView) findViewById( R.id.score2 );
	scores[ 2 ] = (TextView) findViewById( R.id.score3 );
	scores[ 3 ] = (TextView) findViewById( R.id.score4 );
	scores[ 4 ] = (TextView) findViewById( R.id.score5 );
	scores[ 5 ] = (TextView) findViewById( R.id.score6 );
	scores[ 6 ] = (TextView) findViewById( R.id.score7 );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
	getMenuInflater( ).inflate( R.menu.activity_hi_scores, menu );
	return true;
    }

    public List<Score> getScores( )
    {
	db = dbhelper.getWritableDatabase( );

	List<Score> ret = new ArrayList<Score>( );

	String query = "SELECT *" + "FROM scores, players "
		+ "where scores.PLAYER_ID=players.PLAYER_ID "
		+ "ORDER BY scores.SCORE DESC;";

	cursor = db.rawQuery( query, null );

	if ( cursor.moveToFirst( ) )
	{
	    int i = 0;
	    do
	    {
		Score score = new Score( );
		score.setPlayer( cursor.getString( 4 ) );
		score.setScore( cursor.getInt( 2 ) );
		ret.add( score );
		i++;
	    } while ( cursor.moveToNext( ) && i < 7 );

	}

	db.close( );

	return ret;
    }
}
