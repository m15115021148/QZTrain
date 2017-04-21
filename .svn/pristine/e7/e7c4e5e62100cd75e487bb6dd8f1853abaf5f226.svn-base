package com.sitemap.qingzangtrain.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.util.DialogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_set)
public class SetActivity extends BaseActivity implements View.OnClickListener {
    private SetActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.set_about)
    private RelativeLayout mAbout;//关于
    private SharedPreferences preferences;//保存
    @ViewInject(R.id.set_exit)
    private RelativeLayout mExit;//退出登录



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
        mAbout.setOnClickListener(this);
        mExit.setOnClickListener(this);
        mTitle.setText("设置");
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            mContext.finish();
        }
        if (v == mAbout){
            Intent intent = new Intent(mContext,AboutActivity.class);
            startActivity(intent);
        }
        if (v == mExit){
            View dialog = DialogUtil.customPromptDialog(mContext, "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //退出所有的activity
                            Intent intent = new Intent();
                            intent.setAction(BaseActivity.TAG_ESC_ACTIVITY);
                            sendBroadcast(intent);
//                System.exit(0);
                            finish();
                        }
                    }, null);
            TextView txt = (TextView) dialog.findViewById(R.id.dialog_tv_txt);
            txt.setText("确定退出吗？");
        }
    }
}
