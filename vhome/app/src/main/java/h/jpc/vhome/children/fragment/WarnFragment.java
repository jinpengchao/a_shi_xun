package h.jpc.vhome.children.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.R;
import h.jpc.vhome.children.fragment.dialog.MyDialog;
import h.jpc.vhome.children.fragment.imageloader.GlideImageLoader;
import h.jpc.vhome.children.fragment.slideadapter.ListViewCompat;
import h.jpc.vhome.children.fragment.slideadapter.SlideView;

import static h.jpc.vhome.children.fragment.slideadapter.SlideView.flag;

public class WarnFragment extends Fragment implements AdapterView.OnItemLongClickListener, View.OnClickListener, SlideView.OnSlideListener {
    private static final String TAG = "MainActivity";
    private ListViewCompat mListView;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    private SlideView mLastSlideViewWithStatusOn;
    private SlideAdapter adapter;
    private MyDialog myDialog;
    private Button addNewNormalWarn;
    private Button sendNewWarn;
    private Button quaryAllWarn;
    private EditText newNormalWarnMsg;
    private Banner banner;
    private List<Integer> images;
    private List<String> titles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_children_warn, null);
        //setView();
        addNewNormalWarn = view.findViewById(R.id.addNormalWarn);
        sendNewWarn = view.findViewById(R.id.sendNewWarn);
        quaryAllWarn = view.findViewById(R.id.quaryAllWarn);
        newNormalWarnMsg = view.findViewById(R.id.new_normal_warn_text);
        banner = view.findViewById(R.id.banner);

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images = new ArrayList<>();
        images.add(R.mipmap.sss);
        images.add(R.mipmap.sss);
        images.add(R.mipmap.sss);
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        titles = new ArrayList<>();
        titles.add("jwbsb1");
        titles.add("jwbsb2");
        titles.add("jwbsb3");
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        //点击事件
        addNewNormalWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_add_new_normal_warn, null);
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                Button cancle = (Button)view.findViewById(R.id.new_normal_warn_cancle);
                Button ok = (Button)view.findViewById(R.id.new_normal_warn_ok);
                final EditText editText = (EditText)view.findViewById(R.id.new_normal_warn_text);
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //存入数据库放入常用列表
                        Toast.makeText(getActivity(),editText.getText(),Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
        sendNewWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_send_new_warn, null);
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
        quaryAllWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_quary_all_warn, null);
                myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                myDialog.setCancelable(true);
                myDialog.show();
            }
        });
        mListView = view.findViewById(R.id.list);

        for (int i = 0; i < 20; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.msg = "青岛爆炸满月：大量鱼虾死亡"+i;
            } else {
                item.msg = "欢迎你使用微信"+i;
            }
            mMessageItems.add(item);
        }
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(this);
        return view;
    }



    private class SlideAdapter extends BaseAdapter {
        public LayoutInflater mInflater;
        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.item_listview_delete, null);
                slideView = new SlideView(getActivity());
                slideView.setContentView(itemView);
                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(WarnFragment.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.msg.setText(item.msg+"****");
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mMessageItems.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "删除成功",Toast.LENGTH_SHORT).show();
                }
            });

            return slideView;
        }

    }

    public class MessageItem {
        public String msg;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public TextView msg;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {;
            msg = (TextView) view.findViewById(R.id.msg);
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        view = getLayoutInflater().inflate(R.layout.dialog_send_new_warn, null);
        MessageItem item = mMessageItems.get(position);
        Log.e("ss",item.msg);
        EditText sendNewWarnMsg = view.findViewById(R.id.send_new_warn_text);
        sendNewWarnMsg.setText(item.msg);
        myDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
        myDialog.setCancelable(true);
        myDialog.show();
        return true;
    }

    //    public voidOnI
//    @Override
//    public void onItem

//    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.holder:
                break;
            default:
                break;
        }
    }
}
