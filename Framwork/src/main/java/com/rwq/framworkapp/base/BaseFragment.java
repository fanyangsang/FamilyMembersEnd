package com.rwq.framworkapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rwq.framworkapp.contract.ContractProxy;
import com.rwq.framworkapp.utils.LoadingViewUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/4/25.
 */

public abstract class BaseFragment<M extends BaseModel, P extends BasePresenter> extends com.trello.rxlifecycle.components.support.RxFragment {
    protected Unbinder unbinder;
    protected View rootView;
    protected Context mContext = null;//context
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据

    //    定义Presenter
    protected P presenter;
    private Toast toast;
    private LoadingViewUtil loadingViewUtil;

    //    获取布局资源文件
    protected abstract int getLayoutId();

    //    初始化数据

    protected abstract void initView(Bundle bundle);

    //    初始化事件Event

    protected abstract void onEvent();

    //   获取抽取View对象
    protected abstract BaseView getViewImp();

    //    获得抽取接口Model对象
    protected Class getModelClazz() {
        return (Class<M>) ContractProxy.getModelClazz(getClass(), 0);
    }

    //    获得抽取接口Presenter对象
    protected Class getPresenterClazz() {
        return (Class<P>) ContractProxy.getPresenterClazz(getClass(), 1);
    }
    //初始化Fragment的布局，加载布局和渲染数据通常在这个方法里面执行。
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        if (getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        unbinder = ButterKnife.bind(this, rootView);
        bindMVP();
        initView(savedInstanceState);
        return rootView;
    }
    //设置Fragment是否可见，如果可见则进行懒加载操作
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    /**
     * 获取presenter 实例
     */
    private void bindMVP() {
        if (getPresenterClazz() != null) {
            presenter = getPresenterImpl();
            presenter.context = getActivity();
            bindVM();
        }
    }
    //执行该方法时，与Fragment绑定的Activity的onCreate方法已经执行完成并返回，在该方法内可以进行与Activity交互的UI操作
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onEvent();
    }

    private <T> T getPresenterImpl() {
        return ContractProxy.getInstance().presenter(getPresenterClazz());
    }
    //Fragment由不可见状态变为可见状态
    @Override
    public void onStart() {
        if (presenter == null) {
            bindMVP();
        }
        super.onStart();
    }

    private void bindVM() {
        if (presenter != null && !presenter.isViewBind() && getModelClazz() != null && getViewImp() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), presenter);
            ContractProxy.getInstance().bindView(getViewImp(), presenter);
            presenter.context = getActivity();
        }
    }
    //初始化Fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    //执行完onCreatView方法后，立刻执行本方法
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
        if (presenter == null) {
            bindMVP();
        }
    }

    /**
     * 进行懒加载
     */
    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }

    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected abstract void lazyFetchData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (presenter != null) {
            ContractProxy.getInstance().unbindView(getViewImp(), presenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), presenter);
        }
    }
    public void showToast(String info) {
        try {
            if (toast != null) {
                toast.cancel();
            }
        } catch (Exception e) {
        }
        toast = Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT);
        toast.show();
    }


    public void showLoadingDialog() {
        //throw new NullPointerException("999999");
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(getActivity());
        }
        loadingViewUtil.showLoading();

    }

    public void showLoadingDialog(String msg) {
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(getActivity());
        }
        loadingViewUtil.showLoading(msg);

    }
    public void showLoadingDialog(String msg,String style) {
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(getActivity());
        }
        loadingViewUtil.showLoading(msg,style);
    }

    public void showLoadingDialog(String msg, String style, int color) {
        if (loadingViewUtil == null) {
            loadingViewUtil = new LoadingViewUtil(getActivity());
        }
        loadingViewUtil.showLoading(msg, style, color);
    }
    public void hideLoadingDialog() {
        if (loadingViewUtil != null) {
            loadingViewUtil.hideLoadingDialog();
        }
    }

    public void LoadingViewUpdateProgress(int progress) {
        if (loadingViewUtil != null) {
            loadingViewUtil.updateProgress(progress);
        }
    }

}
