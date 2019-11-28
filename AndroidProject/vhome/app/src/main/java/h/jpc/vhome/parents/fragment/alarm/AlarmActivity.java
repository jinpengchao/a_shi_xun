package h.jpc.vhome.parents.fragment.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

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
    DrawerLayout drawerLayout;
    Context context = AlarmActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        recyclerView = findViewById(R.id.clock_list);
        drawerLayout = findViewById(R.id.layout1);
        Intent intent1 = new Intent(AlarmActivity.this, ReadAlarm.class);
        startActivity(intent1);
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
        recyclerView.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
            AlarmActivity.this.finish();
    }
}
