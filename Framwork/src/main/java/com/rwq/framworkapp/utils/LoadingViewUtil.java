package com.rwq.framworkapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.rwq.framworkapp.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/5/22.
 */

public class LoadingViewUtil {
    private Context context;
    private LoadingViewUtil loadingViewUtil;
    private Dialog loadingDialog;


    public static String row_1_1 = "BallPulseIndicator";
    public static String row_1_2 = "BallGridPulseIndicator";
    public static String row_1_3 = "BallClipRotateIndicator";
    public static String row_1_4 = "BallClipRotatePulseIndicator";

    public static String row_2_1 = "SquareSpinIndicator";
    public static String row_2_2 = "BallClipRotateMultipleIndicator";
    public static String row_2_3 = "BallPulseRiseIndicator";
    public static String row_2_4 = "BallRotateIndicator";

    public static String row_3_1 = "CubeTransitionIndicator";
    public static String row_3_2 = "BallZigZagIndicator";
    public static String row_3_3 = "BallZigZagIndicator";
    public static String row_3_4 = "BallTrianglePathIndicator";


    public static String row_4_1 = "BallScaleIndicator";
    public static String row_4_2 = "LineScaleIndicator";
    public static String row_4_3 = "LineScalePartyIndicator";
    public static String row_4_4 = "BallScaleMultipleIndicator";

    public static String row_5_1 = "BallPulseSyncIndicator";
    public static String row_5_2 = "BallBeatIndicator";
    public static String row_5_3 = "LineScalePulseOutIndicator";
    public static String row_5_4 = "LineScalePulseOutRapidIndicator";


    public static String row_6_1 = "BallScaleRippleIndicator";
    public static String row_6_2 = "BallScaleRippleMultipleIndicator";
    public static String row_6_3 = "BallSpinFadeLoaderIndicator";
    public static String row_6_4 = "LineSpinFadeLoaderIndicator";

    public static String row_7_1 = "TriangleSkewSpinIndicator";
    public static String row_7_2 = "PacmanIndicator";
    public static String row_7_3 = "BallGridBeatIndicator";
    public static String row_7_4 = "SemiCircleSpinIndicator";
    private TextView textView;

    public LoadingViewUtil(Context context) {
        this.context = context;
    }


    /**
     * show Loading 框
     *
     * @param msg
     */
    private void showLoadingDialog(String msg, String loadingStyle, int color) {
        if (loadingDialog == null) {
            loadingDialog = new Dialog(context, R.style.BottomDialog);
            View view = View.inflate(context, R.layout.dialog_loading, null);
            if (msg != null && !TextUtils.isEmpty(msg)) {
                textView = view.findViewById(R.id.tv_content_text);
                textView.setVisibility(View.VISIBLE);
                textView.setText(msg);
            }
            AVLoadingIndicatorView loadingView = view.findViewById(R.id.av_loading_view);
            if (loadingStyle != null) {
                loadingView.setIndicator(loadingStyle);
            } else {
                loadingView.setIndicator(row_1_1);
            }
            if (color != 0) {
                loadingView.setIndicatorColor(color);
            } else {
                loadingView.setIndicatorColor(context.getResources().getColor(R.color.colorAccent));
            }
            loadingDialog.setContentView(view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = ConvertUtils.dp2px(120);
            layoutParams.width = ConvertUtils.dp2px(120);
            view.setLayoutParams(layoutParams);
            // loadingDialog.getWindow().setAttributes(new WindowManager.LayoutParams(layoutParams.width, layoutParams.height));
            loadingDialog.setCanceledOnTouchOutside(false);
            Drawable drawable = context.getResources().getDrawable(R.drawable.my_loading_bg);
            loadingDialog.getWindow().setBackgroundDrawable(drawable);
            loadingDialog.getWindow().setWindowAnimations(R.style.loading_anim);
        }
        loadingDialog.show();
    }


    public void showLoading(String msg) {
        showLoadingDialog(msg, null, 0);
    }

    public void showLoading(String msg, String style) {
        showLoadingDialog(msg, style, 0);
    }

    public void showLoading(String msg, String style, int color) {
        showLoadingDialog(msg, style, color);
    }



    public void showLoading() {
        showLoadingDialog(null, null, 0);
    }


    /**
     * 隐藏Loading
     */
    public void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    public void destroy() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
            loadingViewUtil = null;
            context = null;
        }
    }

    public void updateProgress(int progress) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            if (textView != null) {
                textView.setText("正在上传："+progress+"%");
            }
        }
    }
}
