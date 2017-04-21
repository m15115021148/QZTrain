package com.sitemap.qingzangtrain.model;

/**
 * @desc 防滑剂
 * Created by chenmeng on 2017/2/16.
 */

public class FangHuaJiModel {
    private String cheSu		;//车速,
    private String suDu1	;//速度传感器1,（0、正常；1故障；2曾经故障过；3、故障且曾经故障）
    private String suDu2		;//速度传感器2,
    private String suDu3		;//速度传感器3,
    private String suDu4		;//速度传感器4,
    private String paiFen1  	;//排风阀1, （0、正常；1故障；2曾经故障过；3、故障且曾经故障）
    private String paiFen2		;//排风阀2,
    private String paiFen3		;//排风阀3,
    private String paiFen4		;//排风阀4,
    private String paiFen1DZ	;//排风阀动作1,	（0、正常；1、动作）
    private String paiFen2DZ	;//排风阀动作2,
    private String paiFen3DZ	;//排风阀动作3,
    private String paiFen4DZ	;//排风阀动作4,
    private String time			;//数据上报时间,
    private String carriageNum		;//车厢
    private String carriageName	;//车厢编号

    public String getCheSu() {
        return cheSu;
    }

    public void setCheSu(String cheSu) {
        this.cheSu = cheSu;
    }

    public String getSuDu1() {
        return suDu1;
    }

    public void setSuDu1(String suDu1) {
        this.suDu1 = suDu1;
    }

    public String getSuDu2() {
        return suDu2;
    }

    public void setSuDu2(String suDu2) {
        this.suDu2 = suDu2;
    }

    public String getSuDu3() {
        return suDu3;
    }

    public void setSuDu3(String suDu3) {
        this.suDu3 = suDu3;
    }

    public String getSuDu4() {
        return suDu4;
    }

    public void setSuDu4(String suDu4) {
        this.suDu4 = suDu4;
    }

    public String getPaiFen1() {
        return paiFen1;
    }

    public void setPaiFen1(String paiFen1) {
        this.paiFen1 = paiFen1;
    }

    public String getPaiFen2() {
        return paiFen2;
    }

    public void setPaiFen2(String paiFen2) {
        this.paiFen2 = paiFen2;
    }

    public String getPaiFen3() {
        return paiFen3;
    }

    public void setPaiFen3(String paiFen3) {
        this.paiFen3 = paiFen3;
    }

    public String getPaiFen4() {
        return paiFen4;
    }

    public void setPaiFen4(String paiFen4) {
        this.paiFen4 = paiFen4;
    }

    public String getPaiFen1DZ() {
        return paiFen1DZ;
    }

    public void setPaiFen1DZ(String paiFen1DZ) {
        this.paiFen1DZ = paiFen1DZ;
    }

    public String getPaiFen2DZ() {
        return paiFen2DZ;
    }

    public void setPaiFen2DZ(String paiFen2DZ) {
        this.paiFen2DZ = paiFen2DZ;
    }

    public String getPaiFen3DZ() {
        return paiFen3DZ;
    }

    public void setPaiFen3DZ(String paiFen3DZ) {
        this.paiFen3DZ = paiFen3DZ;
    }

    public String getPaiFen4DZ() {
        return paiFen4DZ;
    }

    public void setPaiFen4DZ(String paiFen4DZ) {
        this.paiFen4DZ = paiFen4DZ;
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
