package h.jpc.vhome.parents.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.ContactsActivity;
import h.jpc.vhome.chat.activity.PersonalActivity;
import h.jpc.vhome.user.entity.ParentUserInfo;
import h.jpc.vhome.util.ConnectionUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

public class MyselfFragment extends Fragment {
    private ImageView blurImageView;
    private ImageView header;
    private TextView nikeName;
    private TextView ids;
    private TextView areas;
    private ImageView sexs;
    private SharedPreferences sp2;
    private RelativeLayout myRelation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_myself,null);
        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        header = (ImageView) view.findViewById(R.id.parent_head);
        nikeName = (TextView) view.findViewById(R.id.parent_name);
        ids = (TextView) view.findViewById(R.id.parent_id);
        areas = (TextView) view.findViewById(R.id.parent_area);
        sexs = (ImageView) view.findViewById(R.id.parent_sex);
        myRelation = view.findViewById(R.id.my_relation);
        myRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ContactsActivity.class));
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PersonalActivity.class));
            }
        });
        initMyselfInfo();
        initData();
        return view;
    }
    private void initData(){
        Glide.with(getActivity()).load(R.mipmap.header)
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);
        Glide.with(getActivity()).load(R.mipmap.header)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(header);
    }
    public void initMyselfInfo(){
            Log.e("缓存的个人信息","old");
            sp2 = getActivity().getSharedPreferences("parentUserInfo", MODE_PRIVATE);
            String id = sp2.getString("id","");
            String nickName = sp2.getString("nickName","");
            String sex = sp2.getString("sex","");
            String area = sp2.getString("area","");
            String achieve = sp2.getString("achieve","");
            String personalWord = sp2.getString("personalWord","");
            String headImg = sp2.getString("headImg","");
            nikeName.setText(nickName);
            ids.setText(id);
            areas.setText(area);
    }

}
