package cn.edu.gdmec.android.androidstudiodemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.androidstudiodemo.utils.AnalysisUtils;
import cn.edu.gdmec.android.androidstudiodemo.utils.MD5Utils;

/**
 * Created by Jack on 2018/4/10.
 */

public class ModifyPwdActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back;
    private EditText et_original_pwd;
    private EditText et_new_pwd;
    private EditText et_new_pwd_again;
    private Button btn_save;
    private String originalPwd;
    private String newPwd;
    private String newPwdAgain;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        userName = AnalysisUtils.readLoginUserName(this);
        init();
    }
    private void init() {
        tv_main_title = ((TextView) findViewById(R.id.tv_main_title));
        tv_main_title.setText("修改密码");
        tv_back = ((TextView) findViewById(R.id.tv_back));
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyPwdActivity.this.finish();
            }
        });
        et_original_pwd = (EditText)findViewById(R.id.et_original_pwd);
        et_new_pwd = (EditText)findViewById(R.id.et_new_pwd);
        et_new_pwd_again = (EditText)findViewById(R.id.et_new_pwd_again);
        btn_save = ((Button) findViewById(R.id.btn_save));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditString();
                if(TextUtils.isEmpty(originalPwd)){
                    Toast.makeText(ModifyPwdActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(newPwd)){
                    Toast.makeText(ModifyPwdActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(newPwdAgain)){
                    Toast.makeText(ModifyPwdActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!MD5Utils.md5(originalPwd).equals(readPwd())){
                    Toast.makeText(ModifyPwdActivity.this, "输入密码与原始密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else if(MD5Utils.md5(newPwd).equals(readPwd())){
                    Toast.makeText(ModifyPwdActivity.this, "输入新密码与原始密码不能一致", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!newPwd.equals(newPwdAgain)){
                    Toast.makeText(ModifyPwdActivity.this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(ModifyPwdActivity.this, "新密码设置成功", Toast.LENGTH_SHORT).show();
                    modifyPwd(newPwd);
                    Intent intent = new Intent(ModifyPwdActivity.this,LoginActivity.class);
                    startActivity(intent);
                    SettingActivity.instance.finish() ;//关闭设置页面 create field 'instance'
                    ModifyPwdActivity.this.finish(); //关闭当前页面
                }
            }
        });
    }

    private void modifyPwd(String newPwd) {
        String md5Pwd = MD5Utils.md5(newPwd);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,md5Pwd);
        editor.commit();
    }

    private String readPwd() {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPwd = sp.getString(userName, "");
        return spPwd;
    }

    private void getEditString() {
        originalPwd = et_original_pwd.getText().toString().trim();
        newPwd = et_new_pwd.getText().toString().trim();
        newPwdAgain = et_new_pwd_again.getText().toString().trim();
    }
}