package com.rwq.framworkapp.base;

/**
 * 类作用： view 层需要实现的方法
 * Created by rwq_Administrator on 2018/4/24.
 */

public interface BaseView<T> {
    /**
     * 提示错误信息
     *
     * @param msg 错误信息提示
     */
    void showErrorWithStatus(String msg);
}
