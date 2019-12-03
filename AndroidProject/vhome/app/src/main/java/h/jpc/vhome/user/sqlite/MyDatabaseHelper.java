package h.jpc.vhome.user.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(phone char(11),id varchar(11),nikeName varchar(20),sex varchar(4),area varchar(30),headerImg varchar(50),type int)";
        db.execSQL(sql);
        Toast.makeText(
                context,
                "创建数据库表成功",
                Toast.LENGTH_SHORT
        ).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

