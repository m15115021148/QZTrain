package com.sitemap.qingzangtrain.model;

/**
 * @desc 获取常用短语
 * Created by chenmeng on 2017/2/10.
 */

public class DesInfoModel {

    /**
     * desID : 1
     * desInfo : 核查信息A
     * type : 1
     */

    private String desID;
    private String desInfo;
    private String isSelect;//是否选择

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

    public String getDesID() {
        return desID;
    }

    public void setDesID(String desID) {
        this.desID = desID;
    }

    public String getDesInfo() {
        return desInfo;
    }

    public void setDesInfo(String desInfo) {
        this.desInfo = desInfo;
    }

}
