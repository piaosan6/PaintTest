package com.pjm.painttest.recyclerViewTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pjm.painttest.R;
import com.pjm.painttest.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewTestActivity extends AppCompatActivity {

    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvSelectAll)
    TextView tvSelectAll;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Random random = new Random();
    private String lastKey;
    private TestAdapter testAdapter;
    private List<TestEntity> allList = new ArrayList<>();
    private int titleIndex;
    private int loadMoreStartIndex;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<DataBean> data = new ArrayList<>();
//        data.addAll(genData(++titleIndex, 1, 3));
//        data.addAll(genData(++titleIndex, 1, 2));
//        data.addAll(genData(titleIndex, 3, 2));
//        data.addAll(genData(++titleIndex, 1, 3));
        allList = getEntityList(data, false);
        setAdapter();
    }

    private void loadMore(){
        int count = random.nextInt(3) + 1;
        Log.i("info", "count = " + count);
        if(count % 2 == 0){
            titleIndex++;
            loadMoreStartIndex = 1;
        }else {
            loadMoreStartIndex = loadMoreStartIndex + count;
        }
        allList = getEntityList(genData(titleIndex, loadMoreStartIndex, count), true);
        setAdapter();
    }

    private void setAdapter() {
        if(testAdapter == null){
            testAdapter = new TestAdapter(allList);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            testAdapter.bindToRecyclerView(recyclerView);
            testAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TestEntity bean = testAdapter.getData().get(position);
                    if(testAdapter.isSelectMode()){
                        testAdapter.updateSelect(position, bean);
                    }else {
                        ToastUtils.showShort("click " + position);
                    }
                }
            });
            testAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                    if(!testAdapter.isSelectMode()){
                        testAdapter.updateSelectMode(true);
                    }
                    return true;
                }
            });
        }else {
            testAdapter.setNewData(allList);
        }
    }

    @OnClick({R.id.tvCancel, R.id.tvRefresh, R.id.tvLoadMore, R.id.tvSelectAll, R.id.tvDelete})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                if(testAdapter != null && testAdapter.isSelectMode()){
                    testAdapter.updateSelectMode(false);
                }
                break;

            case R.id.tvRefresh:
                allList.clear();
                titleIndex = 0;
                id = 0;
                loadMoreStartIndex = 0;
                initView();
                break;

            case R.id.tvLoadMore:
                loadMore();
                break;

            case R.id.tvSelectAll:
                if(testAdapter != null && testAdapter.isSelectMode()){
                    testAdapter.updateSelectAll(true);
                }
                break;

            case R.id.tvDelete:
                if(testAdapter != null && testAdapter.isSelectMode()){
                    testAdapter.deleteSelect();
                }
                break;
        }
    }


    private List<TestEntity> getEntityList(List<DataBean> dataList, boolean isLoadMore) {
        int subIndex = 0;
        int parentIndex = 0;
        TestEntity parentEntity = null;
        List<TestEntity> list = new ArrayList<>();
        List<TestEntity> subList = new ArrayList<>();
        if(isLoadMore){
            if(testAdapter != null && testAdapter.getData() != null && testAdapter.getData().size() > 0){
                list = testAdapter.getData();
                TestEntity lastEntity = list.get(list.size() - 1);
                subIndex = lastEntity.getSubIndex();
                parentIndex = lastEntity.getParentIndex();
                parentEntity = list.get(parentIndex);
                lastKey = parentEntity.header;
                subList = parentEntity.getSubList();
                //showLog("lastKey = " + lastKey + ", parentIndex = " + parentIndex + ", subList = " + GsonUtils.toJsonString(subList));
            }
        }
        int size = dataList.size();
        for(int i = 0; i < size; i++) {
            DataBean dataBean = dataList.get(i);
            String tempKey = dataBean.getTitle();
            if (tempKey == null) {
                continue;
            }
            //showLog("lastKey = " + lastKey + ", tempKey = " + lastKey + ", parentIndex = " + parentIndex);
            if (!tempKey.equals(lastKey)) {
                parentIndex = list.size();
                if (parentIndex != 0) {
                    parentEntity.setSubList(subList);
                    parentEntity.setParentIndex(parentIndex);
                    //Log.i("info",  "parentIndex = " + parentIndex + "\n parentEntity = " + GsonUtils.toJsonString(parentEntity));
                    subList = new ArrayList<>();
                    subIndex = 0;
                }
                lastKey = tempKey;
                parentEntity = new TestEntity(true, tempKey, parentIndex);
                list.add(parentEntity);
            }
            TestEntity contentEntity = new TestEntity(dataBean, ++subIndex, parentIndex);
            subList.add(contentEntity);
            list.add(contentEntity);
        }
        if(parentEntity != null){
            parentEntity.setSubList(subList);
            //Log.i("info", "parentEntity = " + GsonUtils.toJsonString(parentEntity) + "\n list = " + GsonUtils.toJsonString(subList));
        }

        return list;
    }

    private void showLog(String msg) {
        Log.e("info", msg);
    }

    private List<DataBean> getData(int pager) {
       return genData(1, 1, random.nextInt(20));
    }

    private List<DataBean> genData(int titleIndex, int startIndex, int size) {
        ArrayList<DataBean> list = new ArrayList<>();
        for (int i = startIndex; i <= startIndex + size; i++) {
            list.add(new DataBean(++id,"标题" + titleIndex, "内容" + i));
        }
        return  list;
    }


}