package com.example.npuzzle;

public enum Difficulty
{
	START(8);
	
	// Amount of shuffles added on each difficulty increase
	private static final int INC = 2;
	private static final int LEVELS = 2;
	public static final int MAX_DIFFICULTY = INC * LEVELS;
	
	private int shuffles;
	
	private Difficulty( int shuffles )
	{
		this.shuffles = shuffles;
	}
	
	public int getShuffles() 
	{
		return shuffles;
	}
	
	// Increments difficulty. Returns true if max difficulty is achieved.
	public boolean next()
	{
		if ( shuffles < 8 + MAX_DIFFICULTY )
		{
			shuffles += INC;
			return false;
		}
		
		return true;
	}
	
};