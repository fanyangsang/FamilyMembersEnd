package com.wlan.familymembers.bean;

/**
 * 类作用：
 * Created by Administrator on 2018/10/11.
 */

public class ServiceClassificationBean {

    /**
     * id : 1539416518301
     * name : 家具维修
     * parentId : 0
     * pic : /userfiles/1/files/hm/servicehmCategory/2018/10/one.jpg
     */

    private String id;
    private String name;
    private String parentId;
    private String pic;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
