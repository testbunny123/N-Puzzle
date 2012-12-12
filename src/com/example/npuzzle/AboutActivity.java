package com.example.npuzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class AboutActivity extends Activity
{

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
	super.onCreate( savedInstanceState );
	setContentView( R.layout.activity_about );

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

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
	getMenuInflater( ).inflate( R.menu.activity_about, menu );
	return true;
    }
}
