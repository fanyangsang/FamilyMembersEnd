package com.wlan.familymembers.dingyiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlan.familymembers.R;

import java.security.InvalidParameterException;

/**
 * 类作用：
 * Created by rwq_rwq on 18-9-19.
 */
public class LevelView extends RelativeLayout{
    private TextView tvLevel;
    private ImageView ivIcon;
    private int num;

    public LevelView(Context context) {
        super(context);
        init();
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.diy_view_level_view, this, true);
        tvLevel = view.findViewById(R.id.tv_level);
        ivIcon = view.findViewById(R.id.iv_icon);
    }


    public void setLevel(int num) {
        if (num < 0) {
            throw new InvalidParameterException("级别不能小于0");
        }
        this.num = num;
        tvLevel.setText("LV " + num);
    }
    public int getLevel() {
        return num;
    }
}
