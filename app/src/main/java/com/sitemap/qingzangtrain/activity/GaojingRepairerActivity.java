package com.sitemap.qingzangtrain.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.adapter.GaoJingRepairerAdapter;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.config.RequestCode;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.http.HttpUtil;
import com.sitemap.qingzangtrain.model.ResultModel;
import com.sitemap.qingzangtrain.model.TroublesModel;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.PullToRefreshLayout;
import com.sitemap.qingzangtrain.view.PullableLinearLayout;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 检修员 报警页面
 * @author chenmeng created by 2017/2/8
 */
@ContentView(R.layout.activity_gaojing_repairer)
public class GaojingRepairerActivity extends BaseActivity implements View.OnClickListener ,GaoJingRepairerAdapter.OnCallBackLook,PullToRefreshLayout.OnRefreshListener{
    private GaojingRepairerActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.listView)
    private ListView mLv;//列表
    private RoundProgressDialog progressDialog;//加载条
    private HttpUtil http;//网络请求
    private List<TroublesModel> mList;//数据
    private GaoJingRepairerAdapter adapter;//适配器
    private int type = 1;//进入的类别 1获取网络数据  2 main页面查询传入
    private String checkType = "";//查询类别
    private int currPos = 0;//当前选择的位置
    @ViewInject(R.id.refresh_layout)
    private PullToRefreshLayout mRefresh;//刷新布局
    @ViewInject(R.id.pull_layout)
    private PullableLinearLayout mPullLayout;//刷新布局
    private int page = 1;//当前页
    private List<TroublesModel> mListMore;//数据 更多
    private String troubleID = "";//id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();// 关闭进度条
            }
            switch (msg.what){
                case HttpUtil.SUCCESS:
                    //获取故障（告警）列表
                    if (msg.arg1 == RequestCode.GETTROUBLES){
                        currPos = 0;
                        page = 1;
                        mList = new ArrayList<>();
                        mList.clear();
                        mList = JSONObject.parseArray(msg.obj.toString(),TroublesModel.class);
                        initListView(mList);
                        mRefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
                        page+=1;
                    }
                    //获取故障（告警）列表 更多
                    if (msg.arg1 == RequestCode.GETTROUBLESMORE){
                        mListMore = new ArrayList<>();
                        mListMore.clear();
                        currPos = mList.size()-1;
                        mListMore = JSONObject.parseArray(msg.obj.toString(),TroublesModel.class);
                        for (TroublesModel model:mListMore){
                            mList.add(model);
                        }
                        initListView(mList);
                        mRefresh.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        page+=1;
                    }
                    //
                    if(msg.arg1 == RequestCode.UPLOADFIX){
                        ResultModel model = JSONObject.parseObject(msg.obj.toString(),ResultModel.class);
                        if (model.getResult().equals("1")){
                            if (mList.get(currPos).getStatus().equals("0")||mList.get(currPos).getStatus().equals("1")){//去检修
                                Intent intent = new Intent(mContext,GaojingUploadActivity.class);
                                Bundle b = new Bundle();
                                b.putSerializable("TroublesModel",mList.get(currPos));
                                intent.putExtra("type",2);//检修详情  检修员
                                intent.putExtras(b);
                                startActivityForResult(intent,100);
                            }else{//查看检修详情
                                Intent intent=new Intent(mContext,GaojingDetailsActivity.class);
                                intent.putExtra("TroublesModel",mList.get(currPos));
                                startActivity(intent);
                            }
                        }else{
                            ToastUtil.showBottomLong(mContext,model.getErrorMsg());
                        }
                    }
                    break;
                case HttpUtil.EMPTY:
                    if (msg.arg1 == RequestCode.GETTROUBLES){
                        mRefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
                        MyApplication.setEmptyShowText(mContext,mLv,"暂无数据");
                    }
                    if (msg.arg1 == RequestCode.GETTROUBLESMORE){
                        ToastUtil.showBottomLong(mContext,"暂无更多数据");
                        mRefresh.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                    break;
                case HttpUtil.FAILURE:
                    mRefresh.refreshFinish(PullToRefreshLayout.FAIL);
                    ToastUtil.showBottomLong(mContext,RequestCode.ERRORINFO);
                    break;
                case HttpUtil.LOADING:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化数据
     */
    private void initData(){
        mTitle.setText(R.string.gao_jing_repairer);
        mBack.setOnClickListener(this);
        mRefresh.setOnRefreshListener(this);
        mPullLayout.setLv(mLv);
        if (http == null){
            http = new HttpUtil(handler);
        }
        type = getIntent().getIntExtra("type",1);
        checkType = getIntent().getStringExtra("troubleLevel");
        troubleID = getIntent().getStringExtra("troubleID");
        if (type==1){
            getTroubles(checkType,String.valueOf(page),MyApplication.loginModel.getUserID(),troubleID);
        }else if(type==3){//查询1/2级别 推送入口
            mTitle.setText("最新告警");
            mPullLayout.setSlide(true);//无下拉刷新
            getTroubles(checkType,String.valueOf(page),MyApplication.loginModel.getUserID(),troubleID);
        }else{//图片 点击入口
            mTitle.setText("最新告警");
//            mList = new ArrayList<>();
//            mList.clear();
//            mList = JSONObject.parseArray(getIntent().getStringExtra("TroublesModel"),TroublesModel.class);
//            initListView(mList);
            mPullLayout.setSlide(true);//无下拉刷新
            getTroubles(checkType,String.valueOf(page),MyApplication.loginModel.getUserID(),troubleID);
        }

    }

    /**
     * 初始化数据
     */
    private void initListView(List<TroublesModel> list){
        adapter = new GaoJingRepairerAdapter(mContext,list,this);
        mLv.setAdapter(adapter);
        mLv.setSelection(currPos);
    }

    /**
     * 获取故障（告警）列表 首次 跟刷新
     */
    private void getTroubles(String troubleLevel,String page,String userID,String troubleID){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.GETTROUBLES, WebUrlConfig.getTroubles("","",troubleLevel,"","",page,userID,troubleID));
        } else {
//            mRefresh.refreshFinish(PullToRefreshLayout.FAIL);
            ToastUtil.showBottomShort(this, RequestCode.NOLOGIN);
        }
    }

    /**
     * 获取故障（告警）列表 更多
     */
    private void getTroublesMore(String troubleLevel,String page,String userID,String troubleID){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.GETTROUBLESMORE, WebUrlConfig.getTroubles("","",troubleLevel,"","",page,userID,troubleID));
        } else {
//            mRefresh.loadmoreFinish(PullToRefreshLayout.FAIL);
            ToastUtil.showBottomShort(this, RequestCode.NOLOGIN);
        }
    }

    /**
     * 更改核查状态
     */
    private void uploadFix(String userID,String troubleID){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.UPLOADFIX, WebUrlConfig.updateFix(userID, troubleID));
        } else {
            ToastUtil.showBottomShort(this, RequestCode.NOLOGIN);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            mContext.finish();
        }
    }

    @Override
    public void onLookInfo(int pos) {
        currPos = pos;
        uploadFix(MyApplication.loginModel.getUserID(),mList.get(pos).getTroubleID());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100){
            getTroubles(checkType,"1",MyApplication.loginModel.getUserID(),troubleID);
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getTroubles(checkType,String.valueOf(1),MyApplication.loginModel.getUserID(),troubleID);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getTroublesMore(checkType,String.valueOf(page),MyApplication.loginModel.getUserID(),troubleID);
    }
}
