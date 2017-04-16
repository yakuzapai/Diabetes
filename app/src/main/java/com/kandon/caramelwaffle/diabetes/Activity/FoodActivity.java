package com.kandon.caramelwaffle.diabetes.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kandon.caramelwaffle.diabetes.Fragment.DessertFragment;
import com.kandon.caramelwaffle.diabetes.Fragment.DrinkFragment;
import com.kandon.caramelwaffle.diabetes.Fragment.FoodFragment;
import com.kandon.caramelwaffle.diabetes.Fragment.FruitFragment;
import com.kandon.caramelwaffle.diabetes.R;

public class FoodActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        pager = (ViewPager)findViewById(R.id.pager);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("พลังงานในอาหาร");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager);


    }
    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FoodFragment();
                case 1:
                    return new DrinkFragment();
                case 2:
                    return new DessertFragment();
                case 3:
                default:
                    return new FruitFragment();

            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "อาหาร";
                case 1:
                    return "เครื่องดื่ม";
                case 2:
                    return "ขนม";
                case 3:
                    default:
                        return "ผลไม้";

            }
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
