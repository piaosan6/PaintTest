package com.pjm.painttest.dialogTest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.pjm.painttest.R;
import com.pjm.painttest.customView.MyDialog;

public class DialogTestActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);
        mContext =this;
    }

    public void click(View v) {
        switch (v.getId()){
            case R.id.btn1:
                showSystemDialog();
                break;

            case R.id.btn2:
                showSystemCustomViewDialog();
                break;

            case R.id.btn3:
                MyDialog dialog = new MyDialog(DialogTestActivity.this);
                dialog.show();
                dialog.changesTextPosition(Gravity.START);
                dialog.setTitle("自定义dialog");
                dialog.setMessage("我是一个自定义的dialog");
                dialog.showTwoBtn();
                dialog.setButtonName("否", "是");
                dialog.setOnSureListener(new MyDialog.DialogConfirmListener() {
                    @Override
                    public void OnConfirmClick(View v) {
                        dialog.dismiss();
                        ToastUtils.showShort("确定了啥也不做");
                    }
                });
                break;
        }

    }

    private void showSystemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        builder.setTitle("系统dialog");
        builder.setMessage("我是一个系统的dialog");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.showShort("确定了啥也不做");
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.showShort("你居然点否定");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 自定义布局
     * setView()只会覆盖AlertDialog的Title与Button之间的那部分，而setContentView()则会覆盖全部，
     * setContentView()必须放在show()的后面
     */
    private void showSystemCustomViewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.myDialog);//
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.my_dialog2, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView content = (TextView) v.findViewById(R.id.dialog_message);
        Button btn_sure = (Button) v.findViewById(R.id.btn_sure);
        Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        title.setText("标题");
        content.setText("内容");
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ToastUtils.showShort("ok");
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                ToastUtils.showShort("no");
            }
        });
    }


}
