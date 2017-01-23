package com.techidea.demand.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;


import com.techidea.demand.R;
import com.techidea.demand.entity.Student;
import com.techidea.demand.adapter.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/11/17.
 */

public class SwipeMenuActivity extends Activity {


    StudentAdapter studentAdapter;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipemenu);
        ButterKnife.bind(this);
        initStudents();
        initSwipeMenu();
    }

    private void initSwipeMenu() {
        studentAdapter = new StudentAdapter(this, students, R.layout.itemview_student);
    }

    private void initStudents() {
        students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student(i, " zhang" + String.valueOf(i));
            students.add(student);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
