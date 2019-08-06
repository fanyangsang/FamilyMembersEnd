package com.rwq.framworkapp.contract;

import com.rwq.framworkapp.base.BaseModel;
import com.rwq.framworkapp.base.BasePresenter;
import com.rwq.framworkapp.base.BaseView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/4/24.
 */

public class ContractProxy {
    private static final ContractProxy contractProxy = new ContractProxy();

    Map<Class, Object> objectMap;

    private ContractProxy() {
        objectMap = new HashMap<>();
    }

    public static ContractProxy getInstance() {
        return contractProxy;
    }

    /**
     * presenter 通过反射，获得定义class时声明的父类的泛型参数的类型
     *
     * @param clazz
     * @param index
     * @return
     */
    public static Class<BasePresenter> getPresenterClazz(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return BasePresenter.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return BasePresenter.class;
        }
        if (!(params[index] instanceof Class)) {
            return BasePresenter.class;
        }
        return (Class) params[index];
    }

    public static Class<BaseModel> getModelClazz(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return BaseModel.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return BaseModel.class;
        }
        if (!(params[index] instanceof Class)) {
            return BaseModel.class;
        }
        return (Class<BaseModel>) params[index];
    }

    /**
     * 获取presenter
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T presenter(Class clazz) {
        if (!objectMap.containsKey(clazz)) {
            initInstance(clazz);
        }
        BasePresenter presenter = null;
        try {
            presenter = (BasePresenter) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) presenter;
    }

    /**
     * 进行初始化
     *
     * @param clazz
     */
    private void initInstance(Class clazz) {
        try {
            objectMap.put(clazz, clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 绑定 View
     *
     * @param presenter
     * @param <V>
     * @return
     */
    public <V> V bindView(BaseView view, BasePresenter presenter) {

        if (view != presenter.getView()) {
            if (presenter.getView() != null) {
                presenter.detachView();
            }
            presenter.attachView(view);
        }
        return (V) view;
    }

    /**
     * 绑定Persenter
     *
     * @param clzz
     * @param var1
     * @param <T>
     * @return
     */
    public <T> T bindPresenter(Class clzz, BaseView var1) {
        if (!objectMap.containsKey(clzz)) {
            //            init(clzz);
        }
        BasePresenter presenter = null;
        try {
            presenter = ((BasePresenter) clzz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (var1 != presenter.getView()) {
            if (presenter.getView() != null) {
                presenter.detachView();
            }
            presenter.attachView(var1);
        }
        return (T) presenter;
    }

    // 初始化model add map
    public <M> M bindModel(Class clzz, BasePresenter presenter) {
        if (!objectMap.containsKey(clzz)) {
            initInstance(clzz);
        }
        BaseModel model = null;
        try {
            model = ((BaseModel) clzz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (model != presenter.getModel()) {
            if (presenter.getModel() != null) {
                presenter.detachModel();
            }
            presenter.attachModel(model);
        }
        return (M) model;
    }

    // 解除绑定 移除map
    public void unbindPresenter(Class clzz, BaseView var1) {
        if (objectMap.containsKey(clzz)) {
            BasePresenter presenter = null;
            try {
                presenter = ((BasePresenter) clzz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (var1 != presenter.getView()) {
                if (presenter.getView() != null)
                    presenter.detachView();
                objectMap.remove(clzz);
            }
        }
    }

    // 解除绑定 移除map
    public void unbindView(BaseView view, BasePresenter presenter) {

        if (view != presenter.getView()) {
            if (presenter.getView() != null)
                presenter.detachView();
        }
    }

    // 解除绑定 移除map
    public void unbindModel(Class clzz, BasePresenter presenter) {
        if (objectMap.containsKey(clzz)) {
            BaseModel model = null;
            try {
                model = ((BaseModel) clzz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (model != presenter.getModel()) {
                if (presenter.getModel() != null)
                    presenter.detachModel();
                objectMap.remove(clzz);
            }
        }
    }


}
