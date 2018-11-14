package com.pjm.painttest.canvasTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CanvasTestActivity extends BaseActivity {


    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_test);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2 })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btn0:
                startActivity(new Intent(mContext, CanvasBaseActivity.class));
                break;

            case R.id.btn1:
                startActivity(new Intent(mContext, MyDrawableTestActivity.class));
                break;

            case R.id.btn2:
                startActivity(new Intent(mContext, SearchViewTestActivity.class));
                break;
        }

    }




}
