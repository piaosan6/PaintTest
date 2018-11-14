package com.pjm.painttest.netTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pjm.painttest.R;
import com.pjm.painttest.utils.NetWorkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetTestActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tvInfo)
    TextView tvInfo;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_test);
        ButterKnife.bind(this);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                tvInfo.setText(String.valueOf(msg.what));
//                tvInfo.setText((String)msg.obj);
                return false;
            }
        });
    }

    @OnClick({R.id.btn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                int s =  NetWorkUtils.getNetWorkState(this);
                mHandler.sendEmptyMessage(s);

//                L.e("NetworkType = " + s.name());
//                Message msg = Message.obtain();
//                msg.what = 0;
//                msg.obj = s.name();
//                mHandler.sendMessage(msg);
                break;
        }
    }
}
