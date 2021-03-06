package com.sitemap.qingzangtrain.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.sitemap.qingzangtrain.model.LoginModel;
import com.sitemap.qingzangtrain.util.NetworkUtil;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by chenmeng on 2016/10/18.
 */
public class MyApplication extends Application{
    /**application对象*/
    private static MyApplication instance;
    public static NetworkUtil netState;//网络状态
    public static LoginModel loginModel;//登录实体类
    private static final int REQUEST_EXTERNAL_STORAGE = 1;//动态添加权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static String deviceID = "";//设备id

    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
        netState = new NetworkUtil(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        JPushInterface.setDebugMode(true);// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

    public static MyApplication instance() {
        if (instance != null) {
            return instance;
        } else {
            return new MyApplication();
        }
    }

    /**
     * 初始化xutils框架
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    /**
     * 获取手机网络状态对象
     *
     * @return
     */
    public static NetworkUtil getNetObject() {
        if (netState != null) {
            return netState;
        } else {
            return new NetworkUtil(instance().getApplicationContext());
        }
    }

    /**
     * 设置段落中个别字体的颜色
     * @param tv         textview
     * @param txt        段落
     * @param lable      设置字体的颜色
     */
    public static void setTextColor(TextView tv, String txt, String lable) {
        SpannableStringBuilder style = new SpannableStringBuilder(txt);
        String[] split = lable.split(";");
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                if(txt.contains(split[i])){
                    int start = txt.indexOf(split[i]);
                    int end = start + split[i].length();
                    ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#1cace9"));
                    style.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    tv.setText(style);
                }
            }
        } else {
            tv.setText(style);
        }
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    /**
     * listview没有数据显示 的控件
     * @param context 本类
     * @param T AbsListView
     * @param txt 内容
     */
    public static void setEmptyShowText(Context context, AbsListView T, String txt){
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText(txt);
        emptyView.setTextSize(18);
        emptyView.setTextColor(Color.parseColor("#808080"));
        emptyView.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)T.getParent().getParent().getParent().getParent()).addView(emptyView);
        T.setEmptyView(emptyView);
    }

    /**
     * 获取手机读写 权限
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
