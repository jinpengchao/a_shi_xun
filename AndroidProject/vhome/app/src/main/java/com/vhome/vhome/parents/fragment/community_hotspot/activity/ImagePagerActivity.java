package com.vhome.vhome.parents.fragment.community_hotspot.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.vhome.chat.R;
import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.fragment.community_hotspot.HackyViewPager;
import com.vhome.vhome.parents.fragment.fragment.ImageDetailFragment;

import java.util.ArrayList;

public class ImagePagerActivity extends AppCompatActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pa);

        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(
                EXTRA_IMAGE_URLS);

        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);

//        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
//                .getAdapter().getCount());
//        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
//                CharSequence text = getString(R.string.viewpager_indicator,
//                        arg0 + 1, mPager.getAdapter().getCount());
//                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }

    }

}
