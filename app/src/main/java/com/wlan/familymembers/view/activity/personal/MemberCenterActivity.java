package com.wlan.familymembers.view.activity.personal;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.dingyiview.CircleImageView;
import com.wlan.familymembers.model.personal.MemberCenterModel;
import com.wlan.familymembers.presenter.personal.MemberCenterPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/20.
 */

public class MemberCenterActivity extends BaseActivity<MemberCenterModel, MemberCenterPresenter> implements Contract.MemberCenterContract.View {
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.civ_img)
    CircleImageView civImg;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.tv_current_grade)
    TextView tvCurrentGrade;
    @BindView(R.id.tv_vip_bag)
    TextView tvVipBag;
    @BindView(R.id.btn_bag_receive)
    Button btnBagReceive;
    @BindView(R.id.tv_door_ticket)
    TextView tvDoorTicket;
    @BindView(R.id.tv_mall_ticket)
    TextView tvMallTicket;
    @BindView(R.id.tv_ticket_explain)
    TextView tvTicketExplain;
    @BindView(R.id.tv_perfect_information)
    TextView tvPerfectInformation;
    @BindView(R.id.tv_go_perfect)
    TextView tvGoPerfect;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_member_center;
    }

    @Override
    public void initView() {
        tvPoints.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

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
