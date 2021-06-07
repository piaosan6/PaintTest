package com.pjm.painttest.timeTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TimeTestActivity extends AppCompatActivity {


    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvTimeHandler)
    TextView tvTimeHandler;
    @BindView(R.id.tvTimeSystem)
    TextView tvTimeSystem;



    /**
     * 精确修正时间
     */
    private Handler mCalHandler = new Handler(Looper.getMainLooper());

    private final Runnable mTicker = new Runnable() {
        public void run() {
            if(!isStop){
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                mCalHandler.postAtTime(mTicker, next);
                long accurateTime = (now - lastSysTime) / 1000;
                tvTime.setText("精确计时：" + secToTime(accurateTime));
            }else {
                mCalHandler.removeCallbacks(mTicker);
            }

            //Log.e("buder", now + ", time = " + time + ", timeSys = " + timeSys);
        }
    };

    private Unbinder unbinder;
    private Handler mHandler;
    private long lastTime;
    private long time;
    private long lastSysTime;
    private boolean isStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_test);
        unbinder = ButterKnife.bind(this);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                time++;
                long timeSys = (System.currentTimeMillis() - lastTime) / 1000L;
                tvTimeSystem.setText("系统计时:" + secToTime(timeSys));
                tvTimeHandler.setText("计时:" + secToTime(time));
                mHandler.sendEmptyMessageDelayed(0, 1000);
                return false;
            }
        });
    }

    @OnClick({R.id.btnStart, R.id.btnStop, R.id.btnClean})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                time = 0;
                isStop = false;
                lastSysTime = SystemClock.uptimeMillis();
                lastTime = System.currentTimeMillis();
                mCalHandler.post(mTicker);
                mHandler.sendEmptyMessageDelayed(0, 1000);
                break;

            case R.id.btnStop:
                isStop = true;
                mHandler.removeMessages(0);
                mCalHandler.removeCallbacks(mTicker);
                time = 0;
                break;
        }
    }



    public String secToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + String.valueOf(i);
        else
            retStr = "" + i;
        return retStr;
    }
}