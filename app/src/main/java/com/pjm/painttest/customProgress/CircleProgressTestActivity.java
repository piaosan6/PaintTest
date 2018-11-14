package com.pjm.painttest.customProgress;

import android.os.Bundle;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.customProgress.customView.CircleProgress;
import com.pjm.painttest.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleProgressTestActivity extends BaseActivity {

    @BindView(R.id.cp)
    CircleProgress cp;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_test);
        unbinder = ButterKnife.bind(this);
        random = new Random();

    }

    @OnClick(R.id.btn)
    public void startAnim() {
        int percent =  random.nextInt(100);
        cp.setPercentWithAnim(percent);
    }

}
