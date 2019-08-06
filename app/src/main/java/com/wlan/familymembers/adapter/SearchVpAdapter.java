package com.wlan.familymembers.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wlan.familymembers.view.fragment.ServiceListContentFragment;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/11/7.
 */

public class SearchVpAdapter extends FragmentPagerAdapter {
    private List<String> list;
    private List<Fragment> fragmentList;


    public SearchVpAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
//        this.list = list;
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return list.get(position);
//    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
