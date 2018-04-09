package cn.edu.gdmec.android.androidstudiodemo;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edu.gdmec.android.androidstudiodemo.Fragment.CourseFragment;
import cn.edu.gdmec.android.androidstudiodemo.Fragment.ExercisesFragment;
import cn.edu.gdmec.android.androidstudiodemo.Fragment.MyinfoFragment;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setMain();
    }
    //用于打开初始页面
    private void setMain() {
        //getSupportFragmentManager() -> beginTransaction() -> add -> (R.id.main_boy,显示课程 new CourseFragment()
        this.getSupportFragmentManager().beginTransaction().add(R.id.main_body,new CourseFragment()).commit();
    }

    private void setSelectStatus(int index) {
        switch (index){
            case 0:
                //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                bottom_bar_image_course.setImageResource(R.drawable.main_course_icon_selected);
                bottom_bar_text_course.setTextColor(Color.parseColor("#0097F7"));

                bottom_bar_text_exercises.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_myinfo.setTextColor(Color.parseColor("#666666"));

                bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon);
                bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon);
                break;
            case 1:
                bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                bottom_bar_text_exercises.setTextColor(Color.parseColor("#0097F7"));

                bottom_bar_text_course.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_myinfo.setTextColor(Color.parseColor("#666666"));

                bottom_bar_image_course.setImageResource(R.drawable.main_course_icon);
                bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon);
                break;
            case 2:
                bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon_selected);
                bottom_bar_text_myinfo.setTextColor(Color.parseColor("#0097F7"));

                bottom_bar_text_course.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_exercises.setTextColor(Color.parseColor("#666666"));

                bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon);
                bottom_bar_image_course.setImageResource(R.drawable.main_course_icon);
                break;
        }
    }

    private void initView() {
        //标题显示
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        title_bar=findViewById(R.id.title_bar);
        //底部导航栏
        main_body=findViewById(R.id.main_body);
        bottom_bar_text_course=findViewById(R.id.bottom_bar_text_course);
        bottom_bar_image_course=findViewById(R.id.bottom_bar_image_course);
        bottom_bar_text_exercises=findViewById(R.id.bottom_bar_text_exercises);
        bottom_bar_image_exercises=findViewById(R.id.bottom_bar_image_exercises);
        bottom_bar_text_myinfo=findViewById(R.id.bottom_bar_text_myinfo);
        bottom_bar_image_myinfo=findViewById(R.id.bottom_bar_image_myinfo);
        //包含底部 android:id="@+id/main_bottom_bar"
        main_bottom_bar=findViewById(R.id.main_bottom_bar);
        //View Btn android:id="@+id/bottom_bar_course_btn" 每个RelativeLayout
        bottom_bar_course_btn=findViewById(R.id.bottom_bar_course_btn);
        bottom_bar_exercises_btn=findViewById(R.id.bottom_bar_exercises_btn);
        bottom_bar_myinfo_btn=findViewById(R.id.bottom_bar_myinfo_btn);

        bottom_bar_course_btn.setOnClickListener(this);
        bottom_bar_exercises_btn.setOnClickListener(this);
        bottom_bar_myinfo_btn.setOnClickListener(this);
        //技巧
        //tv_back.setVisibility(View.GONE);
        tv_main_title.setText("博学谷课程");
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_bar_course_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new CourseFragment()).commit();
                setSelectStatus(0);
                break;
            case R.id.bottom_bar_exercises_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new ExercisesFragment()).commit();
                setSelectStatus(1);
                break;
            case R.id.bottom_bar_myinfo_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new MyinfoFragment()).commit();
                setSelectStatus(2);
                break;
        }
    }
}
