package com.example.npuzzle;

import java.util.Date;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class Puzzle extends Activity
{
    private static final int DIRECTIONS = 4;

    private PuzzleView puzzleView;

    private int tiles[][];
    private int empty_i;
    private int empty_j;
    private int N;
    private Score totalScore;

    private Difficulty difficulty;

    private int moves;
    private long startTime, endTime;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
	super.onCreate( savedInstanceState );

	LayoutInflater inflater = LayoutInflater.from( Puzzle.this );
	View game_layout = inflater.inflate( R.layout.game_layout, null );
	LinearLayout game_area = (LinearLayout) game_layout
		.findViewById( R.id.game_area );

	Bundle extras = getIntent( ).getExtras( );
	totalScore =  (Score) extras.getSerializable( "totalScore" ) ;

	puzzleView = new PuzzleView( this );
	game_area.addView( puzzleView );

	setContentView( game_layout );

	moves = 0;
	N = 3;
	difficulty = Difficulty.START;
	initTiles( );
	shuffleTiles( difficulty );

	startTime = new Date( ).getTime( );
    }

    public void shuffleTiles( Difficulty difficulty )
    {
	int shuffles = difficulty.getShuffles( );
	int dir;

	for ( int i = 0; i < shuffles; i++ )
	{
	    dir = (int) ( DIRECTIONS * Math.random( ) );
	    switch ( dir )
	    {
	    case 0:
		attemptMove( empty_i + 1, empty_j );
		break;
	    case 1:
		attemptMove( empty_i - 1, empty_j );
		break;
	    case 2:
		attemptMove( empty_i, empty_j + 1 );
		break;
	    case 3:
		attemptMove( empty_i, empty_j - 1 );
		break;
	    }
	}
    }

    private void initTiles( )
    {
	tiles = new int[ N ][ N ];

	for ( int i = 0; i < N; i++ )
	    for ( int j = 0; j < N; j++ )
		tiles[ i ][ j ] = i * N + j + 1;

	tiles[ N - 1 ][ N - 1 ] = 0;
	empty_i = empty_j = N - 1;
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
	//getMenuInflater( ).inflate( R.menu.activity_main, menu );
	return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
	/*
	switch ( item.getItemId( ) )
	{
	case R.id.quit_game:

	    break;
	}
	*/
	return true;
    }

    public int tileAt( int i, int j )
    {
	return tiles[ i ][ j ];
    }

    public boolean attemptMove( int i, int j )
    {
	if ( possibleMove( i, j ) )
	{
	    tiles[ empty_i ][ empty_j ] = tiles[ i ][ j ];
	    tiles[ i ][ j ] = 0;
	    empty_i = i;
	    empty_j = j;
	    return true;
	}
	return false;
    }

    private boolean possibleMove( int i, int j )
    {
	return inBounds( i, j ) && spaceAvailable( i, j );

    }

    private boolean spaceAvailable( int i, int j )
    {
	return Math.abs( i - empty_i ) + Math.abs( j - empty_j ) == 1;
    }

    private boolean inBounds( int i, int j )
    {
	return i < N && i >= 0 && j < N && j >= 0;
    }

    public boolean isGoal( )
    {
	for ( int i = 0; i < N; i++ )
	    for ( int j = 0; j < N; j++ )
	    {
		if ( i * N + j + 1 != tileAt( i, j ) )
		{
		    if ( i == getEmptyI( ) && j == getEmptyJ( ) )
			continue;

		    return false;
		}
	    }

	endTime = new Date( ).getTime( );

	return true;
    }

    public void incrementMoves( )
    {
	moves++;
    }

    public int getEmptyJ( )
    {
	return empty_j;
    }

    public int getEmptyI( )
    {
	return empty_i;
    }

    public long getTime( )
    {
	return endTime - startTime;
    }

    public int getMoves( )
    {
	return moves;
    }

    public boolean increaseDifficulty( )
    {
	return difficulty.next( );
    }

    public Score getTotalScore( )
    {
	return totalScore;
    }

}