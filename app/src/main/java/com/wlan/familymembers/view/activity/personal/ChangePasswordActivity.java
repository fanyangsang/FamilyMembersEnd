package com.wlan.familymembers.view.activity.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.ChangePasswordModel;
import com.wlan.familymembers.presenter.personal.ChangePasswordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class ChangePasswordActivity extends BaseActivity<ChangePasswordModel, ChangePasswordPresenter> implements Contract.ChangePasswordContract.View {
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.btn_submission)
    Button btnSubmission;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initView() {
        tvContent.setText("修改密码");

    }

    @Override
    public void onEvent() {
        btnSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId= SPUtils.getInstance().getString("userId");
                String OldPassword=etOldPassword.getText().toString().trim();
                String NewPassword=etNewPassword.getText().toString().trim();
                String ConfirmPassword=etConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(OldPassword)||OldPassword.length()<6||TextUtils.isEmpty(NewPassword)||NewPassword.length()<6||TextUtils.isEmpty(ConfirmPassword)||ConfirmPassword.length()<6){
                    showToast("密码不能为空，且密码的长度应大于6位");
                }else if(!NewPassword.equals(ConfirmPassword)){
                    showToast("前后密码输入不一致");
                }else {
                    showLoadingDialog();
                    presenter.changePassword(userId,OldPassword,NewPassword);
                }
            }
        });
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
        hideLoadingDialog();

    }
    @Override
    public void changePasswordSuccess() {
        hideLoadingDialog();
        showToast("修改密码成功");
        finish();
    }
}
