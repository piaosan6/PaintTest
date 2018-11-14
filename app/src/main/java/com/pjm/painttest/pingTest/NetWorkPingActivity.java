package com.pjm.painttest.pingTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ShellUtils;
import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    private String ip = "223.5.5.5";// default ping ip

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
        ping();
        //pingNetWork();
    }



    /*
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return
     */
    public static final boolean ping() {
        String result = null;
        try {
            ///String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            String ip = "14.215.177.38";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 5 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuilder stringBuffer = new StringBuilder();
            String content;
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            L.e("------ping---- 》result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
            L.e("status: " + status);
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            L.e("----result--- > result = " + result);
        }
        return false;
    }


    private void pingNetWork() { // 需要su权限
        ip = "14.215.177.39";
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format("ping -c 1 %s", ip), false);
        boolean ret = result.result == 0;
        if (result.errorMsg != null) {
            Log.d("NetworkUtils", "errorMsg = " + result.errorMsg);
        }
        if (result.successMsg != null) {
            Log.d("NetworkUtils", "successMsg = " + result.successMsg);
        }
        L.e("ret = " + ret);

    }

}
