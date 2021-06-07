package com.pjm.painttest.textViewTest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改TextView 部分文字的颜色和大小
 */
public class TextSizeColorActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.tvWord)
    TextView tvWord;
    @BindView(R.id.tv_text)
    TextView textView;

    private String keyWord = "我们";
    private String resStr = "我们有以下代码和LinearGradient,我们它与其他所有示例看起来非常相似， 我们太你前期，我们的家园," +
            "它与其他所有示例看起来非常相似";
    String str = "最后一定要记得在设置完了之后设置这个 不是点击事件是不起作用了,最后一定要记得在设置完了之后设置这个 不是点击事件是不起作用了";
    String clicl = "点击事件";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_size_color);
        unbinder = ButterKnife.bind(this);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void viewClick(View v){
        switch (v.getId()){
            case R.id.btn1:
                setBySystem();
                break;

            case R.id.btn2:
                setByCustom();
                break;

            case R.id.btn3:
                //testTextClick();
                textView.setText(getContentBuilder(str, "点击事件"));
                //一定要记得设置这个方法  不是不起作用
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                break;
        }
    }

    private SpannableStringBuilder getContentBuilder(String content, String... text) {
        SpannableStringBuilder contentBuilder = new SpannableStringBuilder(content);
        for(String str : text){
            int contentStart = content.indexOf(str);
            if(contentStart != -1){
                PrivacySpan privacySpan = new PrivacySpan();
                contentBuilder.setSpan(privacySpan, contentStart, contentStart + str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return contentBuilder;
    }

    private void testTextClick() {

        textView.setTextColor(0xffff0000);


        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(str);
        TextViewSpan1 span1 = new TextViewSpan1();
        stringBuilder.setSpan(span1,3,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextViewSpan2 span2 = new TextViewSpan2();
        stringBuilder.setSpan(span2,15,18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(stringBuilder);
        //一定要记得设置这个方法  不是不起作用
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private void setBySystem() {
        String text = resStr.replace(keyWord, "<font color='#00ff00'><big>" + keyWord + "</big></font>");
        Log.i("info", text);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvWord.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvWord.setText(Html.fromHtml(text));
        }
    }

    private void setByCustom() {
        int textSize = 75 ; //单位是px
        String textColor = "#ff0000";
        String htmlFormatStr = "<myFont size='%d' color='%s'>%s</myFont>";
        String htmlStr = String.format(Locale.getDefault(), htmlFormatStr, textSize, textColor, keyWord);
        String text = resStr.replace(keyWord, htmlStr);
        Log.i("info", text);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvWord.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY,null,new HtmlTagHandler("myFont")));
        } else {
            tvWord.setText(Html.fromHtml(text, null, new HtmlTagHandler("myFont")));
        }
    }

    private class PrivacySpan extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.linkColor = Color.TRANSPARENT;
            ds.setColor(0xff00ff00);
            ds.setTextSize(35);
            //设置是否有下划线
            ds.setUnderlineText(false);
        }
        @Override
        public void onClick(View widget) {
            //点击事件
            widget.setBackgroundColor(Color.TRANSPARENT);
            ToastUtils.showShort("这是测试点击呀！");
        }
    }
    private class TextViewSpan1 extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.linkColor = Color.TRANSPARENT;
            ds.setColor(0xff00ff00);
            ds.setTextSize(35);
            //设置是否有下划线
            ds.setUnderlineText(false);
        }
        @Override
        public void onClick(View widget) {
            //点击事件
            widget.setBackgroundColor(Color.TRANSPARENT);
            ToastUtils.showShort("这是测试点击1");
        }
    }

    private class TextViewSpan2 extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setTextSize(75);
            //设置是否有下划线
            ds.setUnderlineText(false);
        }
        @Override
        public void onClick(View widget) {
            //点击事件
            ToastUtils.showShort("这是测试点击2");
        }
    }

}