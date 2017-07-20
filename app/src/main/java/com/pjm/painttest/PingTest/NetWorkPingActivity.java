package com.pjm.painttest.PingTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;
import com.pjm.painttest.Utils.L;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NetWorkPingActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    ProcessBuilder pb;
    StringBuffer sb;
    Handler mHandler;
    @BindView(R.id.et)
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_ping);
        ButterKnife.bind(this);
        //pb = new ProcessBuilder("adb shell","ping -c 5 www.baidu.com");
        sb = new StringBuffer();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tvInfo.setText(sb.toString());
            }
        };
    }

    @OnClick({R.id.btn})
    public void onClick(View view) {
        pingNetWork();
    }

    private void pingNetWork() {
//        new Thread(){
//            @Override
//            public void run() {
//                try{
//                    Process process = Runtime.getRuntime().exec("/system/bin/ping");
//                    OutputStream stdout = process.getOutputStream();
//                    InputStream stderr = process.getErrorStream();
//                    InputStream stdin = process.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));
//                    BufferedReader err= new BufferedReader(new InputStreamReader(stderr));
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdout));
//                    writer.write("-c 5 -i 0.6 58.217.200.113");
////                    writer.write("ping -c 5 www.baidu.com");
//                    String str ;
//                    while((str = reader.readLine()) != null){
//                        sb.append(str);
//                    }
//                    L.i("sb = " + sb.toString());
//                    while((str = err.readLine()) != null){
//                        sb.append(str);
//                    }
//                    L.i("sb = " + sb.toString());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    //adb shell
                    Process process = Runtime.getRuntime().exec(et.getText().toString());
                    InputStream is = process.getInputStream();
                    int len = 0;
                    sb.delete(0, sb.length());
                    byte[] buff = new byte[1024];

                    while (-1 != (len = is.read(buff))) {
                        String str = new String(buff, 0, len);
                        L.i("str =" + str + ",len = " + len);
                        sb.append(str);
                    }
                    L.i("len = " + len);

                    is.close();
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

}
