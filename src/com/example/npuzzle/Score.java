package com.example.npuzzle;

import java.io.Serializable;

public class Score implements Serializable
{
    private int score;
    private String player;

    public Score()
    {
	score = 0;
	player = "Guest";
    }

    public String getPlayer( )
    {
	return player;
    }

    public void setPlayer( String player )
    {
	this.player = player;
    }

    public int getScore( )
    {
	return score;
    }

    public void setScore( int score )
    {
	this.score = score;
    }

    public void increase( float time, int moves )
    {
	score += 8200 / ( (int) time + moves );
    }
}
