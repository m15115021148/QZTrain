package com.sitemap.qingzangtrain.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.config.RequestCode;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.http.HttpUtil;
import com.sitemap.qingzangtrain.model.LoginModel;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.HorizontalProgressView;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import java.util.Set;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录进行中页面
 * @author created by chenmeng on 2016/11/14
 */
public class SplashActivity extends BaseActivity implements Runnable {
    private SplashActivity mContext;//本类
    private HttpUtil http;//网络请求
    private RoundProgressDialog progressDialog;//加载条
    private PackageManager pm;//获得PackageManager对象
    private TextView appVersion;//版本号

    private HorizontalProgressView progress;//进度条
    private static final int MSG_SET_ALIAS = 5001;//极光设置 别名
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        appVersion = (TextView) findViewById(R.id.app_version);
        MyApplication.deviceID = getDeviceID(this);

        pm = getPackageManager();
        progress=(HorizontalProgressView)findViewById(R.id.progress);
        progress.start();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        appVersion.setText("V" + getVersion());
        if (http == null) {
            http = new HttpUtil(handler);
        }
        handler.postDelayed(this, 3000);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();// 关闭进度条
            }
            switch (msg.what) {
                case HttpUtil.SUCCESS:
                    if (msg.arg1 == RequestCode.LOGIN) {
                        LoginModel loginModel = JSON.parseObject(msg.obj.toString(), LoginModel.class);
                        if (loginModel != null) {
                            Intent intent = new Intent();
                            if (loginModel.getResult().equals("1")) {//成功
                                MyApplication.loginModel = loginModel;
                                intent.setClass(mContext, MainActivity.class);

                                JPushInterface.setAlias(getApplicationContext(), MyApplication.loginModel.getUserID(), mAliasCallback);
                                setStyleCustom();
                            } else {
                                intent.setClass(mContext, LoginActivity.class);
                                intent.putExtra("imei", MyApplication.deviceID);
                            }
                            startActivity(intent);
                            mContext.finish();
                        }else {
                            ToastUtil.showBottomShort(mContext, RequestCode.ERRORINFO);
                            finish();
                        }
                    }
                    break;
                case HttpUtil.FAILURE:
                    ToastUtil.showBottomShort(mContext, RequestCode.ERRORINFO);
                    finish();
                    break;
                case 1:
//                    getLoginMsg(MyApplication.getMac());
                    getLoginMsg(MyApplication.deviceID);
                    break;
                case MSG_SET_ALIAS://推送
                    JPushInterface.setAlias(getApplicationContext(), MyApplication.loginModel.getUserID(), mAliasCallback);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取登录信息
     *
     * @param mac
     */
    private void getLoginMsg(String mac) {
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(mContext);
            if (progressDialog != null && !progressDialog.isShowing()) {
            }
            http.sendGet(RequestCode.LOGIN, WebUrlConfig.login(mac));
        } else {
            ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
        }
    }

    /**
     * 改写物理按键——返回的逻辑
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handler.removeCallbacks(this);
            mContext.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(this);
            //强制回收
        System.gc();
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(1);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置极光别名回调
     */
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("TAG", logs);
                    Log.i("TAG", alias);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("TAG", logs);
                    if (MyApplication.getNetObject().isNetConnected()) {
                        handler.sendMessageDelayed(handler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        ToastUtil.showBottomShort(mContext,"网络无法连接！");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.i("TAG", logs);
            }
        }

    };

    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom() {
        Log.i("TAG","aaaaaaaaaaaaaa");
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(mContext, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.drawable.icon_03;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setDefaultPushNotificationBuilder(builder);
//        JPushInterface.setPushNotificationBuilder(2, builder);
//        Toast.makeText(mContext, "Custom Builder - 2", Toast.LENGTH_SHORT).show();
    }


//    private void showInspectorRecordNotification() {
//        RemoteViews customView = new RemoteViews(mContext.getPackageName(), R.layout.view_custom);
////        customView.setTextViewText(R.id.tvName_inspectPlan, planInfo.convertlineId2lineName(mContext, MyApplication.getInstance().getAppData().getUserId()));
////        customView.setTextViewText(R.id.tvTime_inspectPlan, planInfo.getPlanYm());
////        customView.setTextViewText(R.id.tvPlanSate_inspectPlan, planInfo.convertstateId2stateText(context));
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
//        mBuilder.setContent(customView)
//                .setContentIntent(getDefalutIntent(PendingIntent.FLAG_UPDATE_CURRENT))
//                .setWhen(System.currentTimeMillis())
//                .setTicker("")
//                .setPriority(Notification.PRIORITY_DEFAULT)
//                .setOngoing(false)
//                .setSmallIcon(R.mipmap.icon);
//        Notification notify = mBuilder.build();
//        notify.contentView = customView;
//        notify.flags |= Notification.FLAG_AUTO_CANCEL; // 点击通知后通知栏消失
//        // 通知id需要唯一，要不然会覆盖前一条通知
//        int notifyId = (int) System.currentTimeMillis();
//        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(notifyId, notify);
//    }

    /**
     * 获取手机设备id
     */
    public static String getDeviceID(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    1);
        }
        TelephonyManager tm = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

}
