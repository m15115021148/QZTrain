package com.sitemap.qingzangtrain.model;

import java.io.Serializable;

/**
 * @desc 获取统计信息（首页）
 * Created by chenmeng on 2017/2/13.
 */

public class StatisticsInfoModel implements Serializable{
    private String num8;//故障车数量
    private String num7;// GPS故障数量
    private String num6;//停止中数量
    private String num4;//行驶中数量
    private String num5;//备用车数
    private String num2;//离线数据
    private String num3;//在线总数据
    private String num1;//列车总数据

    public String getNum8() {
        return num8;
    }

    public void setNum8(String num8) {
        this.num8 = num8;
    }

    public String getNum7() {
        return num7;
    }

    public void setNum7(String num7) {
        this.num7 = num7;
    }

    public String getNum6() {
        return num6;
    }

    public void setNum6(String num6) {
        this.num6 = num6;
    }

    public String getNum4() {
        return num4;
    }

    public void setNum4(String num4) {
        this.num4 = num4;
    }

    public String getNum5() {
        return num5;
    }

    public void setNum5(String num5) {
        this.num5 = num5;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }
}
