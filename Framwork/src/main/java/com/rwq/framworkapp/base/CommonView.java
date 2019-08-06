package com.rwq.framworkapp.base;

/**
 * 类作用：抽取View 接口中公共方法 处理相同回调更新UI
 * Created by rwq_Administrator on 2018/4/24.
 */

public interface CommonView {
    /**
     *  提示成功信息
     * @param msg
     */
    void showSuccessWithStatus(String msg);

    /**
     *  提示错误信息
     * @param msg
     */
    void showErrorWithStatus(String msg);

    /**
     *  提示消息
     * @param msg
     */
    void showsInfoWithStatus(String msg);
    /**
     *  进度框
     * @param msg
     */
    void showWithProgress(String msg);

    /**
     *
     */
    void dismiss();
}
