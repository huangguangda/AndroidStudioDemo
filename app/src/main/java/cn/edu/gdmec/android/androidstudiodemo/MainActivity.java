package cn.edu.gdmec.android.androidstudiodemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.androidstudiodemo.Fragment.CourseFragment;
import cn.edu.gdmec.android.androidstudiodemo.Fragment.ExercisesFragment;
import cn.edu.gdmec.android.androidstudiodemo.Fragment.MyinfoFragment;
import cn.edu.gdmec.android.androidstudiodemo.utils.AnalysisUtils;

/*
* FragmentManager manager = getSupportFragmentManager();
* FragmentTransaction transaction = manager.beginTransaction();
* transaction.add(R.id.main_body,new CourseFragment()).commit();
* */
public class MainActivity extends FragmentActivity implements View.OnClickListener{
    //来自main_title_bar.xml
    private TextView tv_main_title;//标题
    private TextView tv_back;//返回按钮
    private RelativeLayout title_bar;// android:id="@+id/title_bar"
    //来自activity_main.xml
    private RelativeLayout main_body;
    private TextView bottom_bar_text_course;
    private ImageView bottom_bar_image_course;
    private TextView bottom_bar_text_exercises;
    private ImageView bottom_bar_image_exercises;
    private TextView bottom_bar_text_myinfo;
    private ImageView bottom_bar_image_myinfo;
    private LinearLayout main_bottom_bar;
    //  android:id="@+id/main_body"
    //  android:id="@+id/bottom_bar_course_btn" 按钮
    // android:id="@+id/bottom_bar_text_course"
    // android:id="@+id/bottom_bar_image_course"
    // android:id="@+id/title_bar";
    /*main_exercises_icon.png、
    main_course_icon.png、
    main_my_icon.png
    main_exercises_icon_selected.png、
    main_course_icon_selected.png、
    main_my_icon_selected.png*/
    //private View mCourseBtn,mExercisesBtn,mMyInfoBtn;
    private RelativeLayout bottom_bar_course_btn;
    private RelativeLayout bottom_bar_exercises_btn;
    private RelativeLayout bottom_bar_myinfo_btn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            boolean isLogin = data.getBooleanExtra("isLogin", false);
            //从登录活动获得isLogin==true,从设置活动获得isLogin==false，他们的请求码都是1
            //之后还可以根据请求码和结果码完成更多需求
            if (isLogin) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body, new CourseFragment()).commit();
                clearBottomImageState();
                setSelectedStatus(0);
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body, new MyinfoFragment()).commit();
                clearBottomImageState();
                setSelectedStatus(2);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initNavigation();
        initBody();
        initBottomBar();
        setInitStatus();
    }

    private void initNavigation() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
    }

    private void initBody() {
        main_body = (RelativeLayout) findViewById(R.id.main_body);
    }

    private void initBottomBar() {
        bottom_bar_text_course = (TextView) findViewById(R.id.bottom_bar_text_course);
        bottom_bar_image_course = (ImageView) findViewById(R.id.bottom_bar_image_course);
        bottom_bar_course_btn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        bottom_bar_text_exercises = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        bottom_bar_image_exercises = (ImageView) findViewById(R.id.bottom_bar_image_exercises);
        bottom_bar_exercises_btn = (RelativeLayout) findViewById(R.id.bottom_bar_exercises_btn);
        bottom_bar_text_myinfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);
        bottom_bar_image_myinfo = (ImageView) findViewById(R.id.bottom_bar_image_myinfo);
        bottom_bar_myinfo_btn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);
        main_bottom_bar = (LinearLayout) findViewById(R.id.main_bottom_bar);
        setListener();
    }

    private void setListener() {
        for (int i = 0; i < main_bottom_bar.getChildCount(); i++) {
            main_bottom_bar.getChildAt(i).setOnClickListener(this);
        }
    }

    private void setInitStatus() {
        clearBottomImageState();
        setSelectedStatus(0);
        getSupportFragmentManager().beginTransaction().add(R.id.main_body, new CourseFragment()).commit();
    }

    private void setSelectedStatus(int index) {
        switch (index) {
            case 0:
                //mCourseBtn.setSelected(true);
                bottom_bar_image_course.setImageResource(R.drawable.main_course_icon_selected);
                bottom_bar_text_course.setTextColor(Color.parseColor("#0097f7"));
                tv_main_title.setText("博学谷课程");
                break;
            case 1:
                //mExercisesBtn.setSelected(true);
                bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                bottom_bar_text_exercises.setTextColor(Color.parseColor("#0097f7"));
                tv_main_title.setText("博学谷习题");
                break;
            case 2:
                //mMyInfoBtn.setSelected(true);
                bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon_selected);
                bottom_bar_text_myinfo.setTextColor(Color.parseColor("#0097f7"));
                title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷");
                break;
        }
    }

    private void clearBottomImageState() {
        bottom_bar_text_course.setTextColor(Color.parseColor("#666666"));
        bottom_bar_text_exercises.setTextColor(Color.parseColor("#666666"));
        bottom_bar_text_myinfo.setTextColor(Color.parseColor("#666666"));

        bottom_bar_image_course.setImageResource(R.drawable.main_course_icon);
        bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon);
        bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_bar_course_btn:
                clearBottomImageState();
                /**  replacing instead of adding **/
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body, new CourseFragment()).commit();
                setSelectedStatus(0);
                break;
            case R.id.bottom_bar_exercises_btn:
                clearBottomImageState();
                setSelectedStatus(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body, new ExercisesFragment()).commit();
                break;
            case R.id.bottom_bar_myinfo_btn:
                clearBottomImageState();
                setSelectedStatus(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body, new MyinfoFragment()).commit();
                break;
        }
    }

    protected long exitTime;//记录第一次点击的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出博学谷", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
                if (AnalysisUtils.readLoginStatus(this)) {
                    //已登陆的话，清除登陆状态
                    AnalysisUtils.clearLoginStatus(this);
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}