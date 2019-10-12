package com.OA.LuvOAPraise.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.OA.LuvOAPraise.Fragment.LyicsFragment;
import com.OA.LuvOAPraise.Fragment.SheetMusicFragment;
import com.OA.LuvOAPraise.R;

import me.relex.circleindicator.CircleIndicator;

public class ContentMainActivity extends AppCompatActivity {

    FragmentPagerAdapter fragmentPagerAdapter;
    public String strTitle;
    public String strLyics;
    public String strSongKey;
    public String strPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_main);

        Intent intent = getIntent();
        strTitle = intent.getExtras().getString("title");
        getSupportActionBar().setTitle(strTitle);

        strLyics = intent.getExtras().getString("lyics");
        strSongKey = intent.getExtras().getString("songkey");
        strPages = intent.getExtras().getString("pages");

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        fragmentPagerAdapter = new SongPagerAdapter(getSupportFragmentManager(), strLyics, strSongKey, strPages);
        viewPager.setAdapter(fragmentPagerAdapter);

        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

    }

    public static class SongPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        private String strLyics;
        private String strSongKey;
        private String strPages;

        public SongPagerAdapter(FragmentManager fragmentManager, String strLyics, String strSongKey, String strPages)
        {
            super(fragmentManager);
            this.strLyics = strLyics;
            this.strSongKey = strSongKey;
            this.strPages = strPages;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return LyicsFragment.newInstance(strLyics);
                case 1:
                    return SheetMusicFragment.newInstance(strSongKey, strPages);
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_settings).getActionView();
        return true;
    }
}
