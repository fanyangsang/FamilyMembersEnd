package com.wlan.familymembers.bean;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/11/6.
 */

public class ServiceOrderBean {

    /**
     * orderNum : 115412163138451978
     * orderStatus : 3
     * payType : 1
     * price : 20
     * priceFinal :
     * orderContent : [{"pic":"/userfiles/1/files/hm/servicehm/2018/11/201810271212095922_x.jpg","serviceName":"洗电脑","userhmAddressVo":{"id":"1540795705818","name":"大大","address":"河南省/郑州市/金水区","addressServer":"广播电视台","lon":"34.759047","lat":"113.6860090","phone":"18746352482","defaultAddress":0,"userId":"1539310186164"},"subTime":"2018-11-30 17:22","price":"80.52","workerName":"工人001","workerId":"1539420262485","workerTel":"13569091001","sellPoint":"专注洗电脑五十年"}]
     * downTime : 2018-11-03 11:38
     */

    private String orderNum;
    private int orderStatus;
    private int payType;
    private String price;
    private String priceFinal;
    private String downTime;
    private String orderid;
    private List<OrderContentBean> orderContent;

    public String getOrderNum() {
        return orderNum;
    }

    public String getOrderid(){
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setOrderid(String orderid){
        orderid = orderNum;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceFinal() {
        return priceFinal;
    }

    public void setPriceFinal(String priceFinal) {
        this.priceFinal = priceFinal;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public List<OrderContentBean> getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(List<OrderContentBean> orderContent) {
        this.orderContent = orderContent;
    }

    public static class OrderContentBean {
        /**
         * pic : /userfiles/1/files/hm/servicehm/2018/11/201810271212095922_x.jpg
         * serviceName : 洗电脑
         * userhmAddressVo : {"id":"1540795705818","name":"大大","address":"河南省/郑州市/金水区","addressServer":"广播电视台","lon":"34.759047","lat":"113.6860090","phone":"18746352482","defaultAddress":0,"userId":"1539310186164"}
         * subTime : 2018-11-30 17:22
         * price : 80.52
         * workerName : 工人001
         * workerId : 1539420262485
         * workerTel : 13569091001
         * sellPoint : 专注洗电脑五十年
         */

        private String pic;
        private String serviceName;
        private UserhmAddressVoBean userhmAddressVo;
        private String subTime;
        private String price;
        private String workerName;
        private String workerId;
        private String workerTel;
        private String sellPoint;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public UserhmAddressVoBean getUserhmAddressVo() {
            return userhmAddressVo;
        }

        public void setUserhmAddressVo(UserhmAddressVoBean userhmAddressVo) {
            this.userhmAddressVo = userhmAddressVo;
        }

        public String getSubTime() {
            return subTime;
        }

        public void setSubTime(String subTime) {
            this.subTime = subTime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getWorkerName() {
            return workerName;
        }

        public void setWorkerName(String workerName) {
            this.workerName = workerName;
        }

        public String getWorkerId() {
            return workerId;
        }

        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }

        public String getWorkerTel() {
            return workerTel;
        }

        public void setWorkerTel(String workerTel) {
            this.workerTel = workerTel;
        }

        public String getSellPoint() {
            return sellPoint;
        }

        public void setSellPoint(String sellPoint) {
            this.sellPoint = sellPoint;
        }

        public static class UserhmAddressVoBean {
            /**
             * id : 1540795705818
             * name : 大大
             * address : 河南省/郑州市/金水区
             * addressServer : 广播电视台
             * lon : 34.759047
             * lat : 113.6860090
             * phone : 18746352482
             * defaultAddress : 0
             * userId : 1539310186164
             */

            private String id;
            private String name;
            private String address;
            private String addressServer;
            private String lon;
            private String lat;
            private String phone;
            private int defaultAddress;
            private String userId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddressServer() {
                return addressServer;
            }

            public void setAddressServer(String addressServer) {
                this.addressServer = addressServer;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getDefaultAddress() {
                return defaultAddress;
            }

            public void setDefaultAddress(int defaultAddress) {
                this.defaultAddress = defaultAddress;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
