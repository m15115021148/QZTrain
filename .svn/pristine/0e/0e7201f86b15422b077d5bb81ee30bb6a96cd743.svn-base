package com.sitemap.qingzangtrain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.adapter.BottomTopAdapter;
import com.sitemap.qingzangtrain.adapter.BottomTopTwoAdapter;
import com.sitemap.qingzangtrain.adapter.MainDrawerRightAdapter;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.model.StatisticsInfoModel;
import com.sitemap.qingzangtrain.model.WarmNumsModel;
import com.sitemap.qingzangtrain.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 首页底部弹出 视图 页面
 * @author chenmeng created by 2017/2/24
 */
@ContentView(R.layout.activity_main_bottom)
public class MainBottomActivity extends BaseActivity implements View.OnClickListener {
    private MainBottomActivity mContext;//本类
    @ViewInject(R.id.main_gridView)
    private GridView mRightGv;//右侧布局
    private StatisticsInfoModel statisticsInfoModel = null;//统计数据
    @ViewInject(R.id.menu_txt)
    private TextView mBottomTitle;//底部内容
    @ViewInject(R.id.menu_bottom)
    private LinearLayout mMenuBottom;//底部布局
    @ViewInject(R.id.bottom_gridView)
    private GridView mBottomGv;//底部布局
    @ViewInject(R.id.activity_main_bottom)
    private LinearLayout mContainer;//整个布局
    private WarmNumsModel warmModel = null;//故障数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");//无标题 label属性
        // 设置 窗口的显示位置
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
//        lp.windowAnimations = R.style.AnimActivity;
        getWindow().setAttributes(lp);
        mContext = this;
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mMenuBottom.setOnClickListener(this);
        if (MyApplication.loginModel.getType().equals("1")||MyApplication.loginModel.getType().equals("4")){
            mBottomTitle.setText("运行状态");
            statisticsInfoModel = (StatisticsInfoModel) getIntent().getSerializableExtra("StatisticsInfoModel");
            getRightGridView(statisticsInfoModel);
        }else{
            mBottomTitle.setText("列车状态");
            warmModel = (WarmNumsModel) getIntent().getSerializableExtra("WarmNumsModel");
            initTopGridView(warmModel);
            initTopTwoGridView(warmModel);
        }
    }

    /**
     * 管理员 数据显示
     * @param model
     */
    private void getRightGridView(StatisticsInfoModel model){
        MainDrawerRightAdapter adapter = new MainDrawerRightAdapter(mContext,model);
        mRightGv.setAdapter(adapter);
    }

    /**
     * 初始化 头部数据  乘务员 检修员
     */
    private void initTopGridView(WarmNumsModel model){
        final BottomTopAdapter adapter = new BottomTopAdapter(mContext,model);
        mRightGv.setAdapter(adapter);
        mRightGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getListData()!=null&&adapter.getListData().size()>0){
                    if (!adapter.getListData().get(position).getNum().equals("0")){
                        Intent intent = new Intent(mContext,WarmListActivity.class);
                        intent.putExtra("title",adapter.getListData().get(position).getTxt());
                        intent.putExtra("troubleType",adapter.getListData().get(position).getType());//0供电, 1轴温, 2制动, 3防滑器,
                        startActivity(intent);
                    }
                }
            }
        });
    }

    /**
     * 初始化 头部数据  乘务员 检修员
     */
    private void initTopTwoGridView(WarmNumsModel model){
        final BottomTopTwoAdapter adapter = new BottomTopTwoAdapter(mContext,model);
        mBottomGv.setAdapter(adapter);
        mBottomGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getListData()!=null&&adapter.getListData().size()>0){
                    if (!adapter.getListData().get(position).getNum().equals("0")){
                        Intent intent = new Intent(mContext,WarmListActivity.class);
                        intent.putExtra("title",adapter.getListData().get(position).getTxt());
                        intent.putExtra("troubleType",adapter.getListData().get(position).getType());//4烟火, 5车门, 6制氧机, 7车门, 8车下电源
                        startActivity(intent);
                    }
                }
                ;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mMenuBottom){
            mContext.finish();
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.bottom_enter,R.anim.bottom_exit);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int height = mContainer.getTop();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height) {
                mMenuBottom.callOnClick();
            }
        }
        return true;
    }
}
