package h.jpc.vhome.chat.utils.photochoose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.PersonalActivity;
import h.jpc.vhome.chat.application.JGApplication;
import h.jpc.vhome.chat.utils.HandleResponseCode;
import h.jpc.vhome.chat.utils.SharePreferenceManager;
import h.jpc.vhome.chat.utils.ToastUtil;
import h.jpc.vhome.children.fragment.MyselfFragment;
import h.jpc.vhome.user.entity.User;
import h.jpc.vhome.util.ConnectionUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ${chenyn} on 2017/3/3.
 */

public class ChoosePhoto {
    public MyselfFragment myselfFragment;
    public PhotoUtils photoUtils;
    private BottomMenuDialog mDialog;
    private Activity mContext;
    private boolean isFromPersonal;
    public static String fileName;
    public void setInfo(PersonalActivity personalActivity, boolean isFromPersonal) {
        this.mContext = personalActivity;
        this.isFromPersonal = isFromPersonal;
    }

    public void showPhotoDialog(final Context context) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

        mDialog = new BottomMenuDialog(context);
        mDialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                photoUtils.takePicture((Activity) context);
            }
        });
        mDialog.setMiddleListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                photoUtils.selectPicture((Activity) context);
            }
        });
        mDialog.show();
    }
    public void uploadHeaderImg(String pathName){
        String url = "http://"+(new MyApp()).getIp()+":8080/vhome/uploadHeader";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("msg","上传头像");
        params.addBodyParameter("imgs",new File(pathName));
        params.setMultipart(true);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("upLoadImg","头像上传成功！");
                File file = new File(pathName);
                if (file.exists()) {
                    file.delete();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("upLoadImg","头像上传失败！");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
    public void saveHeaderImg(String path){
        SharedPreferences sp = mContext.getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        int type = sp.getInt("type",0);
        String[] file = path.split("/");
        fileName = file[file.length-1];
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("type",type);
            jsonObject.put("fileName",fileName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String dataTo = jsonObject.toString();
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/saveheaderImg");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,dataTo);
                    //获取数据
                    final String dataFrom = util.getData(connection);
                    if(null!=dataFrom){
                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("headImg",dataFrom);
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
    }
    public void setPortraitChangeListener(final Context context, final ImageView iv_photo) {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(final Uri uri) {
                Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
                iv_photo.setImageBitmap(bitmap);
                SharePreferenceManager.setCachedAvatarPath(uri.getPath());

                if (isFromPersonal) {
                    h.jpc.vhome.chat.utils.dialog.LoadDialog.show(context);
                    JMessageClient.updateUserAvatar(new File(uri.getPath()), new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            h.jpc.vhome.chat.utils.dialog.LoadDialog.dismiss(context);
                            if (responseCode == 0) {
                                ToastUtil.shortToast(mContext, "更新成功");
                                uploadHeaderImg(uri.getPath());
                                Log.e("path",uri.getPath());
                            } else {
                                ToastUtil.shortToast(mContext, "更新失败" + responseMessage);
                            }
                        }
                    });
                    saveHeaderImg(uri.getPath());
                }
            }

            @Override
            public void onPhotoCancel() {
            }
        });
    }

    //更新群组头像
    public void setGroupAvatarChangeListener(final Activity context, final long groupId) {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(final Uri uri) {
                h.jpc.vhome.chat.utils.dialog.LoadDialog.show(context);
                JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, GroupInfo groupInfo) {
                        if (i == 0) {
                            groupInfo.updateAvatar(new File(uri.getPath()), "", new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    h.jpc.vhome.chat.utils.dialog.LoadDialog.dismiss(context);
                                    if (i == 0) {
                                        Intent intent = new Intent();
                                        intent.putExtra("groupAvatarPath", uri.getPath());
                                        context.setResult(Activity.RESULT_OK, intent);
                                        ToastUtil.shortToast(context, "更新成功");
                                        context.finish();
                                    } else {
                                        ToastUtil.shortToast(context, "更新失败");
                                        context.finish();
                                    }
                                }
                            });
                        } else {
                            HandleResponseCode.onHandle(context, i, false);
                        }
                    }
                });

            }

            @Override
            public void onPhotoCancel() {
            }
        });
    }

    public void getCreateGroupAvatar(ImageView iv_groupAvatar) {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri) {
                iv_groupAvatar.setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
                JGApplication.groupAvatarPath = uri.getPath();
            }

            @Override
            public void onPhotoCancel() {

            }
        });
    }

}
