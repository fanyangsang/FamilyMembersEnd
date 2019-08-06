package com.rwq.framworkapp.base;

/**
 * 类作用：
 * 基础的presenter
 * Created by rwq_Administrator on 2018/4/24.
 */

public interface Presenter<View, Model> {
    /**
     * 绑定view
     * @param view
     */
    void attachView(View view);

    /**
     * 绑定model
     * @param model
     */
    void attachModel(Model model);

    /**
     * 解除view
     */
    void detachView();

    /**
     * 解除model
     */
    void detachModel();


}
