package h.jpc.vhome;

import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.children.ChildrenMain;
import h.jpc.vhome.parents.ParentMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button parent;
    private Button children;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		//今天好心情
        //测试程序，便于效果预览，登录写完之后就能删了
        parent = findViewById(R.id.parent);
        children = findViewById(R.id.children);
		//这是王的测试行
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

    }
}
