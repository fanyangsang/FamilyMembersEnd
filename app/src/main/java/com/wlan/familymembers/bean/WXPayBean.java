package com.wlan.familymembers.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 类作用：
 * Created by Administrator on 2019/4/17.
 */

public class WXPayBean {


    /**
     * package : Sign=WXPay
     * appid : wx9cc4a7a2a1ff958a
     * sign : 565EDBF12D31C36E5FE38D2A6D8286C2
     * partnerid : 1531823821
     * prepayid : wx180933027500386ea43226850750272731
     * noncestr : o2RDiQjZ6Xsr5txay6EP7eU7fq4V3Vdm
     * timestamp : 1555551182
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
