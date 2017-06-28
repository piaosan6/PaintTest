package com.pjm.painttest.CustomProgress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pjm.painttest.CustomProgress.customView.CircleProgress;
import com.pjm.painttest.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CircleProgressTestActivity extends AppCompatActivity {

    @BindView(R.id.cp)
    CircleProgress cp;
    private Unbinder unbinder;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
