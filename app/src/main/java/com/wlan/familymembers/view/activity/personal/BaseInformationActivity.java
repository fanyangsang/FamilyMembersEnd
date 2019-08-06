package com.wlan.familymembers.view.activity.personal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.BaseInformationBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.dingyiview.CircleImageView;
import com.wlan.familymembers.model.personal.BaseInformationModel;
import com.wlan.familymembers.presenter.personal.BaseInformationPresenter;
import com.wlan.familymembers.utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/21.
 */

public class BaseInformationActivity extends BaseActivity<BaseInformationModel, BaseInformationPresenter> implements Contract.BaseInformationContract.View {

    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_my_address)
    TextView tvMyAddress;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.civ_img)
    CircleImageView civImg;
    @BindView(R.id.ll_photo)
    LinearLayout llPhoto;
    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.ll_pet_name)
    LinearLayout llPetName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.btn_keep)
    Button btnKeep;
    private String mFilePath;
    private String name;
    private File imgFile;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private static final int PICTURE_LIB = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int PICTURE_cmamra = 1;
    private static final int TIME = 201;
    private AlertDialog dialog;
    String userId;
    String phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_information;
    }

    @Override
    public void initView() {
        userId = SPUtils.getInstance().getString("userId");
        showLoadingDialog();
        presenter.getBaseInformation(userId);
        tvMyAddress.setText("基本信息");
        requestPermissions();
        creadAlertdialog();
        createDialog();


    }

    /**
     * 申请必要权限
     */
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions.size() != 0) {
                // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        CAMERA_REQUEST_CODE);
            }
        }

    }

    /**
     * 创建拍照弹框
     */
    public void creadAlertdialog() {
        //初始化builder
        builder = new AlertDialog.Builder(this);
        //加载自定义的view
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.item_dialog_picture, null);
        ViewHolderPicture viewHolderPicture = new ViewHolderPicture(view);
        viewHolderPicture.tvLibraryPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开图库 0
                openPictureLib(PICTURE_LIB);
            }
        });
        viewHolderPicture.tvCameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openCamera(PICTURE_cmamra);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        viewHolderPicture.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        dialog = builder.create();


    }

    /**
     * 打开图库
     *
     * @param pictureLib
     */
    private void openPictureLib(int pictureLib) {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, pictureLib);
    }

    /**
     * 打开相机
     *
     * @param picture_cmamra
     */

    private void openCamera(int picture_cmamra) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        mFilePath = Environment.getExternalStorageDirectory().getPath() + "/enjoyTheGameCache";
        name = System.currentTimeMillis() + ".jpg";
        File file = new File(mFilePath);
        if (!file.exists()) {
            file.mkdir();
        }
        File imgFile = new File(file, name);
        if (!imgFile.exists()) {
            imgFile.createNewFile();
        }
        Uri photoUri;
        if (Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(this, "com.yykj.FamilyMembers.provider", imgFile);
            //    intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            //  intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            photoUri = Uri.fromFile(file); // 传递路径
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
        startActivityForResult(intent, picture_cmamra);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    //startActivityForResult选择完照片后回调到这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICTURE_LIB:
                    Uri imgUri = data.getData();
                    if (imgUri != null) {
                        //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                        //通过uri找到该文件
                        imgFile = getFileByUri(imgUri);
                        Glide.with(this).load(imgUri).into(civImg);
                        showLoadingDialog();
                        dialog.dismiss();
                        presenter.uploadPicture(imgFile, userId);
                    }
                    break;
                case PICTURE_cmamra:
                    imgFile = new File(new File(mFilePath), name);
                    Glide.with(this).load(imgFile).into(civImg);
                    showLoadingDialog();
                    dialog.dismiss();
                    presenter.uploadPicture(imgFile, userId);
                    break;
            }

        }
    }

    /**
     * 通过uri找到该文件
     *
     * @param uri
     * @return
     */
    public File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            //            Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }


    /**
     * 创建性别弹框
     */
    private void createDialog() {
        View view;
        //加载自定义的builder
        builder = new AlertDialog.Builder(this);
        view = LayoutInflater.from(this).inflate(R.layout.dialog_sex, null);
        final ViewHolderSex viewHolderSex = new ViewHolderSex(view);
        viewHolderSex.tvMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSex.setText("男");
                viewHolderSex.tvMan.setTextColor(Color.parseColor("#e10e05"));
                viewHolderSex.tvWoman.setTextColor(Color.parseColor("#000000"));
                alertDialog.dismiss();
            }
        });
        viewHolderSex.tvWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSex.setText("女");
                viewHolderSex.tvMan.setTextColor(Color.parseColor("#000000"));
                viewHolderSex.tvWoman.setTextColor(Color.parseColor("#e10e05"));
                alertDialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        alertDialog = builder.create();
    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
                alertDialog.show();

            }
        });
        llPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creadAlertdialog();
                dialog.show();
            }
        });
        llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseInformationActivity.this, ChangePhoneActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
        btnKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etNickName.getText().toString().trim();
                String sex = tvSex.getText().toString().trim();
                //sex如果是男 择输出0  否则输出1
                String i = String.valueOf(sex.equals("男") ? 0 : 1);
                showLoadingDialog();
                presenter.keep(userId, i, name);
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

    static class ViewHolderSex extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_man)
        TextView tvMan;
        @BindView(R.id.tv_woman)
        TextView tvWoman;

        ViewHolderSex(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void BaseInformation(BaseInformationBean baseInformationBean) {
        hideLoadingDialog();
        showToast("查询成功");
        etNickName.setText(baseInformationBean.getNickName());
        if (baseInformationBean.getSex() == 0) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
        phone = baseInformationBean.getPhone();
        tvPhone.setText(phone);
//        tvTime.setText(TimeUtils.nianyueri(String.valueOf(baseInformationBean.getCreateDate()), null));
        //截取时间
        tvTime.setText(baseInformationBean.getCreateDate().substring(0,10));
        if (!baseInformationBean.getPic().isEmpty()) {
            if (baseInformationBean.getPic().contains("http")) {
                Glide.with(this).load(baseInformationBean.getPic()).into(civImg);
            } else {
                Glide.with(this).load(HttpUtils.BASE_URL + baseInformationBean.getPic()).into(civImg);
            }
        } else {
            return;
        }

    }

    @Override
    public void uploadPicture() {
        hideLoadingDialog();
        showToast("提交成功");

    }

    @Override
    public void keepSuccess() {
        hideLoadingDialog();
        showToast("更改成功");
        finish();

    }

    static class ViewHolderPicture extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_library_picture)
        TextView tvLibraryPicture;
        @BindView(R.id.tv_camera_photo)
        TextView tvCameraPhoto;
        @BindView(R.id.tv_cancel)
        TextView tvCancel;
        @BindView(R.id.rl_root)
        RelativeLayout rlRoot;

        ViewHolderPicture(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
