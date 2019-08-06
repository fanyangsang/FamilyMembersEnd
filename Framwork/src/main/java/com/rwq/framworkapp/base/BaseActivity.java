package com.rwq.framworkapp.base;

import android.os.Bundle;
import android.widget.Toast;

import com.rwq.framworkapp.contract.ContractProxy;
import com.rwq.framworkapp.utils.LoadingViewUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/4/24.
 */

public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends RxAppCompatActivity {
    protected P presenter;
    private Unbinder binder;
    private Toast toast;
    LoadingViewUtil loadingViewUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            binder = ButterKnife.bind(this);
            bindMvp();
            initView(savedInstanceState);
            initView();
            onEvent();
        }
    }

    public void initView(Bundle savedInstanceState) {
    }


    private void bindMvp() {
        if (getPresenterClazz() != null) {
            presenter = getPresenterImpl();
            presenter.context = this;
            bindVM();
        }
    }

    //    获得抽取接口Model对象
    protected Class getModelClazz() {
        return (Class<M>) ContractProxy.getModelClazz(getClass(), 0);
    }

    //    获得抽取接口Presenter对象
    private Class getPresenterClazz() {
        return (Class<P>) ContractProxy.getPresenterClazz(getClass(), 1);
    }

    private <T> T getPresenterImpl() {
        return ContractProxy.getInstance().presenter(getPresenterClazz());
    }

    /**
     * 布局文件
     */
    public abstract int getLayoutId();

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 事件
     */
    public abstract void onEvent();

    public abstract BaseView getBaseView();
    //这个方法在activity在由不可见变为可见时调用
    @Override
    protected void onStart() {
        if (presenter == null) {
            bindMVP();
        }
        super.onStart();
    }

    private void bindMVP() {
        if (getPresenterClazz() != null) {
            presenter = getPresenterImpl();
            presenter.context = this;
            bindVM();
        }
    }

    private void bindVM() {
        if (presenter != null && !presenter.isViewBind() && getModelClazz() != null && getBaseView() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), presenter);
            ContractProxy.getInstance().bindView(getBaseView(), presenter);
            presenter.context = this;
        }
    }
    //这个方法在activity在被销毁之前调用，之后activity的状态将变为销毁状态
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binder != null) {
            binder.unbind();
        }
        if (presenter != null) {
            ContractProxy.getInstance().unbindView(getBaseView(), presenter);
            ContractProxy.getInstance().unbindModel (getModelClazz(), presenter);
            presenter = null;
        }
        if (loadingViewUtil != null) {
            loadingViewUtil.destroy();
            loadingViewUtil = null;
        }
    }

    public void showToast(String info) {
        try {
            if (toast != null) {
                toast.cancel();
            }
        } catch (Exception e) {
        }
        toast = Toast.makeText(this, info, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showLoadingDialog() {
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(this);
        }
        loadingViewUtil.showLoading();
    }

    public void showLoadingDialog(String msg) {
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(this);
        }
        loadingViewUtil.showLoading(msg);
    }

    public void showLoadingDialog(String msg, String style) {
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(this);
        }
        loadingViewUtil.showLoading(msg, style);
    }

    public void showLoadingDialog(String msg, String style, int color) {
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(this);
        }
        loadingViewUtil.showLoading(msg, style, color);
    }

    public void hideLoadingDialog() {
        if (loadingViewUtil != null) {
            loadingViewUtil.hideLoadingDialog();
        }
    }
}
