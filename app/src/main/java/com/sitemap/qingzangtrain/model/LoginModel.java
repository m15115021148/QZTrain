package com.sitemap.qingzangtrain.model;

/**
 * @desc 登录的实体类
 * Created by zhangfan on 2017/1/11
 */
public class LoginModel {
    private String result;//结果（1：成功，2：失败）
    private String errorMsg;//登录失败时的错误信息
    private String userID;//用户id
    private String type;//用户类型（1：管理员、2：列车员、3：维修员、4：领导）

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
