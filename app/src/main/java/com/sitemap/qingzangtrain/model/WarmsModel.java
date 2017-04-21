package com.sitemap.qingzangtrain.model;

/**
 * @desc 获取单车故障列表
 * Created by chenmeng on 2017/2/27.
 */

public class WarmsModel {
    private String carriage;//	 车厢
    private String carriageNum;//车厢编号
    private String troubleName;//故障名称

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    public String getCarriageNum() {
        return carriageNum;
    }

    public void setCarriageNum(String carriageNum) {
        this.carriageNum = carriageNum;
    }

    public String getTroubleName() {
        return troubleName;
    }

    public void setTroubleName(String troubleName) {
        this.troubleName = troubleName;
    }
}
