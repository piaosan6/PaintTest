package com.pjm.painttest.customView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pjm.painttest.R;


public class MyDialog extends Dialog implements View.OnClickListener {

	private TextView title;
	private View v1;
	private Button btnConfirm;
	private Button btnCancel;
	private TextView message;
	public EditText etInput;
	private DialogConfirmListener confirmListener;
	private DialogOnCancelListener cancelListener;
	private DialogEditListener editListener;
	/** 点击空白地方和back键是否可以消失*/
	private boolean mCanDismiss = true;
    private View contentView;

    /**
	 * (点击编辑确定按钮，触发事件)
	 *
	 */
	public interface DialogEditListener {
		void onEditClick(View v, String text);
	}

	/**
	 * (点击确定按钮，触发事件)
	 *
	 */
	public interface DialogConfirmListener {
		void OnConfirmClick(View v);
	}
	/**
	 * (点击取消按钮，触发事件)
	 *
	 */
	public interface DialogOnCancelListener {
		void OnCancelClick(View v);
	}

	public MyDialog(Context context) {
		super(context, R.style.myDialog);
	}
    /**
     * 点击空白区域和back键是否可以消失 cancelable false 不可消失
     */
    public MyDialog(Context context, boolean cancelable) {
        super(context, R.style.myDialog);
        setCancelable(cancelable);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		init();
	}

	public void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        contentView = inflater.inflate(R.layout.my_dialog, null);
        setContentView(contentView);

        title = (TextView) findViewById(R.id.title);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnConfirm = (Button) findViewById(R.id.btn_sure);
        message = (TextView) findViewById(R.id.dialog_message);
        etInput = (EditText) findViewById(R.id.editInput);
        v1 = findViewById(R.id.v1);
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        Window dialogWindow = getWindow();
        if(dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
        }
    }

    public void setView(View view){
        setContentView(view);
        contentView = view;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			if (cancelListener != null) {
				cancelListener.OnCancelClick(v);
			}else{
				dismiss();
			}
			break;
		case R.id.btn_sure:
			if (confirmListener != null) {
                confirmListener.OnConfirmClick(v);
			}
			if(editListener != null){
				String text = etInput.getText().toString();
				editListener.onEditClick(v,text);
			}
			dismiss();
			break;
		}
	}

	public void setButtonName(String left, String right) {
		if (btnCancel != null && btnConfirm != null) {
		    btnCancel.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
			btnCancel.setText(left);
			btnConfirm.setText(right);
		}
	}

	/**
	 * 设置字体的位置：eg:Gravity.LEFT/RIGHT
	 * 默认是Gravity.CENTER/
	 * @param position
     */
	public void changesTextPosition(int position) {
		message.setGravity(position);
	}
	

	public void showTwoBtn() {
		if (btnCancel == null) {
			btnCancel = (Button) findViewById(R.id.btn_cancel);
		}
		btnCancel.setVisibility(View.VISIBLE);
	}

	public void setMessage(String msg) {
		if (message == null) {
			message = (TextView) findViewById(R.id.dialog_message);
		}
		message.setText(msg);
	}

	public void hintTitle(String titleStr) {
		if (title != null) {
			title.setVisibility(View.GONE);
			v1.setVisibility(View.GONE);
		}
	}

	public void setTitle(String titleStr) {
		if (title == null) {
			title = (TextView) findViewById(R.id.title);
		}
		title.setText(titleStr);
	}

	public void setOnSureListener(DialogConfirmListener listener) {
		this.confirmListener = listener;
        btnConfirm.setOnClickListener(this);
		etInput.setVisibility(View.GONE);
		message.setVisibility(View.VISIBLE);
	}
	public void setOnCancelClick(DialogOnCancelListener listener) {
		this.cancelListener = listener;
        btnCancel.setOnClickListener(this);
		etInput.setVisibility(View.GONE);
		message.setVisibility(View.VISIBLE);
	}

	public void setOnEditListener(DialogEditListener editListener) {
		this.editListener = editListener;
		etInput.setVisibility(View.VISIBLE);
		message.setVisibility(View.GONE);
		btnCancel.setVisibility(View.VISIBLE);
	}

	public void setInputType(int inputType, int maxLen){
		if (etInput == null) {
			etInput = (EditText) findViewById(R.id.editInput);
		}
		etInput.setInputType(inputType);
		etInput.setMaxLines(maxLen);

	}

	public void setInputType(int inputType, int maxline,int maxLen){
		if (etInput == null) {
			etInput = (EditText) findViewById(R.id.editInput);
		}
		etInput.setInputType(inputType);
		etInput.setMaxLines(maxline);

		InputFilter[] filters = {new InputFilter.LengthFilter(maxLen)};
		etInput.setFilters(filters);


	}

	public void setRightBtnStr(String str) {
		btnConfirm.setText(str);
	}

	public void disMissRightBtn() {
		if (btnConfirm != null) {
			btnConfirm.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置输入类型
	 * @param inputType
	 */
	public void setEditInputType(int inputType) {
	    if(etInput != null)
		    etInput.setInputType(inputType);
	}

	public void setEditHint(String string) {
		etInput.setHint(string);
	}

	public void setHiddenMessage() {
		if (message == null) {
			message = (TextView) findViewById(R.id.dialog_message);
		}
		message.setVisibility(View.GONE);
	}

	public void setMessageTextSize(int textSize) {
		if (message == null) {
			message = (TextView) findViewById(R.id.dialog_message);
		}
		message.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
	}

}
