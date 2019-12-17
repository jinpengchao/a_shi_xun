package h.jpc.vhome.chat.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.utils.DialogCreator;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ThreadUtil;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.chat.utils.citychoose.view.SelectAddressDialog;
import h.jpc.vhome.chat.utils.citychoose.view.myinterface.SelectAddressInterface;
import h.jpc.vhome.chat.utils.photochoose.ChoosePhoto;
import h.jpc.vhome.chat.utils.photochoose.PhotoUtils;
import h.jpc.vhome.parents.fragment.MyselfFragment;
import h.jpc.vhome.user.entity.EventBean;
import h.jpc.vhome.util.ConnectionUtil;

/**
 * Created by ${chenyn} on 2017/2/23.
 */

public class PersonalActivity extends BaseActivity implements SelectAddressInterface, View.OnClickListener {

    public static final int SIGN = 1;
    public static final String SIGN_KEY = "sign_key";

    public static final int NICK_NAME = 4;
    public static final String NICK_NAME_KEY = "nick_name_key";

    private static final int SIGN_COUNT = 250;
    private static final int NICK_COUNT = 64;

    private RelativeLayout mRl_cityChoose;
    private TextView mTv_city;
    private SelectAddressDialog dialog;
    private RelativeLayout mRl_gender;
    private RelativeLayout mRl_birthday;

    private TextView mTv_birthday;
    private TextView mTv_gender;
    private RelativeLayout mSign;
    private TextView mTv_sign;
    private RelativeLayout mRl_nickName;


