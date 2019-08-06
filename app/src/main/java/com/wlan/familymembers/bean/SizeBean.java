package com.wlan.familymembers.bean;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/11/7.
 */

public class SizeBean {

    private List<尺寸Bean> 尺寸;

    public List<尺寸Bean> get尺寸() {
        return 尺寸;
    }

    public void set尺寸(List<尺寸Bean> 尺寸) {
        this.尺寸 = 尺寸;
    }

    public static class 尺寸Bean {
        /**
         * id : 10
         * val : 55`
         * attr : 尺寸
         */

        private String id;
        private String val;
        private String attr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }
    }
}
