package com.wlan.familymembers.view.activity.personal;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.ChangePhoneModel;
import com.wlan.familymembers.presenter.personal.ChangePhonePresenter;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 类作用：
 * Created by Administrator on 2018/9/28.
 */

public class ChangePhoneActivity extends BaseActivity<ChangePhoneModel, ChangePhonePresenter> implements Contract.ChangePhoneContract.View {
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
    @BindView(R.id.btn_confirm_change)
    Button btnConfirmChange;
    private Timer timer;
    private TimerTask timerTask;
    int i = 60;
    boolean isRun = true;
    Handler myHandler = new myHandler(this);
    String tel;
    String tel2;
    String phone;
    String userId;
    String code;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_change_phone;
    }

    @Override
    public void initView() {
        timer = new Timer();
        createTimerTask();
        tvContent.setText("修改手机号");
        phone = getIntent().getStringExtra("phone");

    }
    //这一部分为获取验证码的操作
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

    static class myHandler extends Handler {
        WeakReference<ChangePhoneActivity> changePhoneActivityWeakReference;

        myHandler(ChangePhoneActivity changePhoneActivity) {
            changePhoneActivityWeakReference = new WeakReference<ChangePhoneActivity>(changePhoneActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    int i = (int) msg.obj;
                    if (i == 0) {
                        changePhoneActivityWeakReference.get().timer.cancel();
                        changePhoneActivityWeakReference.get().isRun = false;
                        changePhoneActivityWeakReference.get().createTimerTask();
                        changePhoneActivityWeakReference.get().timer = new Timer();
                        changePhoneActivityWeakReference.get().btnCode.setText("请重新获取");
                    }else {
                        changePhoneActivityWeakReference.get().btnCode.setText("还剩" + i + "秒重试");
                    }
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
                if (!isRun) {
                    isRun = false;
                    i = 60;
                    timer.schedule(timerTask, 0, 1000);
                }
                String type = String.valueOf(2);
                tel = etPhone.getText().toString().trim();
                if (RegexUtils.isMobileExact(tel)) {
                    if (!tel.equals(phone)) {
                        showLoadingDialog();
                        presenter.getCode(type, phone);
                    } else {
                        showToast("前后手机号不能一致");
                    }
                } else {
                    showToast("新手机号输入不正确");
                }

            }
        });
        btnConfirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel2 = etPhone.getText().toString().trim();
                userId= SPUtils.getInstance().getString("userId");
                if (RegexUtils.isMobileExact(tel2)) {
                    if (tel2.equals(tel)) {
                        showLoadingDialog();
                        presenter.changePhone(userId,code,tel2);
                    } else {
                        showToast("输入的手机号前后不一致");
                    }
                } else {
                    showToast("输入的手机号不正确");
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
    public void getCodeSuccess(String code) {
        hideLoadingDialog();
        showToast("已发送");
        this.code=code;
        etCode.setText(code);

    }

    @Override
    public void changePhoneSuccess() {
        hideLoadingDialog();
        showToast("修改成功");
        finish();

    }
}
