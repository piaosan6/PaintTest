package com.pjm.painttest.CanvasTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.CanvasTest.customView.CanvasView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CanvasBaseActivity extends BaseActivity {

    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.canvasView)
    CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_base);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3 })
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                canvasView.update(CanvasView.TRANSLATE);
                break;

            case R.id.btn1:
                canvasView.update(CanvasView.SCALE);
                break;

            case R.id.btn2:
                canvasView.update(CanvasView.ROTATE);
                break;

            case R.id.btn3:
                canvasView.update(CanvasView.CLIP);
                break;

        }

    }


}
