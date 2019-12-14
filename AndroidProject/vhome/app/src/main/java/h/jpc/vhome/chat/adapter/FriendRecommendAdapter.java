package h.jpc.vhome.chat.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.FriendInfoActivity;
import h.jpc.vhome.chat.activity.GroupNotFriendActivity;
import h.jpc.vhome.chat.activity.SearchFriendDetailActivity;
import h.jpc.vhome.chat.application.JGApplication;
import h.jpc.vhome.chat.database.FriendEntry;
import h.jpc.vhome.chat.database.FriendRecommendEntry;
import h.jpc.vhome.chat.entity.Event;
import h.jpc.vhome.chat.entity.EventType;
import h.jpc.vhome.chat.entity.FriendInvitation;
import h.jpc.vhome.chat.utils.BitmapLoader;
import h.jpc.vhome.chat.utils.DialogCreator;
import h.jpc.vhome.chat.utils.NativeImageLoader;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ViewHolder;
import h.jpc.vhome.chat.utils.photochoose.SelectableRoundedImageView;
import h.jpc.vhome.chat.view.SwipeLayout;
import h.jpc.vhome.user.entity.User;
import h.jpc.vhome.util.ConnectionUtil;

/**
 * Created by ${chenyn} on 2017/3/20.
 */

public class FriendRecommendAdapter extends BaseAdapter {

    private Fragment mFragment;
    private Context mContext;
    private List<FriendRecommendEntry> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private float mDensity;
    private int mWidth;

