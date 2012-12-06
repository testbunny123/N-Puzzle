package com.example.npuzzle;

import android.view.*;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.widget.Toast;

public class PuzzleView extends View implements Animation.AnimationListener {
	private Puzzle puzzle;
	
	private int width;
	private int height;
	private int tile_w;
	private int tile_h;
	private int N;
	
	Paint background, dark, light, foreground;
	public PuzzleView( Context context )
	{
		super( context );
		this.puzzle = ( Puzzle ) context;
		this.N = 3;
		
		background = new Paint();
		background.setColor(getResources().getColor(
	            R.color.background));
		
		dark = new Paint();
	    dark.setColor(getResources().getColor(R.color.puzzle_dark));

	    light = new Paint();
	    light.setColor(getResources().getColor(R.color.puzzle_light));
	    
      foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
	      foreground.setColor(getResources().getColor(
	            R.color.puzzle_foreground));
	      foreground.setStyle(Style.FILL);
	}
	
	@Override
	public void onDraw( Canvas canvas )
	{
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	      
	    for ( int i = 1; i < N; i++ )
	    {
	    	canvas.drawLine( 0, i * tile_h, width, i * tile_h, dark); // Draw horizontal lines
	    	canvas.drawLine( i * tile_w, 0, i * tile_w, height, dark);
	    }
	    
	      // Draw the numbers...
	      foreground.setTextSize(tile_h * 0.75f);
	      foreground.setTextScaleX( tile_w / tile_h );
	      foreground.setTextAlign(Paint.Align.CENTER);

	      // Draw the number in the center of the tile
	      FontMetrics fm = foreground.getFontMetrics();
	      // Centering in X: use alignment (and X at midpoint)
	      float x = tile_w / 2;
	      // Centering in Y: measure ascent/descent first
	      float y = tile_h / 2 - (fm.ascent + fm.descent) / 2;
	      
	      int tile;
	      for (int i = 0; i < N; i++ ) {
	         for (int j = 0; j < N; j++ ) {
	        	 tile = puzzle.tileAt( i, j );
	        	 if ( tile != 0 )
	        	 {
	        		 float curj = j * tile_w + x;
	        	     float curi = i * tile_h + y;
	        		 canvas.drawText( Integer.toString( tile ), curj, curi, foreground);
	        	 }
	         }
	      }		
	}
	
	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh )
	{
		super.onSizeChanged( w, h, oldw, oldh );
		this.width = w;
		this.height = h;
		this.tile_w = w / N;
		this.tile_h = h / N;
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent event )
	{
		super.onTouchEvent( event );
		int i = ( int ) event.getY() / tile_h;
		int j = ( int ) event.getX() / tile_w;
		
		if ( !puzzle.attemptMove( i, j) )
			this.startAnimation(AnimationUtils.loadAnimation( puzzle, R.anim.shake) ) ;
		else
			puzzle.incrementMoves();
		
		invalidate();
		
		if ( puzzle.isGoal() )
		{
			//Toast toast = Toast.makeText( ( Context ) puzzle, "Win.", Toast.LENGTH_SHORT);
			//toast.show();
			Animation fade_out = AnimationUtils.loadAnimation( ( Context ) puzzle, android.R.anim.fade_out );
			fade_out.setDuration( 2200 );
			fade_out.setAnimationListener( this );
			View parent = ( View ) this.getParent();
			parent.startAnimation( fade_out );
			parent.setVisibility( View.INVISIBLE );
		}
		
		return false;
	}
	

	public void onAnimationEnd(Animation animation) {
		
		Intent intent = new Intent( ( Context ) puzzle, DebriefActivity.class );
		intent.putExtra( "time", puzzle.getTime() );
		intent.putExtra( "moves", puzzle.getMoves() );
		
		// When max level achieved, game is over
		boolean maxed = puzzle.increaseDifficulty();
		intent.putExtra("maxed", maxed );
		puzzle.startActivity( intent );
	}

	public void onAnimationRepeat(Animation animation) { }

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
}
