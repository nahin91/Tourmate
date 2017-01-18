package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tbz.practice.tourmateexample1.Fragments.Fragment1;
import com.tbz.practice.tourmateexample1.Fragments.Fragment2;

public class WeatherActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView showIconIv;
    MaterialSearchView searchView;

    String city="london";
    CustomPagerAdapter pagerAdapter;

    public static final String BASE_URL = "http://dotnet.nerdcastlebd.com/";
    public static final String BASE_URL_OF_WEATHER = "http://api.openweathermap.org/";
    public static final String BASE_URL_OF_ICON= "http://openweathermap.org/img/w/";
    public static final String _PNG= ".png";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showIconIv= (ImageView) findViewById(R.id.weatherIcon);
        viewPager = (ViewPager) findViewById(R.id.vpPager);
        pagerAdapter=new CustomPagerAdapter(this,getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        //TODO: this is the custom toolbar for search
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weather");
        toolbar.setTitleTextColor(Color.parseColor("#fcfcfc"));

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_SHORT).show();
                city=query;

                Bundle b = new Bundle();
                b.putString("city",query);
                Fragment1 fragment1 = new Fragment1();
                fragment1.setArguments(b);
                pagerAdapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    //Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();

                }
                else{
                }
                return true;
            }
        });

        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);



        return  true;

    }

    public class CustomPagerAdapter extends FragmentStatePagerAdapter {

        private  String[] fragments={"Current","Forecast"};
        private Context context;

        public CustomPagerAdapter( Context context, FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Fragment1 frg = new Fragment1();
                    Bundle bundle = new Bundle();
                    bundle.putString("city",city);
                    frg.setArguments(bundle);
                    pagerAdapter.notifyDataSetChanged();

                    return frg;

                case 1:
                    Fragment2 frg2 = new Fragment2();
                    Bundle bun = new Bundle();
                    bun.putString("city",city);
                    frg2.setArguments(bun);
                    pagerAdapter.notifyDataSetChanged();

                    return frg2;

                default:
                    return  null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }
}