    Intent intent;
    private TextView mTv_nickName;
    private ImageView mIv_photo;
    private ChoosePhoto mChoosePhoto;
    private UserInfo mMyInfo;
    private TextView mTv_userName;
    private RelativeLayout mRl_zxing;
    private Button commit_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initView();
        initListener();
        initData();
        SharedPreferences sp = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
        String area = sp.getString("area","");
        mTv_city.setText(area);
    }

    private void initData() {
        final Dialog dialog = DialogCreator.createLoadingDialog(PersonalActivity.this,
                PersonalActivity.this.getString(R.string.jmui_loading));
        dialog.show();
        mMyInfo = JMessageClient.getMyInfo();
        if (mMyInfo != null) {
            mTv_nickName.setText(mMyInfo.getNickname());
            SharePreferenceManager.setRegisterUsername(mMyInfo.getNickname());
            mTv_userName.setText("手机:" + mMyInfo.getUserName());
            mTv_sign.setText(mMyInfo.getSignature());
            UserInfo.Gender gender = mMyInfo.getGender();
            if (gender != null) {
                if (gender.equals(UserInfo.Gender.male)) {
                    mTv_gender.setText("男");
                } else if (gender.equals(UserInfo.Gender.female)) {
                    mTv_gender.setText("女");
                } else {
                    mTv_gender.setText("保密");
                }
            }
            long birthday = mMyInfo.getBirthday();
            if (birthday == 0) {
                mTv_birthday.setText("");
            } else {
                Date date = new Date(mMyInfo.getBirthday());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                mTv_birthday.setText(format.format(date));
            }
            mTv_city.setText(mMyInfo.getAddress());
            mMyInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage, Bitmap avatarBitmap) {
                    if (responseCode == 0) {
                        mIv_photo.setImageBitmap(avatarBitmap);
                    } else {
                        mIv_photo.setImageResource(R.drawable.rc_default_portrait);
                    }
                }
            });
            dialog.dismiss();
        }
    }

    private void initListener() {
        mRl_cityChoose.setOnClickListener(this);
        mRl_birthday.setOnClickListener(this);
        mRl_gender.setOnClickListener(this);
        mSign.setOnClickListener(this);
        mRl_nickName.setOnClickListener(this);
        mIv_photo.setOnClickListener(this);
        mRl_zxing.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
    }

    private void initView() {
//        initTitle(true, true, "个人信息", "", false, "");
        commit_btn = (Button)findViewById(R.id.jmui_commit_btn);
        mRl_cityChoose = (RelativeLayout) findViewById(R.id.rl_cityChoose);
        mTv_city = (TextView) findViewById(R.id.tv_city);
        mRl_gender = (RelativeLayout) findViewById(R.id.rl_gender);
        mRl_birthday = (RelativeLayout) findViewById(R.id.rl_birthday);
        mTv_birthday = (TextView) findViewById(R.id.tv_birthday);
        mTv_gender = (TextView) findViewById(R.id.tv_gender);
        mSign = (RelativeLayout) findViewById(R.id.sign);
        mTv_sign = (TextView) findViewById(R.id.tv_sign);
        mRl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        mTv_nickName = (TextView) findViewById(R.id.tv_nickName);
        mIv_photo = (ImageView) findViewById(R.id.iv_photo);
        mTv_userName = (TextView) findViewById(R.id.tv_userName);
        mRl_zxing = (RelativeLayout) findViewById(R.id.rl_zxing);

        mChoosePhoto = new ChoosePhoto();
        mChoosePhoto.setPortraitChangeListener(PersonalActivity.this, mIv_photo);

    }
    @Override
    public void setAreaString(String area) {
        mTv_city.setText(area);
    }

    @Override
    public void setTime(String time) {
    }

    @Override
    public void setGender(String gender) {
        mTv_gender.setText(gender);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(PersonalActivity.this, NickSignActivity.class);
        switch (v.getId()) {
            case R.id.iv_photo:
                //头像
                if ((ContextCompat.checkSelfPermission(PersonalActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(PersonalActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(PersonalActivity.this, "请在应用管理中打开“读写存储”和“相机”访问权限！", Toast.LENGTH_SHORT).show();
                }
                mChoosePhoto.setInfo(PersonalActivity.this, true);
                mChoosePhoto.showPhotoDialog(PersonalActivity.this);
                break;
            case R.id.rl_nickName:
                //昵称
                intent.putExtra(NickSignActivity.TYPE, NickSignActivity.Type.PERSON_NICK);
                intent.putExtra(NickSignActivity.COUNT, NICK_COUNT);
                intent.putExtra(NickSignActivity.DESC, mMyInfo.getNickname());
                startActivityForResult(intent, NICK_NAME);
                break;
            case R.id.sign:
                //签名
                intent.putExtra(NickSignActivity.TYPE, NickSignActivity.Type.PERSON_SIGN);
                intent.putExtra(NickSignActivity.COUNT, SIGN_COUNT);
                intent.putExtra(NickSignActivity.DESC, mMyInfo.getSignature());
                startActivityForResult(intent, SIGN);
                break;
            case R.id.rl_gender:
                //弹出性别选择器
                dialog = new SelectAddressDialog(PersonalActivity.this);
                dialog.showGenderDialog(PersonalActivity.this, mMyInfo);
                break;
            case R.id.rl_birthday:
                //弹出时间选择器选择生日
                TimePickerView timePickerView = new TimePickerView.Builder(PersonalActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(final Date date, View v) {
                        mMyInfo.setBirthday(date.getTime());
                        JMessageClient.updateMyInfo(UserInfo.Field.birthday, mMyInfo, new BasicCallback() {
                            @Override
                            public void gotResult(int responseCode, String responseMessage) {
                                if (responseCode == 0) {
                                    mTv_birthday.setText(getDataTime(date));
                                    Toast.makeText(PersonalActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PersonalActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setOutSideCancelable(true)
                        .isCyclic(true)
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setSubmitColor(Color.GRAY)//确定按钮文字颜色
                        .setCancelColor(Color.GRAY)//取消按钮文字颜色
                        .isCenterLabel(false)
                        .build();
                timePickerView.show();
                break;
            case R.id.rl_cityChoose:
                //点击选择省市
                dialog = new SelectAddressDialog(PersonalActivity.this,
                        PersonalActivity.this, SelectAddressDialog.STYLE_THREE, null, mMyInfo);
                dialog.showDialog();
                break;
            case R.id.rl_zxing:
                //二维码
                Intent intent = new Intent(PersonalActivity.this, Person2CodeActivity.class);
                intent.putExtra("appkey", mMyInfo.getAppKey());
                intent.putExtra("username", mMyInfo.getUserName());
                if (mMyInfo.getAvatarFile() != null) {
                    intent.putExtra("avatar", mMyInfo.getAvatarFile().getAbsolutePath());
                }
                startActivity(intent);
                break;
            case R.id.jmui_commit_btn:
                SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
                int type = sp.getInt("type",0);
                if(type==0){
                    String nickName = mTv_nickName.getText().toString();
                    String sex = mTv_gender.getText().toString();
                    String area = mTv_city.getText().toString();
                    EventBean stiEvent = new EventBean(nickName,sex,area);
                    //发布粘性事件
                    EventBus.getDefault().postSticky(stiEvent);
                    finish();
                }
                if(type == 1){

                }

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            switch (resultCode) {
                case SIGN:
                    final String sign = bundle.getString(SIGN_KEY);
                    ThreadUtil.runInThread(new Runnable() {
                        @Override
                        public void run() {
                            mMyInfo.setSignature(sign);
                            JMessageClient.updateMyInfo(UserInfo.Field.signature, mMyInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int responseCode, String responseMessage) {
                                    if (responseCode == 0) {
                                        mTv_sign.setText(sign);
                                        ToastUtil.shortToast(PersonalActivity.this, "更新成功");
                                    } else {
                                        ToastUtil.shortToast(PersonalActivity.this, "更新失败");
                                    }
                                }
                            });
                        }
                    });
                    break;
                case NICK_NAME:
                    final String nick = bundle.getString(NICK_NAME_KEY);
                    SharedPreferences sp = getSharedPreferences((new MyApp()).getPathInfo(),MODE_PRIVATE);
                    String phone = sp.getString("phone","");
                    String id = sp.getString("id","");
                    int type = sp.getInt("type",0);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id",id);
                        jsonObject.put("phone",phone);
                        jsonObject.put("type",type);
                        jsonObject.put("data",nick);
                        jsonObject.put("flag","nickName");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String nickName = jsonObject.toString();
                    ThreadUtil.runInThread(new Runnable() {
                        @Override
                        public void run() {
                            mMyInfo.setNickname(nick);
                            JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int responseCode, String responseMessage) {
                                    if (responseCode == 0) {
                                        mTv_nickName.setText(nick);
                                        ToastUtil.shortToast(PersonalActivity.this, "更新成功");
                                    } else {
                                        ToastUtil.shortToast(PersonalActivity.this, "更新失败,请正确输入");
                                    }
                                }
                            });
                        }
                    });
                    new Thread(){
                        @Override
                        public void run() {
                            String ip = (new MyApp()).getIp();
                            try {
                                URL url = new URL("http://"+ip+":8080/vhome/changeInfo");
                                ConnectionUtil util = new ConnectionUtil();
                                //发送数据
                                HttpURLConnection connection = util.sendData(url,nickName);
                                //获取数据
                                final String data = util.getData(connection);
                                if(null!=data){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("名称修改成功","!!!");
                                            SharedPreferences sp = getSharedPreferences("parentUserInfo",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString("nickName",nick);
                                            editor.commit();
                                        }
                                    });
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                default:
                    break;
            }
        }
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                mChoosePhoto.photoUtils.onActivityResult(PersonalActivity.this, requestCode, resultCode, data);
                break;
        }
    }
    public String getDataTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
