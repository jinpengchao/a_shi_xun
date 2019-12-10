package h.jpc.vhome.parents.fragment.community_hotspot.activity;

import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.MainActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.adapter.ExpandListAdapter;
import h.jpc.vhome.parents.fragment.adapter.ShowPostImgAdapter;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.CommentDetailBean;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.PostBean;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.ReplyDetailBean;
import h.jpc.vhome.util.ConnectionUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = "CommentActivity";
    private ExpandableListView expandableListView;
    private TextView tvLoadMore;
    private EditText edtCommentContent;
    private Button btnCommentCommit;
    private MyClickListener listener;
    private List<CommentDetailBean> commentList;
    private PostBean post;
    private ImageView ivHotPerson;
    private TextView tvHotName;
    private TextView tvHotContent;
    private TextView tvHotTime;
    private ImageView ivHotSave;
    private TextView tvHotComnum;
    private ImageView ivHotlike;
    private TextView tvHotLikenum;
    private GridView gvPostShow;
    private RelativeLayout rlPostSave;
    private RelativeLayout rlPostLike;
    private ShowPostImgAdapter showPostImgAdapter;
    private List<String> imgs = new ArrayList<>();
    private Gson gson = new Gson();
    private ExpandListAdapter adapter;
    private BottomSheetDialog dialog;
    private TextView tvAttention;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Bundle bundle = msg.getData();
                String data = bundle.getString("data");
                commentList = gson.fromJson(data,new TypeToken<List<CommentDetailBean>>(){}.getType());
                if(commentList.size()>0){
                    Log.e(TAG,"查询到数据id"+commentList.get(1).getId());
                    //给控件填充
                    fillPost();
                }else {
                    Log.e(TAG,"查询到数据为空");
                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        //获取帖子数据
        Intent postIntent = getIntent();
        post = (PostBean) postIntent.getSerializableExtra("post");
        getViews();
        registerListener();
        //判断是否收藏点赞关注过，修改图标
        setCLImg();
        getCommentData();

    }
    /**
     * 给控件添加数据
     */
    private void fillPost() {

        tvHotName.setText(post.getNickName());//设置发帖人昵称
        tvHotContent.setText(post.getPostContent());//设置帖子内容
        //设置时间
        String time = post.getTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String now = new SimpleDateFormat("MM.dd HH:mm").format(date);
        tvHotTime.setText(now);
        //显示图片
        String imgsData = post.getImgs();
        imgs = gson.fromJson(imgsData,new TypeToken<List<String>>(){}.getType());
        showPostImgAdapter = new ShowPostImgAdapter(imgs,CommentActivity.this);
        gvPostShow.setAdapter(showPostImgAdapter);
        tvHotComnum.setText(post.getCommentNum()+"");//显示评论数
        tvHotLikenum.setText(post.getLikeNum()+"");//显示点赞数
        initExpandableListView(commentList);//初始化评论和回复
    }

    /**
     * 初始化评论和回复列表
     * @param commentList
     */
    private void initExpandableListView(List<CommentDetailBean> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new ExpandListAdapter(this, commentList);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

//                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(CommentActivity.this,"点击了回复",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });
    }

    private void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){
                    dialog.dismiss();
                    SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
                    ReplyDetailBean detailBean = new ReplyDetailBean();
                    detailBean.setCommentId(commentList.get(position).getId());
                    detailBean.setContent(replyContent);
                    detailBean.setNickName(sp.getString("nickName",""));
                    detailBean.setHeadimg(sp.getString("headImg",""));
                    detailBean.setPersonId(sp.getString("id",""));
                    //保存时间
                    Date collectDate = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sdf.format(collectDate);
                    detailBean.setTime(time);
                    //更新UI
                    adapter.addTheReplyData(detailBean, position);
                    expandableListView.expandGroup(position);
                    //更新数据库
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                String replyData = gson.toJson(detailBean);
                                URL url = new URL("http://" + (new MyApp()).getIp() + ":8080/vhome/SaveReplyServlet");
                                ConnectionUtil util = new ConnectionUtil();
                                HttpURLConnection connection = util.sendData(url, replyData);
                                String receive = util.getData(connection);
                                if (null != receive && !"".equals(receive)) {
                                    Log.i(TAG, "保存回复成功" + replyData);
                                } else {
                                    Log.e(TAG, "保存回复失败！" + replyData);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }else {
                    Toast.makeText(CommentActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * 设置收藏和点赞的图标
     */
    private void setCLImg() {
        if(post.getSave_status()==1){
            ivHotSave.setImageResource(R.mipmap.post_save1);
        }else {
            ivHotSave.setImageResource(R.mipmap.post_save);
        }
        if(post.getLike_status()==1){
            ivHotlike.setImageResource(R.mipmap.post_like1);
        }else {
            ivHotlike.setImageResource(R.mipmap.post_like);
        }
    }

    /**
     * 获取评论和回复数据
     */
    private void getCommentData() {
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/GetCommentsServlet?postId="+post.getId());
                    ConnectionUtil connectionUtil = new ConnectionUtil();
                    String data = connectionUtil.getData(url);
                    connectionUtil.sendMsg(data,1,handler);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getViews() {
        expandableListView = findViewById(R.id.elview_comment);
        tvLoadMore = findViewById(R.id.tv_post_loadmore);
        edtCommentContent = findViewById(R.id.edt_comment_content);
        btnCommentCommit = findViewById(R.id.btn_comment_commit);
        ivHotPerson = findViewById(R.id.iv_hot_person);
        tvHotName = findViewById(R.id.tv_hot_name);
        tvHotContent = findViewById(R.id.tv_hot_content);
        tvHotTime = findViewById(R.id.tv_hot_time);
        ivHotSave = findViewById(R.id.iv_hot_save);
        tvHotComnum = findViewById(R.id.tv_hot_comnum);
        ivHotlike = findViewById(R.id.iv_hot_like);
        tvHotLikenum = findViewById(R.id.tv_hot_likenum);
        gvPostShow = findViewById(R.id.gv_post_show);
        rlPostSave = findViewById(R.id.rl_post_save);
        rlPostLike = findViewById(R.id.rl_post_like);
        tvAttention = findViewById(R.id.tv_attention);
    }

    private void registerListener() {
        listener = new MyClickListener();
        tvLoadMore.setOnClickListener(listener);
        btnCommentCommit.setOnClickListener(listener);
        rlPostSave.setOnClickListener(listener);
        rlPostLike.setOnClickListener(listener);
        ivHotPerson.setOnClickListener(listener);
        tvHotName.setOnClickListener(listener);
        tvAttention.setOnClickListener(listener);
    }
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_post_loadmore:
                    break;
                case R.id.rl_post_save:
                    if (1 == post.getSave_status()) {
                        Toast.makeText(view.getContext(), "已经收藏过了！", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("评论区", "修改图标");
                        post.setSave_status(1);
                        ivHotSave.setImageResource(R.mipmap.post_save1);
                        addPostCollection();
                    }
                    break;
                case R.id.rl_post_like:
                    if (1 == post.getLike_status()) {
                        Toast.makeText(view.getContext(), "已经点赞过了！", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("commentActivity", "修改点赞图标" );
                        post.setLike_status(1);
                        ivHotlike.setImageResource(R.mipmap.post_like1);
                        //点赞个数加一
                        int cnum = Integer.parseInt(tvHotLikenum.getText().toString().trim())+1;
                        post.setLikeNum(cnum);
                        tvHotLikenum.setText(cnum+"");
                        addPostLike();
                    }
                    break;
                case R.id.btn_comment_commit:
                    //添加评论数据
                    addComment();
                    break;
                case R.id.iv_hot_person:
                    break;
                case R.id.tv_hot_name:
                    break;
                case R.id.tv_attention:
                    //添加关注
                    addAttention();
                    break;
            }
        }
    }

    private void addAttention() {

    }

    /**
     * 点击评论按钮的时候增加一条评论
     */
    private void addComment() {
        String commentContent = edtCommentContent.getText().toString().trim();
        if(!TextUtils.isEmpty(commentContent)){
//            dialog.dismiss();
            //隐藏软键盘
            InputMethodManager imm = (InputMethodManager) CommentActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(CommentActivity.this.getWindow().getDecorView().getWindowToken(), 0);

            SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
            CommentDetailBean detailBean = new CommentDetailBean();
            detailBean.setId(0);
            detailBean.setPostId(post.getId());
            detailBean.setPersonId(sp.getString("id",""));
            detailBean.setHeadimg(sp.getString("headImg",""));
            detailBean.setNickName(sp.getString("nickName",""));
            detailBean.setContent(commentContent);
            //保存时间
            Date collectDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(collectDate);
            detailBean.setTime(time);
            adapter.addTheCommentData(detailBean);//更新UI
            int num = Integer.parseInt(tvHotComnum.getText().toString().trim());
            tvHotComnum.setText(num+1+"");
            edtCommentContent.setText("");
            //更新数据库
            new Thread(){
                @Override
                public void run() {
                    try {
                        String commentData = gson.toJson(detailBean);
                        URL url = new URL("http://" + (new MyApp()).getIp() + ":8080/vhome/SaveCommentServlet");
                        ConnectionUtil util = new ConnectionUtil();
                        HttpURLConnection connection = util.sendData(url, commentData);
                        String receive = util.getData(connection);
                        if (null != receive && !"".equals(receive)) {
                            Log.i(TAG, "保存评论成功" + commentData);
                        } else {
                            Log.e(TAG, "保存评论失败！" + commentContent);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }else {
            Toast.makeText(CommentActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    //点击收藏时
    private void addPostCollection() {
        //首先准备收藏的数据
        CollectionBean collection = new CollectionBean();
        SharedPreferences sp = getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
        String personId = sp.getString("id", "");
        Log.i("评论区收藏人id===", personId);
        collection.setPersonId(personId);
        collection.setPostId(post.getId());
        Date collectDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(collectDate);
        collection.setTime(time);
        Gson gson = new Gson();
        final String data = gson.toJson(collection);
        //开启线程保存到数据库
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + (new MyApp()).getIp() + ":8080/vhome/SaveCollectionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url, data);
                    String receive = util.getData(connection);
                    if (null != receive && !"".equals(receive)) {
                        Log.i("hotSpotAdapter", "收藏成功" + data);
                    } else {
                        Log.e("hotSpotAdapter", "收藏失败！" + data);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    //点赞操作保存数据
    private void addPostLike() {
//        准备点赞数据
        SharedPreferences sp = getSharedPreferences(new MyApp().getPathInfo(), Context.MODE_PRIVATE);
        GoodPostBean goodPost = new GoodPostBean();
        goodPost.setPostId(post.getId());
        String goodPersonId = sp.getString("id", "");
        goodPost.setGoodPersonId(goodPersonId);
        goodPost.setPublishPersonId(post.getPersonId());
        Date likeDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(likeDate);
        goodPost.setTime(time);
        Gson gson = new Gson();
        final String likeData = gson.toJson(goodPost);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/SaveGoodPostServlet");
                    ConnectionUtil connectionUtil = new ConnectionUtil();
                    HttpURLConnection connection = connectionUtil.sendData(url,likeData);
                    String receive = connectionUtil.getData(connection);
                    if (null != receive && !"".equals(receive)) {
                        Log.i("commentactivity", "点赞成功" + likeData);
                    } else {
                        Log.e("commentactivity", "点赞失败！" + likeData);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