    public FriendRecommendAdapter(Fragment fragment, List<FriendRecommendEntry> list, float density,
                                  int width) {
        mFragment = fragment;
        mContext = mFragment.getActivity();
        mList = list;
        mInflater = LayoutInflater.from(mContext);
        mDensity = density;
        mWidth = width;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_friend_recomend, null);
        }
        SelectableRoundedImageView headIcon = ViewHolder.get(convertView, R.id.item_head_icon);
        TextView name = ViewHolder.get(convertView, R.id.item_name);
        TextView reason = ViewHolder.get(convertView, R.id.item_reason);
        final TextView addBtn = ViewHolder.get(convertView, R.id.item_add_btn);
        final TextView state = ViewHolder.get(convertView, R.id.item_state);
        final LinearLayout itemLl = ViewHolder.get(convertView, R.id.friend_verify_item_ll);


        final SwipeLayout swp_layout = ViewHolder.get(convertView, R.id.swp_layout);
        final TextView txt_del = ViewHolder.get(convertView, R.id.txt_del);

        final FriendRecommendEntry item = mList.get(position);
        SharePreferenceManager.setItem(item.getId());
        Bitmap bitmap = NativeImageLoader.getInstance().getBitmapFromMemCache(item.username);
        if (bitmap == null) {
            String path = item.avatar;
            if (path == null || TextUtils.isEmpty(path)) {
                headIcon.setImageResource(R.drawable.rc_default_portrait);
            } else {
                bitmap = BitmapLoader.getBitmapFromFile(path, (int) (50 * mDensity), (int) (50 * mDensity));
                NativeImageLoader.getInstance().updateBitmapFromCache(item.username, bitmap);
                headIcon.setImageBitmap(bitmap);
            }
        } else {
            headIcon.setImageBitmap(bitmap);
        }

        name.setText(item.displayName);
        reason.setText(item.reason);
        JMessageClient.getUserInfo(item.username, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == 0) {
                    if (userInfo.isFriend()) {
                        item.state = FriendInvitation.ACCEPTED.getValue();
                        item.save();
                        FriendEntry entry = FriendEntry.getFriend(JGApplication.getUserEntry(), item.username, item.appKey);
                        if (entry == null) {
                            EventBus.getDefault().post(new Event.Builder().setType(EventType.addFriend)
                                    .setFriendId(item.getId()).build());
                        }

                    }
                }
            }
        });

        if (item.state.equals(FriendInvitation.INVITED.getValue())) {
            addBtn.setVisibility(View.VISIBLE);
            state.setVisibility(View.GONE);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = DialogCreator.createLoadingDialog(mContext, "正在加载");
                    ContactManager.acceptInvitation(item.username, item.appKey, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            dialog.dismiss();
                            if (responseCode == 0) {
                                item.state = FriendInvitation.ACCEPTED.getValue();
                                item.save();
                                Log.e("申请人的电话",item.username);
                                addBtn.setVisibility(View.GONE);
                                state.setVisibility(View.VISIBLE);
                                state.setTextColor(mContext.getResources().getColor(R.color.contacts_pinner_txt));
                                state.setText("已添加");
                                EventBus.getDefault().post(new Event.Builder().setType(EventType.addFriend)
                                        .setFriendId(item.getId()).build());

                                //添加好友成功创建个会话
                                Conversation conversation = JMessageClient.getSingleConversation(item.username, item.appKey);
                                if (conversation == null) {
                                    conversation = Conversation.createSingleConversation(item.username, item.appKey);
                                    EventBus.getDefault().post(new Event.Builder()
                                            .setType(EventType.createConversation)
                                            .setConversation(conversation)
                                            .build());
                                }
                                //此处添加
                                addNewRelation(item.username);
                            }
                        }
                    });
                }
            });
        } else if (item.state.equals(FriendInvitation.ACCEPTED.getValue())) {
            addBtn.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.contacts_pinner_txt));
            state.setText(mContext.getString(R.string.added));
        } else if (item.state.equals(FriendInvitation.INVITING.getValue())) {
            addBtn.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.finish_btn_clickable_color));
            state.setText(mContext.getString(R.string.friend_inviting));
            state.setTextColor(mContext.getResources().getColor(R.color.wait_inviting));
        } else if (item.state.equals(FriendInvitation.BE_REFUSED.getValue())) {
            addBtn.setVisibility(View.GONE);
            reason.setTextColor(mContext.getResources().getColor(R.color.contacts_pinner_txt));
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.contacts_pinner_txt));
            state.setText(mContext.getString(R.string.decline_friend_invitation));
        } else {
            addBtn.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.contacts_pinner_txt));
            state.setText(mContext.getString(R.string.refused));
        }
        itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FriendRecommendEntry entry = mList.get(position);
                final Intent intent;
                if (entry.state.equals(FriendInvitation.INVITED.getValue())) {
                    //1.没同意也没拒绝时--> 是否同意界面
                    intent = new Intent(mContext, SearchFriendDetailActivity.class);
                    intent.putExtra("reason", item.reason);
                    intent.putExtra("position", position);
                    intent.putExtra(JGApplication.TARGET_ID, entry.username);
                    intent.putExtra(JGApplication.TARGET_APP_KEY, entry.appKey);
                    mFragment.startActivityForResult(intent, 0);
                    //2.已经添加的 --> 好友详情
                } else if (entry.state.equals(FriendInvitation.ACCEPTED.getValue())) {
                    JMessageClient.getUserInfo(item.username, new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            if (i == 0) {
                                Intent intent1 = new Intent();
                                if (userInfo.isFriend()) {
                                    intent1.setClass(mContext, FriendInfoActivity.class);
                                    intent1.putExtra("fromContact", true);
                                } else {
                                    intent1.setClass(mContext, GroupNotFriendActivity.class);
                                }
                                intent1.putExtra(JGApplication.TARGET_ID, entry.username);
                                intent1.putExtra(JGApplication.TARGET_APP_KEY, entry.appKey);
                                mFragment.startActivityForResult(intent1, 0);
                            }
                        }
                    });

                    //3.自己拒绝、被对方拒绝、等待对方验证 --> 用户资料界面
                } else {
                    intent = new Intent(mContext, GroupNotFriendActivity.class);
                    intent.putExtra("reason", item.reason);
                    intent.putExtra(JGApplication.TARGET_ID, entry.username);
                    intent.putExtra(JGApplication.TARGET_APP_KEY, entry.appKey);
                    mFragment.startActivityForResult(intent, 0);
                }

            }
        });


        swp_layout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //侧滑删除拉出来后,点击删除,删除此条目
                txt_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final FriendRecommendEntry entry = mList.get(position);
                        FriendRecommendEntry.deleteEntry(entry);
                        mList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                //侧滑删除拉出来后,点击整个条目的话,删除回退回去
                itemLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swp_layout.cancelPull();

                    }
                });
            }
            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                /**
                 * 这里分三种情况
                 * 1.没同意也没拒绝时--> 是否同意界面
                 * 2.已经添加的 --> 好友详情
                 * 3.自己拒绝、被对方拒绝、等待对方验证 --> 用户资料界面
                 */
                itemLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FriendRecommendEntry entry = mList.get(position);
                        Intent intent;
                        if (entry.state.equals(FriendInvitation.INVITED.getValue())) {
                            //1.没同意也没拒绝时--> 是否同意界面
                            intent = new Intent(mContext, SearchFriendDetailActivity.class);
                            intent.putExtra("reason", item.reason);
                            intent.putExtra("position", position);
                            //2.已经添加的 --> 好友详情
                        } else if (entry.state.equals(FriendInvitation.ACCEPTED.getValue())) {
                            intent = new Intent(mContext, FriendInfoActivity.class);
                            intent.putExtra("fromContact", true);
                            //3.自己拒绝、被对方拒绝、等待对方验证 --> 用户资料界面
                        } else {
                            intent = new Intent(mContext, GroupNotFriendActivity.class);
                            intent.putExtra("reason", item.reason);
                        }
                        intent.putExtra(JGApplication.TARGET_ID, entry.username);
                        intent.putExtra(JGApplication.TARGET_APP_KEY, entry.appKey);
                        mFragment.startActivityForResult(intent, 0);
                    }
                });
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
        return convertView;
    }
    public void addNewRelation(String sendPhone) {
        //获得接收人电话
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
        String receivePhone = sharedPreferences.getString("phone","");
        int receiveType = sharedPreferences.getInt("type",0);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("receivePhone",receivePhone);
            jsonObject.put("sendPhone",sendPhone);
            jsonObject.put("receiveType",receiveType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String data = jsonObject.toString();
        new Thread() {
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://" + ip + ":8080/vhome/addNewRelation");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url, data);
                    //获取数据
                    final String data = util.getData(connection);
                    if (null != data) {
                        Log.e("ojbk","nb");
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