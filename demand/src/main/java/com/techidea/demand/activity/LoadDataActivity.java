package com.techidea.demand.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techidea.demand.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/9/8.
 */
public class LoadDataActivity extends BaseActivity {

    @Bind(R.id.progressbar_loaddata)
    ProgressBar progressBar;
    @Bind(R.id.textview_data)
    TextView textViewData;
    @Bind(R.id.textview_nodata)
    TextView textViewNoData;
    @Bind(R.id.layout_content)
    RelativeLayout layoutContent;

    private LoadDataAsyncTask loadDataAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void init() {
        loadDataAsyncTask = new LoadDataAsyncTask();
        loadDataAsyncTask.execute();
    }

    private void loading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void noData() {
        progressBar.setVisibility(View.INVISIBLE);
        layoutContent.setVisibility(View.GONE);
        textViewNoData.setVisibility(View.VISIBLE);
    }

    private void loadSuccess() {
        progressBar.setVisibility(View.GONE);
        textViewNoData.setVisibility(View.GONE);
        layoutContent.setVisibility(View.VISIBLE);
        textViewData.setText("load data success");
    }

    private class LoadDataAsyncTask extends AsyncTask<Void, Void, Boolean> {

        public LoadDataAsyncTask() {
        }

        @Override
        protected void onPreExecute() {
            loading();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                loadSuccess();
            } else {
                noData();
            }
            loadDataAsyncTask = null;
        }
    }
}
