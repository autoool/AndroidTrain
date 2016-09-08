package com.techidea.controls.wiget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techidea.controls.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2016/9/6.
 */
public class MessageDialog extends DialogFragment {

    @Bind(R.id.textview_dialog_title)
    TextView textViewTitle;
    @Bind(R.id.textview_dialog_message)
    TextView textViewMessage;
    @Bind(R.id.textview_dialog_cancel)
    TextView textViewCancle;
    @Bind(R.id.textview_dialog_confirm)
    TextView textViewConfirm;

    private String title;
    private String message;
    private View.OnClickListener cancelListener;
    private View.OnClickListener confirmListener;

    public static MessageDialog newInstance() {
        MessageDialog messageDialog = new MessageDialog();
        return messageDialog;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setConfirmListener(View.OnClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
//        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_message, container, false);
        ButterKnife.bind(this, root);
        if (TextUtils.isEmpty(title)) {
            textViewTitle.setVisibility(View.GONE);
        } else {
            textViewTitle.setText(title);
        }
        textViewMessage.setText(this.message);
        if (cancelListener != null)
            textViewCancle.setOnClickListener(this.cancelListener);
        if (confirmListener != null)
            textViewConfirm.setOnClickListener(this.confirmListener);
        return root;
    }

    @OnClick(R.id.textview_dialog_cancel)
    void cancel() {
        this.dismiss();
    }

}
