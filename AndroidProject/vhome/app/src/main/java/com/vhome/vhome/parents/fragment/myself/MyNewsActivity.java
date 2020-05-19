package com.vhome.vhome.parents.fragment.myself;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.vhome.chat.Constant;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.activity.NewPostActivity;
import com.vhome.vhome.parents.fragment.fragment.HotspotFragment;
import com.vhome.vhome.parents.fragment.fragment.HealthFragment;
import com.vhome.vhome.parents.fragment.fragment.AttentionFragment;
import com.vhome.vhome.parents.fragment.fragment.MessageFragment;
import com.vhome.vhome.parents.fragment.fragment.PrivateMessageFragment;
import com.vhome.vhome.parents.fragment.radio_ximalaya.RadioActivity;
import com.vhome.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;
import com.vhome.vhome.parents.fragment.radio_ximalaya.fragment.RecommendFragment;
import com.vhome.vhome.user.personal.fragment.MyFragmentPagerAdapter;
import com.vhome.vhome.user.personal.fragment.MyPostFragment;
import com.vhome.vhome.user.personal.fragment.dummy.TabEntity;
import com.vhome.vhome.user.personal.util.widget.NoScrollViewPager;
@SuppressLint("NewApi")
public class MyNewsActivity extends AppCompatActivity {

    protected static final String TAG = "MainActivity";
    // textview for unread message count
    private TextView unreadAddressLable1;
    // textview for unread event message
    private TextView unreadAddressLable2;
    private Button[] mTabs;
    private MessageFragment messageFragment;
    private PrivateMessageFragment adminMessageFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }
        setContentView(R.layout.activity_my_news);

        initView();
        if (savedInstanceState != null) {
            messageFragment = (MessageFragment) getSupportFragmentManager().getFragment(savedInstanceState, MessageFragment.class.getSimpleName());
            adminMessageFragment = (PrivateMessageFragment) getSupportFragmentManager().getFragment(savedInstanceState, PrivateMessageFragment.class.getSimpleName());
            fragments = new Fragment[]{messageFragment, adminMessageFragment};
            getSupportFragmentManager().beginTransaction()
                    .show(messageFragment)
                    .hide(adminMessageFragment)
                    .commit();
        } else {
            messageFragment = new MessageFragment();
            adminMessageFragment = new PrivateMessageFragment();
            fragments = new Fragment[]{messageFragment, adminMessageFragment};

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, messageFragment)
                    .add(R.id.fragment_container, adminMessageFragment).hide(adminMessageFragment)
                    .show(messageFragment)
                    .commit();
        }


    }
    /**
     * init views
     */
    private void initView() {
        unreadAddressLable1 = (TextView) findViewById(R.id.unread_address_number);
        unreadAddressLable2 = (TextView) findViewById(R.id.unread_msggg_number);
        unreadAddressLable1.setVisibility(View.VISIBLE);
        unreadAddressLable2.setVisibility(View.VISIBLE);
        mTabs = new Button[2];
        mTabs[0] = (Button) findViewById(R.id.btn_address_list);
        mTabs[1] = (Button) findViewById(R.id.btn_setting);
        // select first tab
        mTabs[0].setSelected(true);
    }

    /**
     * on tab clicked
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_address_list:
                index = 0;
                break;
            case R.id.btn_setting:
                index = 1;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
//        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }




    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);

        //save fragments
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment f : fragments) {
            if (f.isAdded()) {
                fm.putFragment(outState, f.getClass().getSimpleName(), f);
            }
        }

        super.onSaveInstanceState(outState);
    }
}