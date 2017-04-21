package com.sitemap.qingzangtrain.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 数据统计
 *
 * @author chenmeng created by 2017-3-31
 */
@ContentView(R.layout.activity_data_statistics)
public class DataStatisticsActivity extends FragmentActivity implements View.OnClickListener {
    private DataStatisticsActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    private RoundProgressDialog progressDialog;//加载条
    @ViewInject(R.id.WebView)
    private WebView mWb;//webview
    private String url = "";//url
    private String dataType,type,chexiang,isValid,quJian,sTime,eTime;//类型 车次编号 开始时间 结束时间
    private String trainName;// 车次名称
    private String name;//类型名称


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 反射注解机制初始化
        x.view().inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        mContext = this;
        initData();
    }

    /**
     * 得到统计图url
     * @return
     */
    private String getWebUrl(String dataType,String type,String chexiang,String isValid,String quJian,String s_time,String e_time){
        String url = WebUrlConfig.getAppChart(dataType, type, chexiang, isValid, quJian, s_time, e_time);
        return url;
    }

    /**
     * 初始化webview
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        // 可加载js
        WebSettings setting = mWb.getSettings();

        // 设置webview自适应屏幕
        setting.setLoadWithOverviewMode(true);// 设置webview加载的页面的模式，也设置为true。这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上
        setting.setDomStorageEnabled(true);
        setting.setJavaScriptEnabled(true);
        setting.setSupportZoom(true);
//		setting.setTextSize(WebSettings.TextSize.NORMAL);
        setting.setDefaultFontSize(20);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);// 设置 缓存模式
//		setting.setBuiltInZoomControls(true);// 设置支持缩放
        // 同一编码
        setting.setDefaultTextEncodingName("UTF-8");
        mWb.loadUrl(url);
        // 用webview打开该网页
        mWb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
                // /返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.setMessage("加载中...");
                    progressDialog.show();
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                ToastUtil.showBottomShort(mContext,"网页加载失败！");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();// 关闭进度条
                }
            }

        });

    }

    /**
     * 改写物理按键——返回的逻辑
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWb.canGoBack()) {
                mWb.goBack();// 返回上一页面
                return true;
            } else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mBack.setOnClickListener(this);
        progressDialog = RoundProgressDialog.createDialog(this);
        dataType = getIntent().getStringExtra("dataType");
        type = getIntent().getStringExtra("type");
        chexiang = getIntent().getStringExtra("chexiang");
        isValid = getIntent().getStringExtra("isValid");
        quJian = getIntent().getStringExtra("quJian");
        sTime = getIntent().getStringExtra("s_time");
        eTime = getIntent().getStringExtra("e_time");
        trainName = getIntent().getStringExtra("name");
        if (dataType.equals("1")){
            name = "车轴温曲线";
        }else if (dataType.equals("3")){
            name = "车制动曲线";
        }else if (dataType.equals("8")){
            name = "车空调曲线";
        }
        mTitle.setText(trainName+chexiang+name);
        url = getWebUrl(dataType,type,chexiang,isValid,quJian,sTime,eTime);
        Log.w("jack","url:"+url);
        initWebView();
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            mContext.finish();
        }
    }
}
