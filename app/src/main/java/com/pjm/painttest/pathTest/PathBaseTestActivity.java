package com.pjm.painttest.pathTest;

import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.pathTest.customView.PathView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PathBaseTestActivity extends BaseActivity {


    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.pathView)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_base_test);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3 })
    public void onClick(View view){

        switch (view.getId()){
            case R.id.btn0:
                pathView.updateType(Path.FillType.WINDING);
                break;

            case R.id.btn1:
                pathView.updateType(Path.FillType.EVEN_ODD);
                break;

            case R.id.btn2:
                pathView.updateType(Path.FillType.INVERSE_WINDING);
                break;

            case R.id.btn3:
                pathView.updateType(Path.FillType.INVERSE_EVEN_ODD);
                break;
        }


    }
}
