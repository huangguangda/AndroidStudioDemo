package cn.edu.gdmec.android.androidstudiodemo.utils;

/**
 * Created by ASUS on 2018/4/9.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class AnalysisUtils {
    //登陆状态
    public static boolean readLoginStatus(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false);
        return isLogin;
    }

    //获取正在登录的登录名
    public static String readLoginUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String userName = sp.getString("loginUserName", "");
        return userName;
    }

    public static void clearLoginStatus(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器s
        editor.putBoolean("isLogin", false);//清除登陆状态
        editor.putString("loginUserName", "");//清除登陆名
        editor.commit();//提交修改
    }
}