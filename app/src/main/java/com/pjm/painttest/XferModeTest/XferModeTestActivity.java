package com.pjm.painttest.XferModeTest;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pjm.painttest.R;
import com.pjm.painttest.Utils.L;
import com.pjm.painttest.XferModeTest.CustomView.XferModeBaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class XferModeTestActivity extends AppCompatActivity {

    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.btn_add0)
    Button btnAdd0;
    @BindView(R.id.btn_add1)
    Button btnAdd1;
    @BindView(R.id.btn_add2)
    Button btnAdd2;
    @BindView(R.id.btn_add3)
    Button btnAdd3;
    @BindView(R.id.btn_add4)
    Button btnAdd4;
    @BindView(R.id.btn_add5)
    Button btnAdd5;
    @BindView(R.id.btn_add6)
    Button btnAdd6;
    @BindView(R.id.btn_add7)
    Button btnAdd7;
    @BindView(R.id.btn_add8)
    Button btnAdd8;
    @BindView(R.id.btn_add9)
    Button btnAdd9;
    @BindView(R.id.btn_add10)
    Button btnAdd10;
    @BindView(R.id.btn_add11)
    Button btnAdd11;
    @BindView(R.id.btn_add12)
    Button btnAdd12;
    @BindView(R.id.btn_add13)
    Button btnAdd13;
    @BindView(R.id.btn_add14)
    Button btnAdd14;
    @BindView(R.id.btn_add15)
    Button btnAdd15;
    @BindView(R.id.ll_xferMode)
    LinearLayout llXferMode;

    private Unbinder unbinder;
    private XferModeBaseView civ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfer_mode_test);
        unbinder =  ButterKnife.bind(this);
        init();
        civ = new XferModeBaseView(this);
        llXferMode.addView(civ);
    }

    private void init() {

    }

    @OnClick({R.id.btn_add0, R.id.btn_add1, R.id.btn_add2, R.id.btn_add3, R.id.btn_add4,R.id.rb1,R.id.rb2,
              R.id.btn_add5, R.id.btn_add6, R.id.btn_add7, R.id.btn_add8, R.id.btn_add9, R.id.btn_add10,
              R.id.btn_add11, R.id.btn_add12, R.id.btn_add13, R.id.btn_add14, R.id.btn_add15,})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.rb1:
                L.i("rb1 click");
                civ.setDstAsBackGround(true);
                civ.invalidate();
                break;

            case R.id.rb2:
                civ.setDstAsBackGround(false);
                civ.invalidate();
                break;

            case R.id.btn_add0:
                civ.setXferMode(PorterDuff.Mode.CLEAR);
                break;

            case R.id.btn_add1:
                civ.setXferMode(PorterDuff.Mode.SRC);
                break;

            case R.id.btn_add2:
                civ.setXferMode(PorterDuff.Mode.DST);
                break;

            case R.id.btn_add3:
                civ.setXferMode(PorterDuff.Mode.SRC_OVER);
                break;

            case R.id.btn_add4:
                civ.setXferMode(PorterDuff.Mode.DST_OVER);

                break;

            case R.id.btn_add5:
                civ.setXferMode(PorterDuff.Mode.SRC_IN);

                break;

            case R.id.btn_add6:
                civ.setXferMode(PorterDuff.Mode.DST_IN);

                break;

            case R.id.btn_add7:
                civ.setXferMode(PorterDuff.Mode.SRC_OUT);

                break;

            case R.id.btn_add8:
                civ.setXferMode(PorterDuff.Mode.DST_OUT);

                break;

            case R.id.btn_add9:
                civ.setXferMode(PorterDuff.Mode.SRC_ATOP);

                break;

            case R.id.btn_add10:
                civ.setXferMode(PorterDuff.Mode.DST_ATOP);

                break;

            case R.id.btn_add11:
                civ.setXferMode(PorterDuff.Mode.XOR);

                break;

            case R.id.btn_add12:
                civ.setXferMode(PorterDuff.Mode.DARKEN);
                break;

            case R.id.btn_add13:
                civ.setXferMode(PorterDuff.Mode.LIGHTEN);
                break;

            case R.id.btn_add14:
                civ.setXferMode(PorterDuff.Mode.MULTIPLY);
                break;

            case R.id.btn_add15:
                civ.setXferMode(PorterDuff.Mode.SCREEN);
                break;

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
