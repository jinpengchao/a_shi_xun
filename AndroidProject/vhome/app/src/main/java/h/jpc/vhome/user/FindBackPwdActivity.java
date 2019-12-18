package h.jpc.vhome.user;

import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FindBackPwdActivity extends AppCompatActivity {
    private ImageView backToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_back_pwd);

        backToLogin = findViewById(R.id.backToLogin);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
            }
        });
    }
}
