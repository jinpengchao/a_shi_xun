package h.jpc.vhome.parents.fragment.radio_ximalaya.fragment;


import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.radio_ximalaya.DetailActivity;
import h.jpc.vhome.parents.fragment.radio_ximalaya.adapter.RecommendListAdapter;
import h.jpc.vhome.parents.fragment.radio_ximalaya.base.BaseFragment;
import h.jpc.vhome.parents.fragment.radio_ximalaya.interfaces.IRecommendViewCallBack;
import h.jpc.vhome.parents.fragment.radio_ximalaya.presenters.AlbumDetailPresenter;
import h.jpc.vhome.parents.fragment.radio_ximalaya.presenters.RecommendPresenter;
import h.jpc.vhome.parents.fragment.radio_ximalaya.views.UILoader;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallBack, RecommendListAdapter.OnRecommendClickListener {
    private static final String TAG="RecommendFragment";
    private View mRootView;
    private RecyclerView recyclerView;
    private RecommendListAdapter recommendListAdapter;
    private RecommendPresenter mRecommendPresenter;
    private UILoader uiLoader;
    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {
        uiLoader= new UILoader(getContext()) {
            @Override
            protected View geSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater,container);
            }
        };



        //拿数据

       mRecommendPresenter=RecommendPresenter.getsInstance();
       mRecommendPresenter.registerViewCallback(this);
       mRecommendPresenter.getRecommendList();

       if(uiLoader.getParent()instanceof ViewGroup){
          ((ViewGroup) uiLoader.getParent()).removeView(uiLoader);
       }
       return uiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView=layoutInflater.inflate(R.layout.fragment_recommend,container,false);
        //设置步骤
        //1.找到对应的控件
        recyclerView=mRootView.findViewById(R.id.recommend_list);
        //2.设置布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(view.getContext(),5) ;
                outRect.bottom= UIUtil.dip2px(view.getContext(),5) ;
                outRect.left= UIUtil.dip2px(view.getContext(),5) ;
                outRect.right= UIUtil.dip2px(view.getContext(),5) ;
            }
        });
        //3.设置适配器
        recommendListAdapter=new RecommendListAdapter();
        recyclerView.setAdapter(recommendListAdapter);
        recommendListAdapter.setOnRecommendItemClickListener(this);
        return  mRootView;
    }


    @Override
    public void onRecommendListLoaded(List<Album> result) {
        recommendListAdapter.setData(result);
        uiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRecommendPresenter!=null){
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }

    @Override
    public void OnItemClick(int position, Album album) {
        AlbumDetailPresenter.getInstance().setTargetAlbum(album);
        //根据位置拿到数据
        Intent intent=new Intent(getContext(), DetailActivity.class);
        startActivity(intent);
    }
}
