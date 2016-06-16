package com.techidea.androidtrain;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import com.techidea.androidtrain.fragment.FragmentMain;
import com.techidea.androidtrain.fragment.FragmentPerson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements FragmentMain.FragmentMainInterface {

    @Bind(R.id.fragment_container)
    FrameLayout mFrameLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, FragmentMain.getINSTANCE())
                .commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void updateData(String message) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentPerson fragmentPerson = FragmentPerson.getINSTANCE();
        fragmentPerson.setArticle(message);
        fragmentTransaction.replace(R.id.fragment_container, fragmentPerson);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, FragmentPerson.getINSTANCE());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
