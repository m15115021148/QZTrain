package com.sitemap.qingzangtrain.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.application.MyApplication;


/**
 * 登录失败页面
 *
 * @author created by zhangfan on 2017/1/11
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private LoginActivity mContext;//本来
    private TextView mResult;//textview显示信息
    private String resultMsg = null;//msg
    private LinearLayout tel;//是否显示提示页面 没有登录的页面
    private String telephone = "021-38748327";
    private TextView mPhone,mMac;//电话显示textview,mac地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
        initNetData();
    }

    /**
     * 初始化数据
     */
    private void initNetData() {
        resultMsg = "您的设备未在后台中登记，请联系后台管理人员将该设备的唯一标识符"
                + "添加到“安全监控系统”后台中，然后重启此应用即可使用。";
        mResult.setText(resultMsg);
        mPhone.setText(telephone);
//        mMac.setText(MyApplication.getMac());
        String mac = getIntent().getStringExtra("imei");
        if (mac.equals("000000000000000")){
            mac = SplashActivity.getDeviceID(this);
        }
        mMac.setText(mac);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mResult = (TextView) findViewById(R.id.login_msg);
        tel = (LinearLayout) findViewById(R.id.login_tel);
        tel.setOnClickListener(this);
        mPhone = (TextView) findViewById(R.id.telphone);
        mMac = (TextView) findViewById(R.id.login_mac);
    }

    @Override
    public void onClick(View v) {
        if (v == tel) {//打电话
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + telephone));
            mContext.startActivity(intent);
        }
    }

}
