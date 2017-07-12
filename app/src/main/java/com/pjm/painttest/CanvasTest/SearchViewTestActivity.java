package com.pjm.painttest.CanvasTest;

import android.os.Bundle;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;

import butterknife.ButterKnife;

public class SearchViewTestActivity extends BaseActivity {


//    @BindView(R.id.searchView)
//    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_test);
        unbinder = ButterKnife.bind(this);
    }



}
