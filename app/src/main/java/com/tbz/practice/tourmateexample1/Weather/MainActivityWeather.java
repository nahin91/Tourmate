package com.tbz.practice.tourmateexample1.Weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tbz.practice.tourmateexample1.Fragments.EventDetails;
import com.tbz.practice.tourmateexample1.Fragments.EventListFragment;
import com.tbz.practice.tourmateexample1.MainActivity;
import com.tbz.practice.tourmateexample1.R;


import retrofit2.Retrofit;

public class MainActivityWeather extends AppCompatActivity {

    CurrentActivity currentActivity = null;
    ForeCastActivity foreCastActivity=null;
    public Bundle b;
    private Retrofit retrofit;
    private EditText searchET;
    private static SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static String searchCity;
    static  String unit="metric";
    int userIdFromEvents;


    LocationClient locationClient;

    @Override
    protected void onPause() {
        super.onPause();
        LocationClient.googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocationClient.googleApiClient.connect();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_t);
        locationClient = new LocationClient(this);

        searchCity=locationClient.city;
       // Toast.makeText(this, searchCity, Toast.LENGTH_SHORT).show();


        currentActivity = new CurrentActivity();
        foreCastActivity =new ForeCastActivity();
        b = new Bundle();

        searchET = (EditText) findViewById(R.id.search_et);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        userIdFromEvents = getIntent().getExtras().getInt("userId");
        Toast.makeText(this, "userIdFromEvent="+userIdFromEvents, Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userid",userIdFromEvents);
            startActivity(intent);

            return true;
        }

        if (id == R.id.farenheit) {
           unitFar();
            return true;
        }
        if (id == R.id.celcius) {
            unitCel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void search_btn(View view) {
        searchCity = searchET.getText().toString();
        mSectionsPagerAdapter.notifyDataSetChanged();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public  void unitCel(){
        unit ="metric";

        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    public void unitFar(){
       unit ="imperial";


        mSectionsPagerAdapter.notifyDataSetChanged();
    }


    public static class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:

                   CurrentActivity currentActivity =new CurrentActivity();
                    //String searchCity=searchET.getText().toString();

                    Bundle b = new Bundle();
                    b.putString("city", searchCity);
                    b.putString("a",unit);

                    currentActivity.setArguments(b);
                    return currentActivity;
                case 1:
                    ForeCastActivity foreCastActivity = new ForeCastActivity();

                   Bundle c= new Bundle();
                    c.putString("city", searchCity);
                    c.putString("a",unit);

                    foreCastActivity.setArguments(c);

                    return foreCastActivity;

                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Current";
                case 1:
                    return "ForeCast";

            }
            return null;
        }
    }

}
