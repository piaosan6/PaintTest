package com.pjm.painttest.baseTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.pjm.painttest.customView.BlueScanView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomViewTestActivity extends AppCompatActivity {
    @BindView(R.id.blueScanView1)
    BlueScanView blueScanView1;
    @BindView(R.id.blueScanView2)
    BlueScanView blueScanView2;
    @BindView(R.id.blueScanView3)
    BlueScanView blueScanView3;

//    @BindView(R.id.waterView1)
//    GasMeterView waterView1;
//    @BindView(R.id.waterView2)
//    GasMeterView waterView2;

//    @BindView(R.id.gasView1)
//    GasMeterView gasView1;
//    @BindView(R.id.gasView2)
//    GasMeterView gasView2;

//    @BindView(R.id.eleMeterView1)
//    EleMeterView eleMeterView1;
//    @BindView(R.id.eleMeterView2)
//    EleMeterView eleMeterView2;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_test);
        ButterKnife.bind(this);

        blueScanView1.startScanAnim();
        blueScanView2.startScanAnim();
        blueScanView3.startScanAnim("设备扫描中...");

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        blueScanView1.setState("设备连接中...");
                        blueScanView2.setState("设备连接中...");
                        blueScanView3.setState("设备连接中...");
                        break;
                    case 1:
                        blueScanView1.setState("设备已连接...");
                        blueScanView2.setState("设备已连接...");
                        blueScanView3.setState("设备已连接...");
                        break;
                    case 2:
                        blueScanView1.setState("读取数据中...");
                        blueScanView2.setState("读取数据中...");
                        blueScanView3.setState("读取数据中...");
                        break;
                    case 3:
                        blueScanView1.stoScanAnim("扫描失败...");
                        blueScanView2.stoScanAnim("扫描失败...");
                        blueScanView3.stoScanAnim("扫描失败...");
                        break;
                }
                return false;
            }
        });
        mHandler.sendEmptyMessageDelayed(0, 3000);
        mHandler.sendEmptyMessageDelayed(1, 6000);
        mHandler.sendEmptyMessageDelayed(2, 9000);
//        mHandler.sendEmptyMessageDelayed(3, 12000);

//        tv1.setText("123123123");
//        tv1.setTextColor(0xff517efd);
//        tv2.setText(R.string.about_us_str);

//        eleMeterView1.setMeterECode("1234.56");
//        eleMeterView1.setRemain("19000.6");
//        eleMeterView2.setMeterECode("456789.23");
//        eleMeterView2.setRemain("1000.8");
//        eleMeterView2.setState(false);

//        gasView1.setMeterECode("01234567");
//        gasView1.setRemain("19000.6");
//        gasView2.setMeterECode("56789456");
//        gasView2.setRemain("1000.8");

//        waterView1.setMeterECode("01234");
//        waterView1.setRemain("19000.6");
//        waterView2.setMeterECode("56789");
//        waterView2.setRemain("1000.8");

    }

}
