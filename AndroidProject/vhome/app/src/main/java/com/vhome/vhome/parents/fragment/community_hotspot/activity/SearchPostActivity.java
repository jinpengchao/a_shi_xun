package com.vhome.vhome.parents.fragment.community_hotspot.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.community_hotspot.util.RecordsDao;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchPostActivity extends AppCompatActivity {

    private RecordsDao mRecordsDao;
    //默然展示词条个数
    private final int DEFAULT_RECORD_NUMBER = 10;
    private List<String> recordList = new ArrayList<>();
    private TagAdapter mRecordsAdapter;

    private ImageView ivBack;
    private EditText editText;
    private ImageView clearSearch;
    private TextView ivSearch;
    private ConstraintLayout clToolbar;
    private TextView tvHistoryHint;
    private ImageView clearAllRecords;
    private TagFlowLayout tagFlowLayout;
    private ImageView moreArrow;
    private LinearLayout mHistoryContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        getViews();
        ButterKnife.bind(this);
        //默认账号
        String username = "a";
        //初始化数据库
        mRecordsDao = new RecordsDao(this, username);
        initData();
        initView();
    }

    private void getViews() {
        ivBack = findViewById(R.id.iv_back);
        editText = findViewById(R.id.edit_query);
        clearSearch = findViewById(R.id.iv_clear_search);
        ivSearch = findViewById(R.id.iv_search);
        clToolbar = findViewById(R.id.cl_toolbar);
        tvHistoryHint = findViewById(R.id.tv_history_hint);
        clearAllRecords = findViewById(R.id.clear_all_records);
        tagFlowLayout = findViewById(R.id.fl_search_records);
        moreArrow = findViewById(R.id.iv_arrow);
        mHistoryContent = findViewById(R.id.ll_history_content);
    }

    private void initView() {
        //创建历史标签适配器
        //为标签设置对应的内容
        mRecordsAdapter = new TagAdapter<String>(recordList) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchPostActivity.this).inflate(R.layout.tv_history,
                        tagFlowLayout, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tagFlowLayout.setAdapter(mRecordsAdapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position, FlowLayout parent) {
                //清空editText之前的数据
                editText.setText("");
                //将获取到的字符串传到搜索结果界面,点击后搜索对应条目内容
                editText.setText(recordList.get(position));
                editText.setSelection(editText.length());
            }
        });
        //删除某个条目
        tagFlowLayout.setOnLongClickListener(new TagFlowLayout.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                showDialog("确定要删除该条历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除某一条记录
                        mRecordsDao.deleteRecord(recordList.get(position));
                    }
                });
            }
        });

        //view加载完成时回调
        tagFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isOverFlow = tagFlowLayout.isOverFlow();
                boolean isLimit = tagFlowLayout.isLimit();
                if (isLimit && isOverFlow) {
                    moreArrow.setVisibility(View.VISIBLE);
                } else {
                    moreArrow.setVisibility(View.GONE);
                }
            }
        });

        moreArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFlowLayout.setLimit(false);
                mRecordsAdapter.notifyDataChanged();
            }
        });

        //清除所有记录
        clearAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("确定要删除全部历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tagFlowLayout.setLimit(true);
                        //清除所有数据
                        mRecordsDao.deleteUsernameAllRecords();
                    }
                });
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String record = editText.getText().toString();
                Intent intent = new Intent();
                intent.setClass(SearchPostActivity.this,ShowSearchActivity.class);
                intent.putExtra("record",record);
                startActivity(intent);
                if (!TextUtils.isEmpty(record)) {
                    //添加数据
                    mRecordsDao.addRecords(record);
                }
            }
        });

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除搜索历史
                editText.setText("");
            }
        });

        mRecordsDao.setNotifyDataChanged(new RecordsDao.NotifyDataChanged() {
            @Override
            public void notifyDataChanged() {
                initData();
            }
        });
    }
    private void showDialog(String dialogTitle, @NonNull DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchPostActivity.this);
        builder.setMessage(dialogTitle);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                emitter.onNext(mRecordsDao.getRecordsByNumber(DEFAULT_RECORD_NUMBER));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> s) throws Exception {
                        recordList.clear();
                        recordList = s;
                        if (null == recordList || recordList.size() == 0) {
                            mHistoryContent.setVisibility(View.GONE);
                        } else {
                            mHistoryContent.setVisibility(View.VISIBLE);
                        }
                        if (mRecordsAdapter != null) {
                            mRecordsAdapter.setData(recordList);
                            mRecordsAdapter.notifyDataChanged();
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        mRecordsDao.closeDatabase();
        mRecordsDao.removeNotifyDataChanged();
        super.onDestroy();
    }
}
