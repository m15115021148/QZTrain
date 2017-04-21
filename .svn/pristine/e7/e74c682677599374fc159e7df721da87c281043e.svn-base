package com.sitemap.qingzangtrain.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity implements View.OnClickListener{
    private AboutActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    private PackageManager pm;//获得PackageManager对象
    @ViewInject(R.id.version)
    private TextView mVersion;//版本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    /**
     * 初始化view
     */
    private void initData() {
        mBack.setOnClickListener(this);
        mTitle.setText("关于");
        pm = getPackageManager();
        mVersion.setText("V" + getVersion());
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            mContext.finish();
        }
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
}
