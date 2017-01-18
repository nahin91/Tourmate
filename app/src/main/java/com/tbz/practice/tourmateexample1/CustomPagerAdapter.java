package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tbz.practice.tourmateexample1.Fragments.Fragment1;
import com.tbz.practice.tourmateexample1.Fragments.Fragment2;

/**
 * Created by USER on 03-Jan-17.
 */

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
                //Fragment1 fragment1 = new Fragment1();
                    /*b.putSerializable("response",weatherResponse);
                    fragment1.setArguments(b);
                    Log.e("weather", "bundle size: "+b );*/
                // fragment1.getWeatherResponse(weatherResponse);
                return new Fragment1();
            case 1:
                return new Fragment2();
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
