package com.pjm.painttest.XferModeTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;
import com.pjm.painttest.XferModeTest.XferModeCase.EraserView;
import com.pjm.painttest.XferModeTest.XferModeCase.GuaGuaKaView;
import com.pjm.painttest.XferModeTest.XferModeCase.GuaGuaKaView2;
import com.pjm.painttest.XferModeTest.XferModeCase.GuaGuaKaView3;
import com.pjm.painttest.XferModeTest.XferModeCase.MagnifierView;
import com.pjm.painttest.XferModeTest.XferModeCase.MagnifierView2;
import com.pjm.painttest.XferModeTest.XferModeCase.ReflectionView;
import com.pjm.painttest.XferModeTest.XferModeCase.ReflectionView2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XferModeCaseTestActivity extends BaseActivity {

    @BindView(R.id.btn_add0)
    Button btnAdd0;
    @BindView(R.id.btn_add1)
    Button btnAdd1;
    @BindView(R.id.btn_add2)
    Button btnAdd2;
    @BindView(R.id.ll)
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnifier_view_test);
        unbinder =  ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_add0, R.id.btn_add1, R.id.btn_add2, R.id.btn_add3, R.id.btn_add4, R.id.btn_add5, R.id.btn_add6, R.id.btn_add7 })
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_add0:
                ll.removeAllViews();
                ll.addView(new MagnifierView(this));
                break;

            case R.id.btn_add1:
                ll.removeAllViews();
                ll.addView(new MagnifierView2(this));
                break;

            case R.id.btn_add2:
                ll.removeAllViews();
                ll.addView(new GuaGuaKaView(this));
                break;
            case R.id.btn_add3:
                ll.removeAllViews();
                ll.addView(new GuaGuaKaView2(this));
                break;
            case R.id.btn_add4:
                ll.removeAllViews();
                ll.addView(new GuaGuaKaView3(this));
                break;
            case R.id.btn_add5:
                ll.removeAllViews();
                ll.addView(new EraserView(this));
                break;
            case R.id.btn_add6:
                ll.removeAllViews();
                ll.addView(new ReflectionView(this));
                break;
            case R.id.btn_add7:
                ll.removeAllViews();
                ll.addView(new ReflectionView2(this));
                break;
        }
    }

}
