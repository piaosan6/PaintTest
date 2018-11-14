package com.pjm.painttest.pathTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.pathTest.customView.TrashView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PathTestActivity extends BaseActivity {

    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.trashView)
    TrashView trashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_test);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn0:
                trashView.performClick();
                break;

            case R.id.btn1:
                startActivity(new Intent(mContext, PathBaseTestActivity.class));
                break;

            case R.id.btn2:
                startActivity(new Intent(mContext, WaveViewTestActivity.class));
                break;

            case R.id.btn3:
                startActivity(new Intent(mContext, DragBubbleViewTestActivity.class));
                break;
        }

    }


}
