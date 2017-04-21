package com.sitemap.qingzangtrain.config;

/**
 * com.sitemap.wisdomjingjiang.config.RequestCode
 *
 *
 * @author zhangfan
 *         接口请求需要用的辨识常量
 *         create at 2016年1月11日 13:30:35
 */
public class RequestCode {

    //	string类型常量
    public static final String ERRORINFO = "服务器无法连接，请稍后再试！";//网络连接错误信息
    public static final String NOLOGIN = "网络无法连接！";//网络无法连接

    /**注册规则*/
    public static final String REGISTERTOOT = "密码长度应在6-16位，必须是字母跟数字组合";

    //	int类型常量
    public static final int REGISTER = 0x0001;//注册常量
    public static final int LOGIN = 0x0002;//登录常量

    public static final int GETTROUBLES = 0x003;//获取故障（告警）列表
    public static final int GETDESINFO  = 0x004;//获取常用短语
    public static final int UPLOADCHECKINFO  = 0x005;//提交核查信息
    public static final int UPLOADFIXINFO  = 0x006;//提交维修信息
    public static final int GETSTATICTICSINFO = 0x007;//获取统计信息（首页）
    public static final int UPLOADCHECK = 0x008;//更改核查状态
    public static final int UPLOADFIX = 0x009;//更改核查状态
    public static final int DATALOAD = 0x010;//历史数据 查询 刷新 第一次加载
    public static final int DATALOADMORE = 0x011;//历史数据 查询 更多
    public static final int GETTROUBLESMORE = 0x012;//获取故障（告警）列表 更多

    public static final int GETTRAININFO = 0x011;//获取火车运行状态
    public static final int GETTROUBLEINFO = 0x012;//获取故障（告警）处理详情
    public static final int GETTOUBLESNUM = 0x013;//获取故障（告警）列表统计数据
    public static final int GETWARMNUMS = 0x014;//获取单车故障统计数据（首页底部）
    public static final int GETWARMS = 0x015;//获取单车故障列表
    public static final int GETCARRIAGES  = 0x016;//获取车厢列表
    public static final int GETMOVERANGES  = 0x017;//获取运行区间列表
    public static final int SEARCHCURRENTTRAININFO = 0x018;//获取列车实时数据

    public static final int GETVIDEO = 0x026;//下载视频

    //数据  筛选
    public static final String[] zhouWen = {
            " 1位 (℃)"," 2位 (℃)"," 3位 (℃)",
            " 4位 (℃)"," 5位 (℃)"," 6位 (℃)",
            " 7位 (℃)"," 8位 (℃)"," 环温 (℃)","状态"," 速度 (km/h)","运行区间","曲线"
    };
    public static final String[] gongDian = {
            "主漏电流(MA)","母线电压(V)","整流电压(V)",
            "供电制式",
            "1、2路","600V电流(A)","600V电压(V)"
    };
    public static final String[] zhiDong = {
            "制动缸 (kpa)","列车管 (kpa)","工作风缸(kpa)",
            "制动缸最大压力(kpa)","GPS速度(km/h)","防滑速度(km/h)","位置","制动状态","曲线"
    };
    public static final String[] fangHuaJi = {
            " 车速 (km/h)","1轴传感器","2轴传感器",
            "3轴传感器","4轴传感器","1轴排风阀",
            "2轴排风阀","3轴排风阀","4轴排风阀"
    };
    public static final String[] cheMen = {
            "1位端右门隔离","1位端左门隔离","1位端右门开/关",
            "1位端左门开/关","2位端右门隔离","2位端左门隔离",
            "2位端右门开/关","2位端左门开/关","车门1故障码","车门2故障码","GPS速度(km/h)","运行位置"
    };
    public static final String[] yanHuo = {
            "1号","2号","3号",
            "4号","5号","6号",
            "7号","8号","9号",
            "10号","11号","12号"
    };
    public static final String[] zhiYangJi = {
            "制氧机","膜制氧","空压机1",
            "空压机2","客室氧浓度","制氧机室氧浓度",
            "风门","富氧出口平均氧浓度","客室氧浓度","空压机压缩空气压力","制氧机故障码"," 海拔 (m)"
    };
    public static final String[] kongTiao = {
            "车厢温度(℃)"," 环温 (℃)","风","制暖",
            "制冷","空调状态","风门开度(%)",
            "制暖1-1","制暖1-2","制暖2-1",
            "制暖2-2","制冷1-1","制冷1-2",
            "制冷2-1","制冷2-2","冷凝1","冷凝2","曲线"
    };
    public static final String[] cheXiaDianYuan = {
            "充电机电流(A)","充、放电电流(A)","充电机状态",
            "逆变1电压(V)","逆变1频率(HZ)","逆变1状态",
            "逆变2电压(V)","逆变2频率(HZ)","逆变2状态"
    };
}
