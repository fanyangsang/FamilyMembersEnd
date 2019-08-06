package com.wlan.familymembers.view.activity.personal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.LatLng;
import com.blankj.utilcode.util.SPUtils;
import com.renwq.chooseaddress.ChooseAddressActivity;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.EditAddressModel;
import com.wlan.familymembers.presenter.personal.EditAddressPresenter;
import com.wlan.familymembers.view.activity.goodsorder.PaymentDetailsActivity;
import com.wlan.familymembers.view.activity.goodsorder.ServicePaymentDetailsActivity;

import butterknife.BindView;

/**
 * 类作用：修改地址
 * Created by Administrator on 2018/9/20.
 */

public class EditAddressActivity extends BaseActivity<EditAddressModel, EditAddressPresenter> implements Contract.EditAddressContract.View {
    AddressBean addressBean;
    private static final int REQUEST_CODE = 1;
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_my_address)
    TextView tvMyAddress;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    //服务地址
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    //详细地址
    @BindView(R.id.et_address_details)
    EditText etAddressDetails;
    @BindView(R.id.cb_rb)
    CheckBox cbRb;
    @BindView(R.id.btn_confirm_modify)
    Button btnConfirmModify;
    private String addressService;
    LatLng latLng;
    String lon;
    String lat;
    private String addressAll;
    private String userId;
    private String isdefault;
    String position;
    int i;
    int order;

    //    private updateList updateList;
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initView() {
        isdefault = 1 + "";
        userId = SPUtils.getInstance().getString("userId");
        //0表示新增地址  1表示编辑地址
        i = getIntent().getIntExtra("key", 0);
        //order=0表示从填写商品订单activity跳入的  =1 从服务订单跳来的
        order = getIntent().getIntExtra("order", 2);
        position = getIntent().getStringExtra("position");
        addressBean = (AddressBean) getIntent().getSerializableExtra("addressBean");
        //0表示新增地址  1表示编辑地址
        if (i == 0) {
            tvMyAddress.setText("新增地址");
            btnConfirmModify.setText("保存");

        } else {
            tvMyAddress.setText("编辑地址");
            btnConfirmModify.setText("确认修改");
            if (null != addressBean) {
                etName.setText(addressBean.getName());
                etPhone.setText(addressBean.getPhone());
                //服务地址
                tvAddress.setText(addressBean.getAddress());
                //服务地址
                addressService = addressBean.getAddress();
                lon = addressBean.getLon();
                lat = addressBean.getLat();
                position = addressBean.getId();
                //详细地址
                etAddressDetails.setText(addressBean.getAddressServer());
                if (isdefault == String.valueOf(1)) {
                    cbRb.setChecked(true);
                } else {
                    cbRb.setChecked(false);
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
        cbRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isdefault = 0 + "";
                } else {
                    isdefault = 1 + "";
                }
            }
        });
        btnConfirmModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String addressDetails = etAddressDetails.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast("名字不能为空");
                } else if (TextUtils.isEmpty(phone)) {
                    showToast("手机号不能为空");
                } else if (phone.length() < 11) {
                    showToast("手机号不正确");
                } else if (TextUtils.isEmpty(addressService)) {
                    showToast("服务地址不能为空");
                } else if (TextUtils.isEmpty(addressDetails)) {
                    showToast("详细地址不能为空");
                } else {
                    if (i == 0) {
                        showLoadingDialog();
                        presenter.submitAddressInformation("", userId, name, phone, addressDetails, addressService, lon, lat, isdefault);
                    } else {
                        showLoadingDialog();
                        presenter.submitAddressInformation(position, userId, name, phone, addressDetails, addressService, lon, lat, isdefault);
                    }

                }


            }
        });
        llMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAddressActivity.this, ChooseAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
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

    //选择完地图回调
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (data != null) {
                        //获得服务地址
                        addressService = data.getStringExtra("province") + data.getStringExtra("city") + data.getStringExtra("district");
                        latLng = data.getParcelableExtra("latlng");
                        lon = String.valueOf(latLng.longitude);
                        lat = String.valueOf(latLng.latitude);
                        tvAddress.setText(addressService);
                    }
                    break;
            }
        }
    }

    //修改 添加地址 设置默认地址成功
    @Override
    public void submitAddressInformationSuccess() {
        hideLoadingDialog();
        if (i == 0) {
            showToast("保存成功");
        } else {
            showToast("修改成功");
        }
        finish();
        //调用MyAddressActivity默认地址修改成功方法 会重新请求一遍所有地址
        if (MyAddressActivity.instance != null) {
            MyAddressActivity.instance.submitAddressInformation();
        }


    }

    //存入新地址后更改订单页面显示的地址
    @Override
    public void newaddress() {
        if (order == 0 && PaymentDetailsActivity.instance != null) {
            PaymentDetailsActivity.instance.address();
        }
        if (order == 1 && ServicePaymentDetailsActivity.instance != null) {
            ServicePaymentDetailsActivity.instance.address();
        }
    }
    //    public interface updateList{
    //        void updateList();
    //    }
    //    public void setListener(updateList updateList){
    //        this.updateList=updateList;
    //
    //    }
}
