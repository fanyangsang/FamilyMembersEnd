package com.wlan.familymembers.view.activity.entrance;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.entrance.FindPasswordModel;
import com.wlan.familymembers.presenter.entrance.FindPasswordPresenter;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class FindPasswordActivity extends BaseActivity<FindPasswordModel, FindPasswordPresenter> implements Contract.FindPasswordContract.View {


    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    private Timer timer;
    private TimerTask timerTask;
    int i = 60;
    boolean isRun = false;
    String phone;
    String phoneTwo;
    String code;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_password;
    }

    @Override
    public void initView() {
        tvContent.setText("找回密码");
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
        WeakReference<FindPasswordActivity> findPasswordActivityWeakReference;

        myHandler(FindPasswordActivity findPasswordActivity) {
            findPasswordActivityWeakReference = new WeakReference<FindPasswordActivity>(findPasswordActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    int i = (int) msg.obj;
                    if (i == 0) {
                        findPasswordActivityWeakReference.get().timer.cancel();
                        findPasswordActivityWeakReference.get().isRun = false;
                        findPasswordActivityWeakReference.get().createTimerTask();
                        findPasswordActivityWeakReference.get().timer = new Timer();
                    }
                    findPasswordActivityWeakReference.get().btnCode.setText("还剩" + i + "秒重试");
            }
        }
    }


    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = String.valueOf(2);
                phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("手机号不能为空");
                } else if (phone.length() < 11) {
                    showToast("手机号不正确");
                } else {
                    showLoadingDialog();
                    presenter.getCode(type, phone);
                }

            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneTwo=etPhone.getText().toString().trim();
                String codeTwo=etCode.getText().toString().trim();
                String newPassword=etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phoneTwo)){
                    showToast("输入的手机号不能为空");
                }else if (phoneTwo.length()<11){
                    showToast("手机号不正确");
                }else if (TextUtils.isEmpty(codeTwo)){
                    showToast("验证码不能为空");
                }else if (!codeTwo.equals(code)){
                    showToast("验证码前后输入的不一致");
                }else if (TextUtils.isEmpty(newPassword)){
                    showToast("新密码不能为空");
                }else if (newPassword.length()<6){
                    showToast("密码的长度不应小于6位");
                }else {
                    showLoadingDialog();
                    presenter.findPassword(codeTwo,newPassword,phoneTwo);
                }
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
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    @Override
    public void getCodeSuccess(String code) {
        hideLoadingDialog();
        this.code=code;
        if (!isRun) {
            isRun = true;
            i = 60;
            timer.schedule(timerTask, 0, 1000);
        }
        etCode.setText(code);

    }

    @Override
    public void findPasswordSuccess() {
        hideLoadingDialog();
        showToast("找回密码成功");
        finish();
    }
}
