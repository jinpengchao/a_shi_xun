package h.jpc.vhome.children.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import h.jpc.vhome.R;
import h.jpc.vhome.chat.activity.ContactsActivity;
import h.jpc.vhome.chat.activity.PersonalActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyselfFragment extends Fragment {
    private ImageView blurImageView;
    public static ImageView header;
    private TextView myRelation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_children_myself, null);
        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        myRelation = view.findViewById(R.id.my_relation);
        header = (ImageView) view.findViewById(R.id.children_head);
        initData();
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
        return view;
    }
    private void initData() {
        Glide.with(getActivity()).load(R.mipmap.sss)
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);
        Glide.with(getActivity()).load(R.mipmap.sss)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(header);
    }
}
