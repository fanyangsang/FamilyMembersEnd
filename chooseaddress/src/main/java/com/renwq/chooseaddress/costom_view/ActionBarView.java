package com.renwq.chooseaddress.costom_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renwq.chooseaddress.R;

public class ActionBarView extends FrameLayout {
    private int bgColor;
    private String ok;
    private float height;
    private int backBtnId;
    private String title;
    private int titleColor;
    private float titleSize;
    private int okColor;
    private float okSize;
    private ActionBarListener listener;


    {
        title = "选择地址";
        ok = "完成";
    }

    public ActionBarView(Context context) {
        super(context);
        init();
    }


    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionBarView);
        height = typedArray.getDimension(R.styleable.ActionBarView_action_bar_height, getResources().getDimension(R.dimen.action_bar_default_height));
        bgColor = typedArray.getColor(R.styleable.ActionBarView_action_bar_bg_color, getResources().getColor(R.color.colorPrimary));
        backBtnId = typedArray.getResourceId(R.styleable.ActionBarView_action_bar_back_icon, R.drawable.back);
        String title = typedArray.getString(R.styleable.ActionBarView_action_bar_title);
        if (!TextUtils.isEmpty(title)) {
            this.title = title;
        }
        titleColor = typedArray.getColor(R.styleable.ActionBarView_action_bar_title_color, getResources().getColor(R.color.colorWhite));
        titleSize = typedArray.getDimension(R.styleable.ActionBarView_action_bar_title_size, getResources().getDimension(R.dimen.action_bar_default_title_size));
        String ok = typedArray.getString(R.styleable.ActionBarView_action_bar_ok);
        if (!TextUtils.isEmpty(ok)) {
            this.ok = ok;
        }
        okColor = typedArray.getColor(R.styleable.ActionBarView_action_bar_ok_color, getResources().getColor(R.color.colorAccent));
        okSize = typedArray.getDimension(R.styleable.ActionBarView_action_bar_ok_size, getResources().getDimension(R.dimen.action_bar_default_ok_size));
        typedArray.recycle();
        init();

    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.costom_action_bar_view, null);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) height);
        view.setLayoutParams(layoutParams);
        view.setPadding(0, 0, (int) getResources().getDimension(R.dimen.action_bar_default_right_padding), 0);
        view.setBackgroundColor(bgColor);
        this.addView(view);
        ImageView ivBack = view.findViewById(R.id.iv_back_icon);
        ivBack.setImageResource(backBtnId);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_title)).setTextColor(titleColor);
        ((TextView) view.findViewById(R.id.tv_title)).setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        tvOk.setText(ok);
        tvOk.setTextColor(okColor);
        tvOk.setTextSize(TypedValue.COMPLEX_UNIT_PX,okSize);

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.actionBack();
                }
            }
        });

        tvOk.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                    if (null != listener) {
                        listener.actionChooseAddressComplete();
                    }
            }
        });


    }


    public interface ActionBarListener {

        void actionBack();


        void actionChooseAddressComplete();
    }

    public void setActionListener(ActionBarListener listener) {
        this.listener = listener;
    }
}

