//package h.jpc.vhome.chat.view;
//
//import android.content.Context;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//import android.util.AttributeSet;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import h.jpc.vhome.R;
//import h.jpc.vhome.chat.utils.SharePreferenceManager;
//
///**
// * Created by ${chenyn} on 2017/2/20.
// */
//
//public class MainView extends RelativeLayout {
//
//
//    private ScrollControlViewPager mViewContainer;
//    private TextView mAllContactNumber;
//
//    public MainView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public void initModule() {
//        mViewContainer = (ScrollControlViewPager) findViewById(R.id.viewpager);
//        mViewContainer.setOffscreenPageLimit(2);
//        mAllContactNumber = findViewById(R.id.all_contact_number);
//        if (SharePreferenceManager.getCachedNewFriendNum() > 0) {
//            mAllContactNumber.setVisibility(VISIBLE);
//            mAllContactNumber.setText(String.valueOf(SharePreferenceManager.getCachedNewFriendNum()));
//        } else {
//            mAllContactNumber.setVisibility(GONE);
//        }
//    }
//    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
//        mViewContainer.addOnPageChangeListener(onPageChangeListener);
//    }
//
//    public void setViewPagerAdapter(FragmentPagerAdapter adapter) {
//        mViewContainer.setAdapter(adapter);
//    }
//
//    public void setCurrentItem(int index, boolean scroll) {
//        mViewContainer.setCurrentItem(index, scroll);
//    }
//}
