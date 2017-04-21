package com.sitemap.qingzangtrain.model;

/**
 * @desc 制动
 * Created by chenmeng on 2017/2/16.
 */

public class ZhiDongModel {
    private String zhiDongGYL		;//制动缸压力,
    private String lieCheGuanYL		;//列车管压力,
    private String gongZuoFenGYL	;//工作风缸压力,
    private String zhiDongGZDYL		;//制动缸最大压力,
    private String time				;//数据上报时间,
    private String carriageNum		;//车厢
    private String carriageName	;//车厢编号
    private String gpsSpeed      ;//  gps速度
    private String preventSpeed  ;//  防滑速度
    private String location      ;//   运行位置
    private String zhidongState  ;//   制动状态

    public String getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(String gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public String getPreventSpeed() {
        return preventSpeed;
    }

    public void setPreventSpeed(String preventSpeed) {
        this.preventSpeed = preventSpeed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZhidongState() {
        return zhidongState;
    }

    public void setZhidongState(String zhidongState) {
        this.zhidongState = zhidongState;
    }

    public String getZhiDongGYL() {
        return zhiDongGYL;
    }

    public void setZhiDongGYL(String zhiDongGYL) {
        this.zhiDongGYL = zhiDongGYL;
    }

    public String getLieCheGuanYL() {
        return lieCheGuanYL;
    }

    public void setLieCheGuanYL(String lieCheGuanYL) {
        this.lieCheGuanYL = lieCheGuanYL;
    }

    public String getGongZuoFenGYL() {
        return gongZuoFenGYL;
    }

    public void setGongZuoFenGYL(String gongZuoFenGYL) {
        this.gongZuoFenGYL = gongZuoFenGYL;
    }

    public String getZhiDongGZDYL() {
        return zhiDongGZDYL;
    }

    public void setZhiDongGZDYL(String zhiDongGZDYL) {
        this.zhiDongGZDYL = zhiDongGZDYL;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCarriageNum() {
        return carriageNum;
    }

    public void setCarriageNum(String carriageNum) {
        this.carriageNum = carriageNum;
    }

    public String getCarriageName() {
        return carriageName;
    }

    public void setCarriageName(String carriageName) {
        this.carriageName = carriageName;
    }
}
