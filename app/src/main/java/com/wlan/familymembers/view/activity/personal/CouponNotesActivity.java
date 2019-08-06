package com.wlan.familymembers.view.activity.personal;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.CouponNotesModel;
import com.wlan.familymembers.presenter.personal.CouponNotesPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class CouponNotesActivity extends BaseActivity<CouponNotesModel, CouponNotesPresenter> implements Contract.CouponNotesContract.View {
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.wv_content)
    WebView wvContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_notes;
    }

    @Override
    public void initView() {
        tvContent.setText("我的优惠券");
        tvPoints.setText("说明");
        tvPoints.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }
}
