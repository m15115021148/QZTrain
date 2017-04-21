package com.sitemap.qingzangtrain.model;

import java.io.Serializable;

/**
 * 人员信息列表
 * Created by chenmeng on 2017/2/9.
 */

public class PersonInfoModel implements Serializable{

    private String type;//人员类型（1：管理员、2：列车员、3：维修员）
    private String phone;//人员手机号
    private String name;//人员名称
    private String status;//人员状态（1：值班中，2：未当班）
    private String process;//故障处理进度
//    管理员处理进度 (0未下发，1下发中，2已下发，3反馈确认待检修指派，4反馈误报，5已指派待检修，6检修反馈确认)
//    乘务员处理进度 (0未处理，1处理中，2处理完成并反馈，3误报反馈处理完成)
//    检修员处理进度 (0未处理，1检修中，2检修完成并反馈)


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
