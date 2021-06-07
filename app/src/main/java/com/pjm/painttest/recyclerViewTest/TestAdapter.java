package com.pjm.painttest.recyclerViewTest;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pjm.painttest.R;
import com.pjm.painttest.utils.GsonUtils;
import com.pjm.painttest.utils.L;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 删除很麻烦
 */
public class TestAdapter extends BaseSectionQuickAdapter<TestEntity, BaseViewHolder> {

    private boolean isSelectMode;
    private ArrayList<Integer> selectList;

    public TestAdapter(List<TestEntity> data) {
        super(R.layout.item_test_content, R.layout.item_test_title, data);
        selectList = new ArrayList<>();
    }

    @Override
    protected void convertHead(BaseViewHolder helper, TestEntity item) {
        helper.setText(R.id.tvTitle, item.header);
        CheckBox cbTitle = helper.getView(R.id.cbTitle);
        cbTitle.setChecked(item.isSelectAll());
        cbTitle.setVisibility(isSelectMode ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestEntity item) {
        DataBean bean = item.t;
        helper.setText(R.id.tvContent, bean.getName());
        helper.setText(R.id.tvSubIndex, String.valueOf(item.getSubIndex()));
        CheckBox cbContent = helper.getView(R.id.cbSelect);
        cbContent.setChecked(item.isSubSelected());
        cbContent.setVisibility(isSelectMode ? View.VISIBLE : View.GONE);
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }

    public void updateSelectMode(boolean selectMode) {
        isSelectMode = selectMode;
        if(!isSelectMode){
            updateSelectAll(false);
        }
        notifyDataSetChanged();
    }

    public void updateSelectAll(boolean isSelectAll) {
        for (int i= 0; i< mData.size(); i++) {
            TestEntity entity = mData.get(i);
            if(entity.isHeader){
                entity.setSelectAll(isSelectMode);
            }else {
                entity.setSubSelected(isSelectMode);
                if(isSelectAll){
                    updateSelectId(isSelectAll, i);
                }else{
                    selectList.clear();
                }
            }
        }
        notifyDataSetChanged();
    }

    private void updateSelectId(boolean isSelected, int pos) {
        if (isSelected) {
            if (!selectList.contains(pos)) {
                selectList.add(pos);
            }
        } else {
            selectList.remove(Integer.valueOf(pos));
        }
        L.e("info", "selected list = " + GsonUtils.toJsonString(selectList));
    }

    public void updateSelect(int pos, TestEntity entity) {
        if(entity.isHeader){
            boolean isSelected = !entity.isSelectAll();
            entity.setSelectAll(isSelected);
            updateSelectId(isSelected, pos);
            List<TestEntity> subList = entity.getSubList();
            for (int i = 0; i < subList.size(); i++) {
                TestEntity subEntity = subList.get(i);
                subEntity.setSubSelected(isSelected);
                updateSelectId(isSelected, pos + i + 1);
            }
        }else {
            boolean isSubSelect = !entity.isSubSelected();
            updateSelectId(isSubSelect, pos);
            entity.setSubSelected(isSubSelect);
            int parentIndex = entity.getParentIndex();
            TestEntity parentEntity = mData.get(parentIndex);
            //L.e("info", "parentIndex = "  + parentIndex + ", parentEntity =" + GsonUtils.toJsonString(parentEntity));
            if(parentEntity != null){
                if(isSubSelect){
                    List<TestEntity> subList = parentEntity.getSubList();
                    boolean isAllSelect = true;
                    for(TestEntity testEntity : subList){
                        if(!testEntity.isSubSelected()){
                            isAllSelect = false;
                            break;
                        }
                    }
                    parentEntity.setSelectAll(isAllSelect);
                }else {
                    parentEntity.setSelectAll(false);
                    updateSelectId(false, parentIndex);
                }
            }
        }
        notifyDataSetChanged();
    }


    public void deleteSelect() {
        int size = mData.size();
        for(int i = size -1; i >= 0; i--){
            if(selectList.remove(Integer.valueOf(i))){
                mData.remove(mData.get(i));
            }
        }
        L.e("info", "delete select list = " + GsonUtils.toJsonString(selectList));
        size = mData.size();
        int parentIndex = 0;
        TestEntity parentEntity = null;
        ArrayList<TestEntity> subList = null;
        int subIndex = 0;
        for(int i = 0; i < size; i++){
            TestEntity entity = mData.get(i);
            if(entity.isHeader){
                L.e(i + " isHeader");
                if(subList != null){
                    parentEntity.setSelectAll(false);
                    parentEntity.setSubList(subList);
                }
                subIndex = 0;
                parentIndex = i;
                parentEntity = entity;
                entity.setParentIndex(parentIndex);
                subList = new ArrayList<>();
            }else {
                subIndex++;
                entity.setParentIndex(parentIndex);
                entity.setSubIndex(subIndex);
                if(subList == null){
                    L.e("subList is null, " + i + ", entity = " + GsonUtils.toJsonString(entity));
                    subList = new ArrayList<>();
                }
                subList.add(entity);
            }
        }
        L.e("info", "mData = " + GsonUtils.toJsonString(mData));
        notifyDataSetChanged();
    }
}
