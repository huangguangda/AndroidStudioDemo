package cn.edu.gdmec.android.androidstudiodemo;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.androidstudiodemo.utils.AnalysisUtils;
import cn.edu.gdmec.android.androidstudiodemo.utils.MD5Utils;

/**
 * Created by Jack on 2018/4/10.
 */

public class FindPwdActivity extends AppCompatActivity {
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    private TextView tv_user_name;
    private EditText et_user_name;
    private EditText et_validate_name;
    private Button btn_validate;
    private TextView tv_reset_pwd;
    private EditText et_reset_pwd;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //找回密码和设置密保用得是同一个界面
        //获取从登录界面和设置界面传递过来的数据，由此判断显示和隐藏的部分
        from = getIntent().getStringExtra("from");
        init();
    }

    private void init() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_validate_name = (EditText) findViewById(R.id.et_validate_name);
        tv_reset_pwd = (TextView) findViewById(R.id.tv_reset_pwd);
        btn_validate = (Button) findViewById(R.id.btn_validate);
        tv_reset_pwd = findViewById(R.id.tv_reset_pwd);
        et_reset_pwd = (EditText) findViewById(R.id.et_reset_pwd);

        if ("security".equals(from)) {
            tv_main_title.setText("设置密保");
        } else {
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPwdActivity.this.finish();
            }
        });
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        // validate
        String validateName = et_validate_name.getText().toString().trim();
        if ("security".equals(from)) {  //设置密保
            if (TextUtils.isEmpty(validateName)) {
                Toast.makeText(this, "请输入要验证的姓名", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, "密保设置成功", Toast.LENGTH_SHORT).show();
                //保存到
                saveSecurity(validateName);
                FindPwdActivity.this.finish();
                return;
            }
        } else {   //找回密码
            final String userName = et_user_name.getText().toString().trim();
            String sp_security = readSecurity(userName);     //密保名
            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(FindPwdActivity.this, "请输入您的用户名", Toast.LENGTH_SHORT).show();
                return;
            } else if (!isExistUserName(userName)) {
                Toast.makeText(FindPwdActivity.this, "您的用户名不存在", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(validateName)) {
                Toast.makeText(FindPwdActivity.this, "请输入要验证的姓名", Toast.LENGTH_SHORT).show();
                return;
            } else if (!validateName.equals(sp_security)) {
                Toast.makeText(this, "输入的密保不正确", Toast.LENGTH_SHORT).show();
                return;
            } else {
                //输入密保正确，重新给用户设置一个密码
                tv_reset_pwd.setVisibility(View.VISIBLE);
                et_reset_pwd.setVisibility(View.VISIBLE);
                btn_validate.setText("确认");
                btn_validate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String resetPwd = et_reset_pwd.getText().toString().trim();
                        //暂时仅验证不为空
                        if (!TextUtils.isEmpty(resetPwd)) {
                            savePwd(userName, resetPwd);
                            FindPwdActivity.this.finish();
                        } else {
                            Toast.makeText(FindPwdActivity.this, "请输入要设置的新密码", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //tv_reset_pwd.setVisibility(View.VISIBLE);
                //tv_reset_pwd.setText("初始化密码：123456");
                //savePwd(userName);
            }
        }
    }

    /**
     * 保存初始化密码
     **/
    private void savePwd(String userName, String resetPwd) {
        String md5Psw = MD5Utils.md5(resetPwd);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName, md5Psw);
        editor.commit();
    }

    private boolean isExistUserName(String userName) {
        boolean hasUserName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPwd = sp.getString(userName, "");
        if (!TextUtils.isEmpty(spPwd)) {
            hasUserName = true;
        }
        return hasUserName;
    }

    /**
     * 保存密保名字
     **/
    private void saveSecurity(String validateName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this) + "_security", validateName);
        editor.commit();
    }

    /**
     * 读取密保
     **/
    private String readSecurity(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String security = sp.getString(userName + "_security", "");
        return security;
    }
}