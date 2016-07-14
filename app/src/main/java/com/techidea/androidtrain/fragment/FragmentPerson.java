package com.techidea.androidtrain.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techidea.androidtrain.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/6/16.
 */
public class FragmentPerson extends Fragment {

    TextView mTextViewArticle;

    private static FragmentPerson INSTANCE;

    public static FragmentPerson getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new FragmentPerson();
        return INSTANCE;
    }

    public FragmentPerson() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        mTextViewArticle = (TextView) view.findViewById(R.id.textview_article);
        return view;
    }

    public void setArticle(String message) {
        mTextViewArticle.setText(message);
    }
}
