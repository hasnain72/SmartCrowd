package com.example.zafar.sartcrowd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.Database.Database;
import com.example.zafar.sartcrowd.Model.Cart;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.SharedPreference.SessionManager;
import com.example.zafar.sartcrowd.fragments.CheckoutFragment;
import com.example.zafar.sartcrowd.fragments.HistoryFragment;
import com.example.zafar.sartcrowd.fragments.HomeFragment;
import com.example.zafar.sartcrowd.fragments.ProductFragment;
import com.example.zafar.sartcrowd.fragments.SettingFragment;
import com.example.zafar.sartcrowd.fragments.WalletFragment;
import com.example.zafar.sartcrowd.other.GPSTracker;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,HomeFragment.OnFragmentInteractionListener
        ,SettingFragment.OnFragmentInteractionListener
        ,HistoryFragment.OnFragmentInteractionListener
        ,WalletFragment.OnFragmentInteractionListener{

        NavigationView navigationView;
        DrawerLayout drawer;
        GPSTracker gps;
        TextView counter;
        String token;

    // index to identify current nav menu item
        public static int navItemIndex = 0;

        // toolbar titles respected to selected nav menu item
        private String[] activityTitles;

        private static final String TAG_HOME = "home";
        private static final String TAG_HISTORY = "history";
        private static final String TAG_WALLET = "wallet";
        private static final String TAG_SETTINGS = "settings";
        public static String CURRENT_TAG = TAG_HOME;

        SessionManager session;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gps = new GPSTracker(getApplicationContext() , this);
        session = new SessionManager(getApplicationContext());
        if(!session.isLoggedIn()){
            session.checkLogin();
            finish();
        }else{

            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            counter = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_checkout));


            //This method will initialize the count value
            initializeCountDrawer();

            // Handle the camera action
            Fragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            selectNavMenu();
            setToolbarTitle();

            loadHomeFragment();

        }

    }


    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0080")));
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
                Fragment fragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
        }
//        Fragment fragment = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.frame, fragment);
//        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            navItemIndex=0;
            // Handle the camera action
            Fragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commitAllowingStateLoss();



            selectNavMenu();
            setToolbarTitle();
        } else if (id == R.id.nav_history) {
            navItemIndex=1;

            Fragment fragment = new HistoryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            selectNavMenu();
            setToolbarTitle();

        } else if (id == R.id.nav_wallet) {
            navItemIndex=2;

            Fragment fragment = new WalletFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            selectNavMenu();
            setToolbarTitle();

        }
        else if (id == R.id.nav_checkout) {
            navItemIndex=3;

            Fragment fragment = new CheckoutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            selectNavMenu();
            setToolbarTitle();

        }else if (id == R.id.nav_settings) {
//            Intent i = new Intent(this , SettingsActivity.class);
//            startActivity(i);
            navItemIndex=4;
            Fragment fragment = new SettingFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            selectNavMenu();
            // set toolbar title
            setToolbarTitle();

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_help) {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initializeCountDrawer(){

        //Gravity property aligns the text
        counter.setGravity(Gravity.CENTER_VERTICAL);
        counter.setTypeface(null, Typeface.BOLD);
        counter.setTextColor(getResources().getColor(R.color.colorAccent));

        List<Cart> cartValues = new ArrayList<>();
        cartValues = new Database(this).getCarts();
        counter.setText("" + cartValues.size());
    }

}
