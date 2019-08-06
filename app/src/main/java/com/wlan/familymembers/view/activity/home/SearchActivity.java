package com.wlan.familymembers.view.activity.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.SearchVpAdapter;
import com.wlan.familymembers.bean.GoodsBean;
import com.wlan.familymembers.bean.ServiceListBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.SearchListModel;
import com.wlan.familymembers.presenter.home.SearchListPresenter;
import com.wlan.familymembers.view.fragment.SearchFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * 类作用：
 * Created by Administrator on 2018/10/15.
 */

public class SearchActivity extends BaseActivity<SearchListModel, SearchListPresenter> implements Contract.SearchListContract.View {

    public static final int TYPE_GOODS = 1;
    public static final int TYPE_SERVICE = 2;
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll_input_name)
    LinearLayout llInputName;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tfl_content)
    TagFlowLayout tflContent;
    @BindView(R.id.tl_menu)
    TabLayout tlMenu;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private String key;
    private String type;
    private SearchFragment goodsFragment;
    private SearchFragment serviceFragment;
    private int style;
    private List<String> historyList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        tflContent.setVisibility(View.VISIBLE);
        tlMenu.setVisibility(View.GONE);
        vpContent.setVisibility(View.GONE);

        tlMenu.setupWithViewPager(vpContent);
        goodsFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",TYPE_GOODS);
        goodsFragment.setArguments(bundle);
        serviceFragment = new SearchFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type",TYPE_SERVICE);
        serviceFragment.setArguments(bundle1);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(goodsFragment);
        fragmentList.add(serviceFragment);
//        SearchVpAdapter adapter = new SearchVpAdapter(getSupportFragmentManager(),fragmentList);
//        vpContent.setAdapter(adapter);
        tlMenu.addTab(tlMenu.newTab().setText("商品"));
        tlMenu.addTab(tlMenu.newTab().setText("服务"));
        //取出历史记录
        outHistory();
    }
    private void outHistory() {
        String keys = SPUtils.getInstance().getString("key");
        if (keys != null && keys.length() > 0) {
            String[]  historys = keys.split(",");
            historyList = Arrays.asList(historys);
        } else {
            historyList = new ArrayList<>();
        }
    }

    @Override
    public void onEvent() {
        tflContent.setAdapter(new TagAdapter<String>(historyList) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv= (TextView) View.inflate(SearchActivity.this,R.layout.item_history,null);
                tv.setText(o);
                return tv;
            }
        });
        tflContent.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                tvSearch.setText(selectPosSet.toString());
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = etName.getText().toString().trim();
                showLoadingDialog();
                presenter.getSearchList(key,1,10,type);
                saveHistory(key);
            }
        });
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveHistory(String key) {
        boolean compare=true;
        for (int i=0;i<historyList.size();i++){
            if (!historyList.get(i).equals(key)){
                compare=true;
            }else {
                compare=false;
                break;
            }
        }
        if (compare){
            historyList.add(key);
        }


    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {
        hideLoadingDialog();
        showToast(msg);
    }


    @Override
    public void getSearchListSuccess(boolean isGoodsLastPage, boolean isServiceLastPage, List<GoodsBean> goodsBeans, List<ServiceListBean> serviceListBeans) {
        hideLoadingDialog();
        goodsFragment.addData(isGoodsLastPage, goodsBeans, TYPE_GOODS);
        serviceFragment.addData(isServiceLastPage,serviceListBeans,TYPE_SERVICE);
    }
}
