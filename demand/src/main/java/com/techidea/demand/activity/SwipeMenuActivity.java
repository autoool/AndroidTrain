package com.techidea.demand.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.techidea.commonlibrary.adapter.CommonAdapter;
import com.techidea.commonlibrary.adapter.CommonViewHolder;
import com.techidea.demand.R;
import com.techidea.demand.itemadapter.Student;
import com.techidea.demand.itemadapter.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.fantasee.swipwmenulistview.SwipeMenu;
import cn.fantasee.swipwmenulistview.SwipeMenuCreator;
import cn.fantasee.swipwmenulistview.SwipeMenuItem;
import cn.fantasee.swipwmenulistview.SwipeMenuListView;

/**
 * Created by zchao on 2016/11/17.
 */

public class SwipeMenuActivity extends Activity {

    @Bind(R.id.listView)
    SwipeMenuListView swipeMenuListView;

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
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                // set item width
                deleteItem.setWidth(90);
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        swipeMenuListView.setMenuCreator(creator);
        swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        swipeMenuListView.setAdapter(studentAdapter);
        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        break;
                }
                return false;
            }
        });
    }

    private void initStudents() {
        students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student(i, " zhang" + String.valueOf(i));
            students.add(student);
        }
    }
}
