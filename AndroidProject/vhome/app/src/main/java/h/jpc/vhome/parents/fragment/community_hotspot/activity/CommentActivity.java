package h.jpc.vhome.parents.fragment.community_hotspot.activity;

import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.parents.fragment.community_hotspot.entity.CommentDetailBean;
import h.jpc.vhome.util.ConnectionUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private ExpandableListView commentListView;
    private TextView tvLoadMore;
    private EditText edtCommentContent;
    private Button btnCommentCommit;
    private MyClickListener listener;
    private List<CommentDetailBean> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        getViews();
        registerListener();

//        getCommentData();
    }

    private void getCommentData() {
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/xxx");
                    ConnectionUtil connectionUtil = new ConnectionUtil();
                    String data = connectionUtil.getData(url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getViews() {
        commentListView = findViewById(R.id.elview_comment);
        tvLoadMore = findViewById(R.id.tv_post_loadmore);
        edtCommentContent = findViewById(R.id.edt_comment_content);
        btnCommentCommit = findViewById(R.id.btn_comment_commit);
    }

    private void registerListener() {
        listener = new MyClickListener();
        tvLoadMore.setOnClickListener(listener);
        btnCommentCommit.setOnClickListener(listener);
    }
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_post_loadmore:
                    break;
            }
        }
    }
}
