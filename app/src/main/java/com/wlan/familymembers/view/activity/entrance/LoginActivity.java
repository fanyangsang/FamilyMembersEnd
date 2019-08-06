package com.wlan.familymembers.view.activity.entrance;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.app.FamilyMembersApp;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.entrance.LoginModel;
import com.wlan.familymembers.presenter.entrance.LoginPresenter;
import com.wlan.familymembers.view.activity.MainActivity;
import com.wlan.familymembers.view.activity.service.ServiceAgreementActivity;

import butterknife.BindView;

/**
 * 类作用：
 * Created by Administrator on 2018/9/13.
 */

public class LoginActivity extends BaseActivity<LoginModel, LoginPresenter> implements Contract.LoginContract.View {


    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_box)
    CheckBox cbBox;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_find_password)
    TextView tvFindPassword;
    //判断是否是退出登录 用于跳转到mainactivity中使用

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        String info = "我已同意<Font color='#df0f03'>" + "《圣之城装饰》" + "</Font>" + "的相关用户服务协议";
        tvAgreement.setText(Html.fromHtml(info));
        tvFindPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onEvent() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    showToast("手机号和密码不能为空");
                } else {
                    showLoadingDialog();
                    presenter.login(phone, password);
                }
            }
        });
        tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ServiceAgreementActivity.class);
                startActivity(intent);

            }
        });
        tvFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
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
        showToast(msg);
    }

    //登录成功后调用此方法
    @Override
    public void loginSuccess() {
        hideLoadingDialog();
        FamilyMembersApp.isLogin = true;
        //关闭登录界面 跳转至首页
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        //        overridePendingTransition(R.anim.up_in, R.anim.up_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();//注释掉这行,back键不退出activity
        //从登录界面不登录 点击返回时
        if (TextUtils.isEmpty(SPUtils.getInstance().getString("userId"))) {
            finish();
        } else {
            SPUtils.getInstance().clear();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void loginFail() {
        hideLoadingDialog();
        FamilyMembersApp.isLogin = false;
        showToast("用户名或密码错误");
    }
}
