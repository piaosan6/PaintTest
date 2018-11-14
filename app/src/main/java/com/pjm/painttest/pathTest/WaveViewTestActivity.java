package com.pjm.painttest.pathTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.pathTest.customView.WaveView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaveViewTestActivity extends BaseActivity {

    @BindView(R.id.btnX)
    Button btnX;
    @BindView(R.id.btnY)
    Button btnY;
    @BindView(R.id.waveView)
    WaveView waveView;
    @BindView(R.id.btnStop)
    Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view_test);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.btnX, R.id.btnY, R.id.btnStop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnX:
                waveView.startAnimForX();
                break;

            case R.id.btnY:
                waveView.startAnimForXY();
                break;

            case R.id.btnStop:
                waveView.stopAnim();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(waveView != null){
            waveView.stopAnim();
        }
    }


}
