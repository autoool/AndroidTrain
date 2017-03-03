package com.techidea.androidtrain.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techidea.androidtrain.DisplayMessageActivity;
import com.techidea.androidtrain.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2016/6/16.
 */
public class FragmentMain extends Fragment {


    @Bind(R.id.buttonactionbar)
    Button mButtonActionBar;

    public interface FragmentMainInterface {
        void updateData(String message);

        void changeFragment();
    }


    private FragmentMainInterface mFragmentMainInterface;
    private boolean actionBarShow = true;
    private Context mContext;

    private static FragmentMain INSTANCE;

    public static FragmentMain getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new FragmentMain();
        return INSTANCE;
    }

    public FragmentMain() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mFragmentMainInterface = (FragmentMainInterface) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.buttondisplay)
    void buttonDisplay() {
        startActivity(new Intent(getActivity(), DisplayMessageActivity.class));
    }

    @OnClick(R.id.buttonactionbar)
    void buttonactionBar() {
        if (actionBarShow) {

            mButtonActionBar.setText(R.string.showactionbar);
            actionBarShow = false;
        } else {

            mButtonActionBar.setText(R.string.hideactionbar);
            actionBarShow = true;
        }
        if (mFragmentMainInterface!=null)
            mFragmentMainInterface.updateData("123456sdgf");
    }

    @OnClick(R.id.button_title)
    void buttonTitle() {
        if (mFragmentMainInterface != null) {
            mFragmentMainInterface.changeFragment();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}
