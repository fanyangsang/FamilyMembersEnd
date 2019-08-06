package com.wlan.familymembers.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018/3/2.
 */

public class RecyclerViewUtil {
    public static boolean isVisBottom(RecyclerView recyclerView){

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position（位置）
        //找到最后一个可见项目的位置
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        //当前屏幕所看到的的子项个数 >0 并且
        // 最后一个可见项目的位置 == 当前RecyclerView的所有子项个数 - 1  即滚动到倒数第二个项目时
        //并且滚动状态闲置时  return true;
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }
}
