package h.jpc.vhome.parents.fragment.community_hotspot;

import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class CommentPostActivity extends AppCompatActivity {

    private ExpandableListView commentListView;
    private TextView tvLoadMore;
    private EditText edtCommentContent;
    private Button btnCommentCommit;
    private MyClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        getViews();
        registerListener();
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
