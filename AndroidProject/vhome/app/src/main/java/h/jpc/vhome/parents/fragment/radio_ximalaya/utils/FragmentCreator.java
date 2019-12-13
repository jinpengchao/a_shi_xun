package h.jpc.vhome.parents.fragment.radio_ximalaya.utils;



import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import h.jpc.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;
import h.jpc.vhome.parents.fragment.radio_ximalaya.fragment.HistoryFragment;
import h.jpc.vhome.parents.fragment.radio_ximalaya.fragment.RecommendFragment;
import h.jpc.vhome.parents.fragment.radio_ximalaya.fragment.SubscriptionFragment;

public class FragmentCreator {
    public final static int INDEX_RECOMMEND=0;
    public final static int INDEX_SUBSCRIPTION=1;
    public final static int INDEX_HISTORY=2;
    public final static int PAGE_COUNT=3;
    public final static Map<Integer, BaseFragment> sCache=new HashMap<>();
    public  final static Fragment getFragment(int index){
        BaseFragment baseFragment=sCache.get(index);
        if (baseFragment!=null){
            return baseFragment;
        }
        switch (index){
            case   INDEX_RECOMMEND:
                baseFragment=new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment=new SubscriptionFragment();
                break;
            case INDEX_HISTORY:
                baseFragment=new HistoryFragment();
                break;
        }
        sCache.put(index,baseFragment);
        return  baseFragment;
    }
}
