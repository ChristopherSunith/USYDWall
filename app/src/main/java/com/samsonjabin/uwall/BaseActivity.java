package com.samsonjabin.uwall;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public abstract class BaseActivity extends ActionBarActivity {



    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        if (toolbar != null)
            setSupportActionBar(toolbar);
        else
            System.out.println("NULL!");
    }

    protected abstract int getLayoutResource();

    protected Toolbar getToolbar(){
        return toolbar;
    }
}
