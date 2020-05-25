package com.vhome.vhome.parents.fragment.community_hotspot.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import cn.edu.heuet.littlecurl.ninegridview.base.NineGridViewAdapter;
import cn.edu.heuet.littlecurl.ninegridview.bean.NineGridItem;
import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;

import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.parents.fragment.adapter.ExpandListAdapter;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.AttentionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.CollectionBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.CommentDetailBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.GoodPostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.MyMedia;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.PostBean;
import com.vhome.vhome.parents.fragment.community_hotspot.entity.ReplyDetailBean;
import com.vhome.vhome.parents.fragment.community_hotspot.util.GetVideoThumbnail;
import com.vhome.vhome.parents.fragment.myself.ShowMyselfActivity;
import com.vhome.vhome.util.ConnectionUtil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
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

import static com.vhome.vhome.parents.fragment.adapter.HotSpotAdapter.isImgUrl;

public class CommentActivity extends Activity {
    private static final String TAG = "CommentActivity";
    private ExpandableListView expandableListView;
    private EditText edtCommentContent;
    private TextView tvLoadMore;
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
    private NineGridViewGroup nineGridViewGroup;
    private RelativeLayout rlPostSave;
    private RelativeLayout rlPostLike;
    private ArrayList<String> imgs = new ArrayList<>();
    private Gson gson = new Gson();
    private ExpandListAdapter adapter;
    private BottomSheetDialog dialog;
    private TextView tvAttention;
    private int DEL_COMMENT = 2;
    private int DEL_REPLY = 3;
    private int SAVE_COMMENT = 4;
    private ArrayList<MyMedia> medias;
    private GetVideoThumbnail getVideoThumbnail = new GetVideoThumbnail();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                Bundle bundle = msg.getData();
                String data = bundle.getString("data");
                commentList = gson.fromJson(data, new TypeToken<List<CommentDetailBean>>() {
                }.getType());
                if (commentList.size() > 0) {
                    Log.e(TAG, "查询到数据id" + commentList.get(0).getId());
                } else {
                    Log.e(TAG, "查询到数据为空");
                }
                initExpandableListView(commentList);//初始化评论和回复
            }else if (msg.what==DEL_COMMENT){
                int position = msg.getData().getInt("position");
                commentList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(CommentActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
            }else if (msg.what == DEL_REPLY){
                Bundle bundle = msg.getData();
                int groupPosition = bundle.getInt("groupPosition");
                int childPosition = bundle.getInt("childPosition");
                commentList.get(groupPosition).getReplyList().remove(childPosition);
                adapter.notifyDataSetChanged();
                Toast.makeText(CommentActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
            }else if (msg.what == SAVE_COMMENT){
                getCommentData();
                adapter.notifyDataSetChanged();
                int num = Integer.parseInt(tvHotComnum.getText().toString().trim());
                tvHotComnum.setText(num+1+"");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment_post);
        medias = new ArrayList<>();
        //获取帖子数据
        Intent postIntent = getIntent();
        post = (PostBean) postIntent.getSerializableExtra("post");
        Log.e(TAG, "onCreate: post内容"+post.getHeadimg() );
        getViews();
        registerListener();
        //判断是否收藏点赞关注过，修改图标
        setCLImg();
        //填充数据
        fillPost();
        getCommentData();

    }

    //评论与回复的长按删除

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            final long packedPosition = expandableListView.getExpandableListPosition(position);
            final int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
            final int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
            SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
            String personId = sp.getString("id","");
            //赋予权限，如果发帖人是自己可以删除全部
            if(personId.equals(post.getPersonId())){
                //长按的是group的时候，childPosition = -1，这是子条目的长按点击
                if(childPosition!=-1) {//删除回复
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setTitle("删除回复：");
                    builder.setMessage("确定要删除这条回复吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delReply(groupPosition,childPosition);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {//删除评论
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setTitle("删除评论：");
                    builder.setMessage("确定要删除这条评论吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delComment(groupPosition);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }else {//发帖人不是自己只能删除自己的
                if(childPosition==-1){//点击了评论
                    if (commentList.get(groupPosition).getPersonId().equals(personId)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                        builder.setTitle("删除评论：");
                        builder.setMessage("确定要删除这条评论吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delComment(groupPosition);
                            }
                        });
                        builder.setNegativeButton("取消", null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }else {//点击回复
                    if (commentList.get(groupPosition).getReplyList().get(childPosition).getPersonId().equals(personId)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                        builder.setTitle("删除回复：");
                        builder.setMessage("确定要删除这条回复吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delReply(groupPosition,childPosition);
                            }
                        });
                        builder.setNegativeButton("取消", null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            }
            return true;
        }
    };

    /**
     * 删除一条回复
     * @param groupPosition
     * @param childPosition
     */
    private void delReply(int groupPosition, int childPosition) {
        new Thread(){
            @Override
            public void run() {
                int replyId = commentList.get(groupPosition).getReplyList().get(childPosition).getId();
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveReplyServlet?replyId="+replyId);
                    ConnectionUtil util = new ConnectionUtil();
                    String data = util.getData(url);
                    if (data != null) {
                        Log.i(TAG,"删除回复成功！");
                        Bundle bundle = new Bundle();
                        bundle.putInt("groupPosition",groupPosition);
                        bundle.putInt("childPosition",childPosition);
                        Message msg = new Message();
                        msg.what = DEL_REPLY;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }else {
                        Log.e(TAG, "run: 删除评论失败" );
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 删除一条评论
     * @param position
     */
    private void delComment(int position) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveCommentServlet?commentId="+commentList.get(position).getId());
                    ConnectionUtil util = new ConnectionUtil();
                    String data = util.getData(url);
                    if (data != null) {
                        Log.i(TAG,"删除评论成功！");
                        Bundle bundle = new Bundle();
                        bundle.putInt("position",position);
                        Message msg = new Message();
                        msg.what = DEL_COMMENT;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }else {
                        Log.e(TAG, "run: 删除评论失败" );
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 给控件添加数据
     */
    private void fillPost() {
        //设置头像
        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/images/"+post.getHeadimg()+".jpg";;
        Glide.with(CommentActivity.this)
                .load(url).priority(Priority.HIGH)
                .error(R.mipmap.errorimg1)
                .centerCrop().into(ivHotPerson);
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
        if(post.getImgs()!=null&&!"".equals(post.getImgs())){
            String imgsData = post.getImgs();
            imgs = gson.fromJson(imgsData,new TypeToken<List<String>>(){}.getType());
            if (imgs.size()>0){//判断集合不为空
                for (String name:imgs) {
                    String pUrl = "http://" + (new MyApp()).getIp() + ":8080/vhome/images/"+name;
                    boolean isPic = isImgUrl(name);
                    if (true==isPic){//当是图片url时，只添加图片
                        medias.add(new MyMedia(pUrl));
                        Log.d(TAG,"图片的url："+pUrl);
                    }else {//当是视频的时候，获取视频缩略图作为图片，并加入视频url
                        String picPath = getVideoThumbnail.getFirstThumbPath(CommentActivity.this,name,pUrl);
                        medias.add(new MyMedia(picPath,pUrl));
                    }
                }
                ArrayList<NineGridItem> nineGridItemList = new ArrayList<>();
                for (MyMedia myMedia : medias) {
                    String thumbnailUrl = myMedia.getImageUrl();
                    String bigImageUrl = thumbnailUrl;
                    String videoUrl = myMedia.getVideoUrl();
                    nineGridItemList.add(new NineGridItem(thumbnailUrl, bigImageUrl, videoUrl));
                }
                NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(nineGridItemList);
                nineGridViewGroup.setAdapter(nineGridViewAdapter);
            }
        }
        tvHotComnum.setText(post.getCommentNum()+"");//显示评论数
        tvHotLikenum.setText(post.getLikeNum()+"");//显示点赞数
//        initExpandableListView(commentList);//初始化评论和回复
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
        //注册长按事件
        expandableListView.setOnItemLongClickListener(onItemLongClickListener);

        //当点击评论的时候
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                if(commentList.get(groupPosition).getId()==0){
                    Toast.makeText(CommentActivity.this,"您刚评论完哦！",Toast.LENGTH_SHORT).show();
                }else {
                    Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
                    showReplyDialog(groupPosition);
                }
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
                                    util.sendMsg(receive,SAVE_COMMENT,handler);
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
            ivHotlike.setImageResource(R.mipmap.post_img_good1);
        }else {
            ivHotlike.setImageResource(R.mipmap.post_img_good);
        }
        //发帖人是自己的时候不显示关注
        SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
        String myId = sp.getString("id","");
        Log.e(TAG,"postpersonId:"+post.getPersonId()+"..个人id"+myId);
        if(post.getPersonId().equals(myId)){
            tvAttention.setVisibility(View.GONE );
        }else {
            if(post.getAttention_status()==1){
                tvAttention.setText("已关注");
                GradientDrawable myGrad = (GradientDrawable)tvAttention.getBackground();
                myGrad.setColor(ContextCompat.getColor(CommentActivity.this,R.color.attentionedColor));
            }else {
                tvAttention.setText("+关注");
                GradientDrawable myGrad = (GradientDrawable)tvAttention.getBackground();
                myGrad.setColor(ContextCompat.getColor(CommentActivity.this,R.color.attentionColor));
            }
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
        nineGridViewGroup = findViewById(R.id.nineGrid);
        rlPostSave = findViewById(R.id.rl_post_save);
        rlPostLike = findViewById(R.id.rl_post_like);
        tvAttention = findViewById(R.id.tv_attention);
    }

    private void registerListener() {
        listener = new MyClickListener();
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
                case R.id.rl_post_save:
                    if (1 == post.getSave_status()) {
                        post.setSave_status(0);
                        ivHotSave.setImageResource(R.mipmap.post_save);
                        delPostCollection();
                    } else {
                        Log.i("评论区", "修改图标");
                        post.setSave_status(1);
                        ivHotSave.setImageResource(R.mipmap.post_save1);
                        addPostCollection();
                    }
                    break;
                case R.id.rl_post_like:
                    if (1 == post.getLike_status()) {
                        post.setLike_status(0);
                        ivHotlike.setImageResource(R.mipmap.post_img_good);
                        //点赞个数减一
                        int cnum = Integer.parseInt(tvHotLikenum.getText().toString().trim())-1;
                        post.setLikeNum(cnum);
                        tvHotLikenum.setText(cnum+"");
                        delPostLike();
                    } else {
                        Log.i("commentActivity", "修改点赞图标" );
                        post.setLike_status(1);
                        ivHotlike.setImageResource(R.mipmap.post_img_good1);
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

                case R.id.tv_hot_name:
                    Intent person = new Intent();
                    person.putExtra("personId",post.getPersonId());
                    person.setClass(CommentActivity.this, ShowMyselfActivity.class);
                    startActivity(person);
                    break;
                case R.id.tv_attention:
                    //添加关注
                    if (post.getAttention_status()==1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                        builder.setTitle("温馨提示：");
                        builder.setMessage("确定要取消关注吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tvAttention.setText("+关注");
                                GradientDrawable myGrad = (GradientDrawable)tvAttention.getBackground();
                                myGrad.setColor(ContextCompat.getColor(CommentActivity.this,R.color.attentionColor));
                                post.setAttention_status(0);
                                delAttention();
                                Log.i(TAG, "onClick: 已经取消关注");
                            }
                        });
                        builder.setNegativeButton("取消",null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else if (post.getAttention_status()==0){
                        addAttention();
                    }
                    break;
            }
        }
    }

    /**
     * 删除关注
     */
    public void delAttention() {
        AttentionBean attentionBean = new AttentionBean();
        attentionBean.setAttentionPersonId(post.getPersonId());
        SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),Context.MODE_PRIVATE);
        attentionBean.setPersonId(sp.getString("id",""));
        String data = (new Gson()).toJson(attentionBean);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveAttentionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url,data);
                    String receive = util.getData(connection);
                    if (null!=receive && !"".equals(receive)){
                        Log.i(TAG, "run: 取消关注成功！");
                    }else {
                        Log.e(TAG, "run: 取消关注失败！");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 删除点赞数据
     */
    private void delPostLike() {
        String personId = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE).getString("id","");
        int postId = post.getId();
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveGoodPostServlet" +
                            "?personId="+personId+"&postId="+postId);
                    ConnectionUtil util = new ConnectionUtil();
                    String receive = util.getData(url);
                    if (receive == null) {
                        Log.e(TAG, "run: 删除点赞数据出错" );
                    }else {
                        Log.i(TAG, "run: 删除点赞数据成功");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 删除收藏数据
     */
    private void delPostCollection() {
        String personId = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE).getString("id","");
        int postId = post.getId();
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/RemoveCollectServlet" +
                            "?personId="+personId+"&postId="+postId);
                    ConnectionUtil util = new ConnectionUtil();
                    String receive = util.getData(url);
                    if (receive == null) {
                        Log.e(TAG, "run: 删除收藏数据出错" );
                    }else {
                        Log.i(TAG, "run: 删除收藏数据成功");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 添加关注
     */
    private void addAttention() {
        //准备数据
        AttentionBean attention = new AttentionBean();
        attention.setAttentionPersonId(post.getPersonId());
        SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
        attention.setPersonId(sp.getString("id",""));
//        修改UI
        tvAttention.setText("已关注");
        GradientDrawable myGrad = (GradientDrawable)tvAttention.getBackground();
        myGrad.setColor(ContextCompat.getColor(CommentActivity.this,R.color.attentionedColor));
        post.setAttention_status(1);
        new Thread(){
            @Override
            public void run() {
                Gson gson = new Gson();
                String attentionData = gson.toJson(attention);
                try {
                    URL url = new URL("http://"+(new MyApp()).getIp()+":8080/vhome/SaveAttentionServlet");
                    ConnectionUtil util = new ConnectionUtil();
                    HttpURLConnection connection = util.sendData(url,attentionData);
                    String receive = util.getData(connection);
                    if (null!=receive&&!"".equals(receive)){
                        Log.i(TAG,"添加关注成功"+attentionData);
                    }else {
                        Log.e(TAG,"添加关注出错");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 点击评论按钮的时候增加一条评论
     */
    private void addComment() {
        String commentContent = edtCommentContent.getText().toString().trim();
        if(!TextUtils.isEmpty(commentContent)){
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
                            util.sendMsg(receive,SAVE_COMMENT,handler);
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
