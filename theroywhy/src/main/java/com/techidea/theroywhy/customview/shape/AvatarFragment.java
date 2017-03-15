package com.techidea.theroywhy.customview.shape;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2017/3/15.
 */

public class AvatarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_acatar, container, false);
        return root;
    }
}
