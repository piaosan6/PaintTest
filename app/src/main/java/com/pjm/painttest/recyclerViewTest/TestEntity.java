package com.pjm.painttest.recyclerViewTest;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

public class TestEntity extends SectionEntity<DataBean> {

    private boolean isSelectAll = false;
    private boolean isSubSelected = false;
    private int subIndex; //0 表示是标题， 1内容的开始
    private List<TestEntity> subList; //标题下的内容列表
    private int parentIndex; //父标题所在位置


    public TestEntity(boolean isHeader, String header, int parentIndex) {
        super(isHeader, header);
        this.parentIndex = parentIndex;
    }

    public TestEntity(DataBean dataBean, int subIndex, int parentIndex) {
        super(dataBean);
        this.subIndex = subIndex;
        this.parentIndex = parentIndex;
    }

    public boolean isSelectAll() {
        return isSelectAll;
    }

    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
    }

    public List<TestEntity> getSubList() {
        return subList;
    }

    public boolean isSubSelected() {
        return isSubSelected;
    }

    public void setSubSelected(boolean subSelected) {
        isSubSelected = subSelected;
    }

    public int getSubIndex() {
        return subIndex;
    }

    public void setSubIndex(int subIndex) {
        this.subIndex = subIndex;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    //设置改标题下的子容器数量
    public void setSubList(List<TestEntity> subList) {
        this.subList = subList;
    }

}
