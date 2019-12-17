package h.jpc.vhome.parents.fragment.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import h.jpc.vhome.R;

public class AlarmActivity extends AppCompatActivity {
    public static List<Clock> list = new ArrayList<>();
    public static TimeAdapter timeAdapter;

    RecyclerView recyclerView;
    RelativeLayout drawerLayout;
    Context context = AlarmActivity.this;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        textView = findViewById(R.id.warnInfo1);
        recyclerView = findViewById(R.id.clock_list);
        drawerLayout = findViewById(R.id.layout1);
        Intent intent1 = new Intent(AlarmActivity.this, ReadAlarm.class);
        startActivity(intent1);
        initRecyclerView();
        initRecyclerView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initRecyclerView();
    }
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        timeAdapter = new TimeAdapter(list, context);
        if (list.size()==0){
            textView.setText("");
        }else{
            textView.setText("您还没有收到小提示哦~");

        }
        recyclerView.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
            AlarmActivity.this.finish();
    }
}
