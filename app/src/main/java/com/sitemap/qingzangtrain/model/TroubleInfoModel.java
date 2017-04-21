package com.sitemap.qingzangtrain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 * 告警详情处理
 */

public class TroubleInfoModel {
    private String time;//				时间点
    private String processName;//   处理进度(0未下发，1下发中，2已下发，3反馈确认待检修指派，4反馈误报，5已指派待检修，6检修反馈确认)
    private String processInfo;//     处理进度描述（后台注意加上信息发起者的名称）
    private List<ProcessListModel> processList;//     进度详细列表（JSONArray）

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(String processInfo) {
        this.processInfo = processInfo;
    }

    public List<ProcessListModel> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessListModel> processList) {
        this.processList = processList;
    }
}
