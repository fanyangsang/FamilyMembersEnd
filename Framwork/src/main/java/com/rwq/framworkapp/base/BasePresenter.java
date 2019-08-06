package com.rwq.framworkapp.base;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/4/24.
 */

public class BasePresenter<V extends BaseView,M extends BaseModel> implements Presenter<V,M> {
    protected V view;
    protected M model;
    protected Context context;
    protected CompositeSubscription compositeSubscription;

    protected  void unSubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }
    protected void addSubscribe(Subscription subscriber) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscriber);
    }
    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void attachModel(M m) {
        this.model = m;
    }

    @Override
    public void detachView() {
        this.view = null;
        unSubscribe();
    }

    @Override
    public void detachModel() {
        this.model = null;
    }
    public V getView() {
        return view;
    }

    public boolean isViewBind() {
        return this.view != null;
    }

    public M getModel() {
        return model;
    }
}
