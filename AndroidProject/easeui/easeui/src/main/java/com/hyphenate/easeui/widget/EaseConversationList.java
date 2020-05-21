package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.MyApp;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseConversationAdapter;
import com.hyphenate.easeui.utils.ConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EaseConversationList extends ListView{
    
    protected int primaryColor;
    protected int secondaryColor;
    protected int timeColor;
    protected int primarySize;
    protected int secondarySize;
    protected float timeSize;
    private Handler handler1;
    private int userTypes;
    private String nikeName;
    private List<Integer> typeList;
    private List<String> nikeNames;

    protected final int MSG_REFRESH_ADAPTER_DATA = 0;
    
    protected Context context;
    protected EaseConversationAdapter adapter;
    protected List<EMConversation> conversations = new ArrayList<EMConversation>();
    protected List<EMConversation> passedListRef = null;
    
    
    public EaseConversationList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public EaseConversationList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    
    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseConversationList);
        primaryColor = ta.getColor(R.styleable.EaseConversationList_cvsListPrimaryTextColor, getResources().getColor(R.color.list_itease_primary_color));
        secondaryColor = ta.getColor(R.styleable.EaseConversationList_cvsListSecondaryTextColor, getResources().getColor(R.color.list_itease_secondary_color));
        timeColor = ta.getColor(R.styleable.EaseConversationList_cvsListTimeTextColor, getResources().getColor(R.color.list_itease_secondary_color));
        primarySize = ta.getDimensionPixelSize(R.styleable.EaseConversationList_cvsListPrimaryTextSize, 0);
        secondarySize = ta.getDimensionPixelSize(R.styleable.EaseConversationList_cvsListSecondaryTextSize, 0);
        timeSize = ta.getDimension(R.styleable.EaseConversationList_cvsListTimeTextSize, 0);
        
        ta.recycle();
        
    }

    public void init(List<EMConversation> conversationList){
        this.init(conversationList, null);
    }

    public void init(List<EMConversation> conversationList, EaseConversationListHelper helper){
        conversations = conversationList;
        if(helper != null){
            this.conversationListHelper = helper;
        }

        adapter = new EaseConversationAdapter(context, 0, conversationList);
        adapter.setCvsListHelper(conversationListHelper);
        adapter.setPrimaryColor(primaryColor);
        adapter.setPrimarySize(primarySize);
        adapter.setSecondaryColor(secondaryColor);
        adapter.setSecondarySize(secondarySize);
        adapter.setTimeColor(timeColor);
        adapter.setTimeSize(timeSize);
        setAdapter(adapter);
    }
    
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
            case MSG_REFRESH_ADAPTER_DATA:
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
            }
        }
    };

    public EMConversation getItem(int position) {
        return (EMConversation)adapter.getItem(position);
    }
    
    public void refresh() {
    	if(!handler.hasMessages(MSG_REFRESH_ADAPTER_DATA)){
    		handler.sendEmptyMessage(MSG_REFRESH_ADAPTER_DATA);
    	}
    }
    
    public void filter(CharSequence str) {
        adapter.getFilter().filter(str);
    }


    private EaseConversationListHelper conversationListHelper;


    public interface EaseConversationListHelper{
        /**
         * set content of second line
         * @param lastMessage
         * @return
         */
        String onSetItemSecondaryText(EMMessage lastMessage);
    }
    public void setConversationListHelper(EaseConversationListHelper helper){
        conversationListHelper = helper;
    }
    public void userType(String username)  {
        //准备数据
        final String data = username;
        new Thread() {
            @Override
            public void run() {
                String ip = (new MyApp()).ip;
                try {
                    URL url = new URL("http://" + ip + ":8080/vhome/ReturnType");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url, data);
                    //获取数据
                    String data = util.getData(connection);
                    util.sendMsg(data,4,handler1);
                    Log.e("conver_adapter_type","userTypes"+data);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void getNickName(int type, final String username) throws JSONException {
        //准备数据
        JSONObject json = new JSONObject();
        json.put("phone", username);
        json.put("type", type);
        final String data = json.toString();

        new Thread() {
            @Override
            public void run() {
                String ip = (new MyApp()).ip;
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/searchUserInfo");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    JSONObject jsonObject = new JSONObject(data);
                    String nickName = jsonObject.getString("nikeName");
                    Log.e("listsize---nickName",nickName);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //            userType(username);
//            getNickName(typeList.get(i),username);
//            final ViewHolder finalHolder = holder;
//            handler1 = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    switch (msg.what) {
//                        case 4:
//                            Bundle c = msg.getData();
//                            String data1 = c.getString("data");
//                            userTypes = Integer.parseInt(data1);
//                            typeList.add(userTypes);
//                            break;
//                        case 5:
//                            for (int i=0;i<typeList.size();i++){
//                                try {
//                                    getNickName(typeList.get(i),username);
//                                    Log.e("listsize---",typeList.get(i)+"");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
////                            Bundle b = msg.getData();
////                            String data = b.getString("data");
//                            break;
//                    }
//                }
//            };
}
