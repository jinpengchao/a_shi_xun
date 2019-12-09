package h.jpc.vhome.chat.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.fragment.FriendFragment;
import h.jpc.vhome.chat.activity.fragment.GroupFragment;
import h.jpc.vhome.chat.adapter.ViewPagerAdapter;
import h.jpc.vhome.chat.view.NoScrollViewPager;

/**
 * Created by ${chenyn} on 2017/11/7.
 */

public class VerificationMessageActivity extends FragmentActivity {

    private NoScrollViewPager mPager;
    private List<Fragment> mFragmentList;
    private RadioGroup mRg;
    private int mCurTabIndex;
    private ImageButton mReturn_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_message);
        initData();
    }

    private void initData() {
        mPager = findViewById(R.id.verification_viewpager);
        mRg = findViewById(R.id.rg_verification);
        mReturn_btn = findViewById(R.id.return_btn);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new FriendFragment());
        mFragmentList.add(new GroupFragment());
        mRg.check(R.id.rb_friend);

        mPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList));

        mRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_friend:
                    mCurTabIndex = 0;
                    break;
                case R.id.rb_group:
                    mCurTabIndex = 1;
                    break;
                default:
                    break;
            }
            mPager.setCurrentItem(mCurTabIndex, false);
        });

        mReturn_btn.setOnClickListener(v -> finish());
    }

}
