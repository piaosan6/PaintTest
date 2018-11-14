package com.pjm.painttest.baseTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.baseTest.customView.MyView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaintBaseTestActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btn10)
    Button btn10;

    @BindView(R.id.myView)
    MyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_base_test);
        unbinder = ButterKnife.bind(this);


    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                view.update(0);
                break;

            case R.id.btn2:
                view.update(1);
                break;

            case R.id.btn3:
                view.update(2);
                break;

            case R.id.btn4:
                view.update(3);
                break;

            case R.id.btn5:
                view.update(4);
                break;

            case R.id.btn6:
                view.update(5);
                break;

            case R.id.btn7:
                view.update(6);
                break;

            case R.id.btn8:
                view.update(7);
                break;

            case R.id.btn9:
                view.update(8);
                break;

            case R.id.btn10:
                view.update(9);
                break;
//            case R.id.btn11:
//                startActivity(new Intent(MainActivity.this, DashViewTestActivity.class));
//                break;
//            case R.id.btn12:
//                startActivity(new Intent(MainActivity.this, PathDashPathViewActivity.class));
//                break;

        }
    }

}
