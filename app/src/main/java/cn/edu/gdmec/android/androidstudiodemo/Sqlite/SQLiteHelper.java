package cn.edu.gdmec.android.androidstudiodemo.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jack on 2018/4/10.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    public static String DB_Name = "bxg.db";
    public static final String U_USER_INFO = "userInfo";

    public SQLiteHelper(Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 当这个SQLiteOpenHelper的子类类被实例化时会创建指定名的数据库，在onCreate中创建个人信息表
         * **/
        db.execSQL("CREATE TABLE IF NOT EXISTS " + U_USER_INFO + "( "
                + "_id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userName VARCHAR, "
                + "nickName VARCHAR, "
                + "sex VARCHAR, "
                + "signature VARCHAR, "
                + "qq VARCHAR "
                + ")");
    }

    /**
     * 当数据库版本号增加才会调用此方法
     **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + U_USER_INFO);
        onCreate(db);
    }
}