package com.pjm.painttest.ShaderTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;
import com.pjm.painttest.ShaderTest.customView.BitmapShaderView;
import com.pjm.painttest.ShaderTest.customView.LinearGradientShaderView;
import com.pjm.painttest.ShaderTest.customView.RadarView;
import com.pjm.painttest.ShaderTest.customView.RadialGradientView;
import com.pjm.painttest.ShaderTest.customView.ShapeDrawView;
import com.pjm.painttest.ShaderTest.customView.SweepGradientView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShaderViewTestActivity extends BaseActivity {

    @BindView(R.id.ll_shader)
    LinearLayout llShader;

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
    private RadarView rv;
    private boolean hasStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shader_view_test);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_add0, R.id.btn_add1, R.id.btn_add2, R.id.btn_add3, R.id.btn_add4, R.id.btn_add5})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add0:
                llShader.removeAllViews();
                llShader.addView(new BitmapShaderView(this));
                break;
            case R.id.btn_add1:
                llShader.removeAllViews();
                llShader.addView(new ShapeDrawView(this));
                break;
            case R.id.btn_add2:
                llShader.removeAllViews();
                llShader.addView(new LinearGradientShaderView(this));

                break;
            case R.id.btn_add3:
                llShader.removeAllViews();
                llShader.addView(new RadialGradientView(this));
                break;
            case R.id.btn_add4:
                llShader.removeAllViews();
                llShader.addView(new SweepGradientView(this));
                break;
            case R.id.btn_add5:
                if(rv == null){
                    llShader.removeAllViews();
                    rv =  new RadarView(this);
                    llShader.addView(rv);
                    rv.start();
                    hasStart = true;
                }else{
                    rv.stop();
                    llShader.removeAllViews();
                    rv = null;
                }
                break;
            default:

                break;

        }
    }

}
