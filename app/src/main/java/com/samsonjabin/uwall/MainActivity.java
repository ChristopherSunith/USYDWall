package com.samsonjabin.uwall;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.samsonjabin.uwall.NavigationDrawer.NavigationDrawerCallbacks;
import com.samsonjabin.uwall.NavigationDrawer.NavigationDrawerFragment;
import com.samsonjabin.uwall.fragments.ArticlesFragment;
import com.samsonjabin.uwall.fragments.EventsFragment;
import com.samsonjabin.uwall.fragments.NewsFragment;
import com.samsonjabin.uwall.fragments.PostFragment;
import com.samsonjabin.uwall.newupload.NewArticle;
import com.samsonjabin.uwall.newupload.NewEvents;
import com.samsonjabin.uwall.newupload.NewNews;
import com.samsonjabin.uwall.newupload.NewPost;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private String uname;
    public String pcu;
    public String admin = "Samson Jabin D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uname = ParseUser.getCurrentUser().get("name").toString();
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(uname, BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

    }
    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    public void postNew(MenuItem item){
        startActivity(new Intent(this, NewNews.class));
    }
    public void PostEvent(MenuItem item){
        startActivity(new Intent(this, NewEvents.class));
    }
    public void PostArticle (MenuItem item)
    {
        startActivity(new Intent(this, NewArticle.class));
    }
    public void NewPost (MenuItem item)
    {
        startActivity(new Intent(this, NewPost.class));
    }

    //@Override
    /**public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            if (pcu.equals(admin)){
            getMenuInflater().inflate(R.menu.menu_main, menu);}
            else {
                getMenuInflater().inflate(R.menu.menu_user, menu);
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateDp(View view){
        startActivity(new Intent(MainActivity.this,Dp.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                fragment = new NewsFragment();
                ft.replace(R.id.container, fragment);
                ft.commitAllowingStateLoss();
                break;
            case 1:
                fragment = new PostFragment();
                ft.replace(R.id.container, fragment);
                ft.commitAllowingStateLoss();
                //startActivity(new Intent(MainActivity.this,HelpoutActivity.class));
                break;
            case 2:
                fragment = new EventsFragment();
                ft.replace(R.id.container, fragment);
                ft.commitAllowingStateLoss();
                break;
            case 3:
                fragment = new ArticlesFragment();
                ft.replace(R.id.container, fragment);
                ft.commitAllowingStateLoss();
                break;
            case 4:
                ParseUser.logOut();
                startActivity(new Intent(MainActivity.this,DispatchActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
        }



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
