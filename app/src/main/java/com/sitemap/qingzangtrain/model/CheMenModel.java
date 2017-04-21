package com.sitemap.qingzangtrain.model;

/**
 * @desc 车门
 * Created by chenmeng on 2017/2/16.
 */

public class CheMenModel {
    private String cheMen1YGL	;//右端隔离1,	（0、未隔离；1隔离）
    private String cheMen1ZGL	;//左端隔离1,
    private String cheMen1YKG	;//右端开关1,	(0、打开；1、关闭)
    private String cheMen1ZKG	;//左端开关1,
    private String cheMen2YGL	;//右端隔离2,
    private String cheMen2ZGL	;//左端隔离2,
    private String cheMen2YKG	;//右端开关2,
    private String cheMen2ZKG	;//左端开关2,
    private String cheMen1GZM		;//故障码1,
    private String cheMen2GZM		;//故障码2,
    private String time				;//数据上报时间,
    private String carriageNum	;//车厢
    private String carriageName	;//车厢编号
    private String preventSpeed;//    防滑速度
    private String location    ;//     运行位置

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

    public String getCheMen1YGL() {
        return cheMen1YGL;
    }

    public void setCheMen1YGL(String cheMen1YGL) {
        this.cheMen1YGL = cheMen1YGL;
    }

    public String getCheMen1ZGL() {
        return cheMen1ZGL;
    }

    public void setCheMen1ZGL(String cheMen1ZGL) {
        this.cheMen1ZGL = cheMen1ZGL;
    }

    public String getCheMen1YKG() {
        return cheMen1YKG;
    }

    public void setCheMen1YKG(String cheMen1YKG) {
        this.cheMen1YKG = cheMen1YKG;
    }

    public String getCheMen1ZKG() {
        return cheMen1ZKG;
    }

    public void setCheMen1ZKG(String cheMen1ZKG) {
        this.cheMen1ZKG = cheMen1ZKG;
    }

    public String getCheMen2YGL() {
        return cheMen2YGL;
    }

    public void setCheMen2YGL(String cheMen2YGL) {
        this.cheMen2YGL = cheMen2YGL;
    }

    public String getCheMen2ZGL() {
        return cheMen2ZGL;
    }

    public void setCheMen2ZGL(String cheMen2ZGL) {
        this.cheMen2ZGL = cheMen2ZGL;
    }

    public String getCheMen2YKG() {
        return cheMen2YKG;
    }

    public void setCheMen2YKG(String cheMen2YKG) {
        this.cheMen2YKG = cheMen2YKG;
    }

    public String getCheMen2ZKG() {
        return cheMen2ZKG;
    }

    public void setCheMen2ZKG(String cheMen2ZKG) {
        this.cheMen2ZKG = cheMen2ZKG;
    }

    public String getCheMen1GZM() {
        return cheMen1GZM;
    }

    public void setCheMen1GZM(String cheMen1GZM) {
        this.cheMen1GZM = cheMen1GZM;
    }

    public String getCheMen2GZM() {
        return cheMen2GZM;
    }

    public void setCheMen2GZM(String cheMen2GZM) {
        this.cheMen2GZM = cheMen2GZM;
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
