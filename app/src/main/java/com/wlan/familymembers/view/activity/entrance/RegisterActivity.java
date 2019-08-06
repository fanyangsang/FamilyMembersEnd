package com.wlan.familymembers.view.activity.entrance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.app.FamilyMembersApp;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.entrance.RegisterModel;
import com.wlan.familymembers.presenter.entrance.RegisterPresenter;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class RegisterActivity extends BaseActivity<RegisterModel, RegisterPresenter> implements Contract.RegisterContract.View {


    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private TimerTask timerTask;
    private Timer timer;
    int i = 60;
    boolean isRun = false;
    String phone;
    String code;
    String newPassword;
    String confirmPassword;
    String phoneTwo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        timer = new Timer();
        createTimerTask();
    }

    private void createTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                i--;
                Message message = myHandler.obtainMessage();
                message.what = 200;
                message.obj = i;
                myHandler.sendMessage(message);
            }
        };

    }

    Handler myHandler = new myHandler(this);



    static class myHandler extends Handler {
        WeakReference<RegisterActivity> registerActivityWeakReference;

        myHandler(RegisterActivity registerActivity) {
            registerActivityWeakReference = new WeakReference<RegisterActivity>(registerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    int i = (int) msg.obj;
                    if (i == 0) {
                        registerActivityWeakReference.get().btnCode.setText("请重试");
                        registerActivityWeakReference.get().timer.cancel();
                        registerActivityWeakReference.get().isRun = false;
                        registerActivityWeakReference.get().createTimerTask();
                        registerActivityWeakReference.get().timer = new Timer();
                    }else {
                        registerActivityWeakReference.get().btnCode.setText("还剩" + i + "秒重试");
                    }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onEvent() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = etPhone.getText().toString().trim();
                code=etCode.getText().toString().trim();
                newPassword=etNewPassword.getText().toString().trim();
               confirmPassword=etConfirmPassword.getText().toString().trim();
               if (TextUtils.isEmpty(phone)){
                   showToast("手机号不能为空");
               }else if (phone.length()<11){
                   showToast("手机号输入不正确");
               }else if (TextUtils.isEmpty(code)){
                   showToast("验证码不能为空");
               } else if (code.length()<6){
                   showToast("验证码输入不正确");
               }else if (TextUtils.isEmpty(newPassword)){
                   showToast("密码不能为空");
               }else if (newPassword.length()<6){
                   showToast("密码需大于6位");
               }else if (TextUtils.isEmpty(confirmPassword)){
                   showToast("确认密码不能为空");
               }else if (confirmPassword.length()<6){
                   showToast("确认密码需大于6位");
               }else if (!confirmPassword.equals(newPassword)){
                   showToast("前后密码不一致");
               }else if (!phone.equals(phoneTwo)){
                   showToast("输入的手机号前后不一致");
               } else {
                   showLoadingDialog();
                   presenter.register(phone,newPassword,code);
               }
            }
        });
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRun) {
                    isRun = false;
                    i = 60;
                    timer.schedule(timerTask, 0, 1000);
                }
                phoneTwo=etPhone.getText().toString().trim();
                String type=String.valueOf(1);
                showLoadingDialog();
                presenter.getCode(type,phoneTwo);
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
    @Override
    public void registerSuccess() {
        hideLoadingDialog();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void getCodeSuccess() {
        hideLoadingDialog();
        showToast("已发送");
    }
}
