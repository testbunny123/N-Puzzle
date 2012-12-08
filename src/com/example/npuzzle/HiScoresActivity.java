package com.example.npuzzle;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

public class HiScoresActivity extends Activity
{

    private SQLiteDatabase db;
    private TextView players[] = new TextView[ 7 ];
    private TextView scores[] = new TextView[ 7 ];
    private Score totalScore;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
	super.onCreate( savedInstanceState );
	setContentView( R.layout.activity_hi_scores );
	
	Bundle extras = getIntent().getExtras( );
	totalScore = ( Score )extras.getSerializable( "totalScore" );

	initTextFields( );
	DBHelper dbhelper = new DBHelper( this );
	db = dbhelper.getWritableDatabase( );

	List<Score> scores_list = getScores( );

	writeScores( scores_list );
	checkPlayerScore( scores_list );
    }

    private void checkPlayerScore( List<Score> scores_list )
    {
	int minScore = scores_list.get( scores_list.size( ) - 1 ).getScore( );
	int playerScore = totalScore.getScore( );
	if ( playerScore > minScore )
	{
	    String query = "INSERT INTO scores (PLAYER_ID, SCORE) VALUES ( 3, " + playerScore + ");";
	    db.rawQuery( query, null );
	}
    }

    private void writeScores( List<Score> scores_list )
    {

	Score current;
	for ( int i = 0; i < scores_list.size( ); i++ )
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
	List<Score> ret = new ArrayList<Score>( );

	String query = "SELECT *" + "FROM scores, players "
		+ "where scores.PLAYER_ID=players.PLAYER_ID "
		+ "ORDER BY scores.SCORE DESC;";

	Cursor cursor = db.rawQuery( query, null );

	if ( cursor.moveToFirst( ) )
	{
	    do
	    {
		Score score = new Score( );
		score.setPlayer( cursor.getString( 4 ) );
		score.setScore( cursor.getInt( 2 ) );
		ret.add( score );
	    } while ( cursor.moveToNext( ) );

	}

	return ret;
    }
}
