package h.jpc.vhome;

import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.children.ChildrenMain;
import h.jpc.vhome.parents.ParentMain;
import h.jpc.vhome.user.FindBackPwdActivity;
import h.jpc.vhome.user.LoginByCodeActivity;
import h.jpc.vhome.user.RegisterActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private long exitTime = 0;
    private Button parent;
    private Button children;
    private TextView register;
    private TextView use_code;
    private ToggleButton togglePwd;
    private TextView etPwd;
    private TextView findBackPwd;
    private ImageView mainBackground;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = this.getActionBar();

        getView();
        Glide.with(this).load(R.mipmap.mainbk).centerCrop().into(mainBackground);
        //测试程序，便于效果预览，登录写完之后就能删了
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ParentMain.class);
                startActivity(intent);
            }
        });
        children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChildrenMain.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(
                        MainActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
            }
        });
        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        use_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(
                        MainActivity.this,
                        LoginByCodeActivity.class);
                startActivity(intent);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
                finish();
            }
        });
        findBackPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(
                        MainActivity.this,
                        FindBackPwdActivity.class);
                startActivity(intent);
                //应用页面跳转动画
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
            }
        });
    }
    public void getView(){
        parent = findViewById(R.id.parent);
        children = findViewById(R.id.children);
        register = findViewById(R.id.register);
        etPwd = findViewById(R.id.etPwd);
        togglePwd = findViewById(R.id.togglePwd);
        use_code = findViewById(R.id.use_code);
        findBackPwd = findViewById(R.id.findBackPwd);
        mainBackground = findViewById(R.id.main_background);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
