package com.pjm.painttest.LoadingView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.pjm.painttest.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashViewTestActivity extends Activity {

    private Context mContext;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view_test);
        unbinder =  ButterKnife.bind(this);
//        mContext = this;
//        View parent = (View) llRoot.getParent();
//        if(parent instanceof FrameLayout){
//            FrameLayout fm = (FrameLayout) parent;
//            ImageView iv =  new ImageView(mContext);
//            ViewGroup.LayoutParams  params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//            iv.setLayoutParams(params);
//            iv.setImageResource(R.mipmap.zm);
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            fm.addView(iv, 0 ,params);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
