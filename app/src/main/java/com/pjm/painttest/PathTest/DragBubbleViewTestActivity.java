package com.pjm.painttest.PathTest;

import android.os.Bundle;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.PathTest.customView.DragBubbleView2;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragBubbleViewTestActivity extends BaseActivity {

//    @BindView(R.id.reset_btn)
//    Button resetBtn;
    @BindView(R.id.dragBubbleView2)
    DragBubbleView2 dragBubbleView2;
//    @BindView(R.id.dragBubbleView)
//    DragBubbleView dragBubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_bubble_view_test);
        unbinder = ButterKnife.bind(this);
    }

//    @OnClick({R.id.reset_btn})
//    public void OnClick(View v){
//        dragBubbleView.reset();
//    }

}
