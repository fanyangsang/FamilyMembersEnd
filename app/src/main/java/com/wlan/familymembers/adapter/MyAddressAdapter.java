package com.wlan.familymembers.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.view.activity.personal.EditAddressActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/20.
 */

public class MyAddressAdapter extends RecyclerView.Adapter {
    Context context;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    List<AddressBean> addressBeans;
    private MyAddressListener listener;

    public MyAddressAdapter(Context context, List<AddressBean> addressBeans) {
        this.context = context;
        this.addressBeans = addressBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_personal_my_address, parent, false);
        return new viewHolderNewAddress(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final viewHolderNewAddress viewHolderNewAddress = (MyAddressAdapter.viewHolderNewAddress) holder;
        viewHolderNewAddress.tvName.setText(addressBeans.get(position).getName());
        viewHolderNewAddress.tvPhone.setText(addressBeans.get(position).getPhone());
        viewHolderNewAddress.tvAddress.setText(addressBeans.get(position).getAddressServer() + addressBeans.get(position).getAddress());
        //底部加横线
        viewHolderNewAddress.cbDefaultButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //getDefaultAddress=0时 设置默认
        if (addressBeans.get(position).getDefaultAddress() == 0) {
            viewHolderNewAddress.cbDefaultButton.setChecked(true);
        }else{
            viewHolderNewAddress.cbDefaultButton.setChecked(false);
        }
        //Checked注册监听

        viewHolderNewAddress.cbDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取点击后的状态··
                boolean b = viewHolderNewAddress.cbDefaultButton.isChecked();
//                viewHolderNewAddress.cbDefaultButton.setChecked(false);
                listener.checkbox(position, addressBeans.get(position).getId(), b);
            }
        });

        viewHolderNewAddress.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddressActivity.class);
                //1代表是编辑地址，0代表新增地址
                intent.putExtra("key", 1);
                intent.putExtra("position", position);
                intent.putExtra("addressBean", addressBeans.get(position));
                context.startActivity(intent);
            }
        });
        viewHolderNewAddress.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(position);
            }
        });
        viewHolderNewAddress.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddressActivity.class);
                intent.putExtra("key", 1);
                intent.putExtra("position", position);
                intent.putExtra("addressBean", addressBeans.get(position));
                context.startActivity(intent);
            }
        });
    }

    private void createDialog(final int position) {
        View view;
        //初始化builder
        builder = new AlertDialog.Builder(context);
        //加载自定义的view
        view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
        ViewHolderDialog viewHolderDialog = new ViewHolderDialog(view);
        viewHolderDialog.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        viewHolderDialog.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                String id = addressBeans.get(position).getId();
                listener.deleteAddress(position, id);
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public int getItemCount() {
        return addressBeans.size();
    }

    static class viewHolderNewAddress extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.ll_content)
        LinearLayout llContent;
        @BindView(R.id.cb_default_button)
        CheckBox cbDefaultButton;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        viewHolderNewAddress(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderDialog {
        @BindView(R.id.btn_cancel)
        Button btnCancel;
        @BindView(R.id.btn_confirm)
        Button btnConfirm;

        ViewHolderDialog(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public interface MyAddressListener {

        void deleteAddress(int position, String id);

        //发送当前checkbox的点击状态
        void checkbox(int position, String id, boolean b);
    }

    public void setListener(MyAddressListener listener) {
        this.listener = listener;
    }

}
