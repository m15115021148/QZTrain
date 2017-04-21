package com.sitemap.qingzangtrain.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.adapter.GaoJingDetailsAdapter;
import com.sitemap.qingzangtrain.adapter.GaoJingTableListViewAdapter;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.config.RequestCode;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.http.HttpUtil;
import com.sitemap.qingzangtrain.model.TroubleInfoModel;
import com.sitemap.qingzangtrain.model.TroublesModel;
import com.sitemap.qingzangtrain.util.ParserUtil;
import com.sitemap.qingzangtrain.util.SystemFunUtil;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_gaojing_details)
public class GaojingDetailsActivity extends BaseActivity implements View.OnClickListener{
    private GaojingDetailsActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.gaojing_title)
    private TextView gaojing_title;//告警标题
    @ViewInject(R.id.train)
    private TextView train;//车次
    @ViewInject(R.id.train_code)
    private TextView train_code;//车厢
    @ViewInject(R.id.number)
    private TextView number;//编号
    @ViewInject(R.id.type)
    private TextView type;//告警类别
    @ViewInject(R.id.level)
    private TextView level;//告警级别
    @ViewInject(R.id.table_layout)
    private LinearLayout table_layout;//人员列表
    @ViewInject(R.id.include_listView)
    private ListView mTableLv;//表格布局listView
    @ViewInject(R.id.details_list)
    private ListView details_list;//处理列表
    @ViewInject(R.id.img)
    private ImageView img;//级别图片
    private GaoJingDetailsAdapter adapter;//告警详情适配器
    private TroublesModel troublesModel;//传过来的数据
    private RoundProgressDialog progressDialog;//加载条
    private HttpUtil http;//网络请求
    private List<TroubleInfoModel> ltroubleInfo=new ArrayList<TroubleInfoModel>();
    @ViewInject(R.id.last)
    private TextView last;//历时
    private SystemFunUtil videoUtil;// 视频
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
                    if (msg.arg1 == RequestCode.GETTROUBLEINFO){
                        if (msg.obj.toString()!=null&&!msg.obj.toString().equals("")&&!msg.obj.toString().equals("null")){
                            ltroubleInfo.clear();
                            ltroubleInfo= ParserUtil.jsonToList(msg.obj.toString(),TroubleInfoModel.class);
                            if (ltroubleInfo.size()>0){
                                Log.e("result", JSON.toJSONString(ltroubleInfo));
                                adapter=new GaoJingDetailsAdapter(GaojingDetailsActivity.this,ltroubleInfo,videoUtil);
                                details_list.setAdapter(adapter);
                            }
                        }
                    }
                    break;
                case HttpUtil.EMPTY:
                    break;
                case HttpUtil.FAILURE:
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
        if (http == null){
            http = new HttpUtil(handler);
        }
        videoUtil=new SystemFunUtil(mContext);
        MyApplication.verifyStoragePermissions(GaojingDetailsActivity.this);
        troublesModel=(TroublesModel)getIntent().getSerializableExtra("TroublesModel");

        mTitle.setText("告警详情");
        mBack.setOnClickListener(this);

        train.setText(getResources().getString(R.string.trains) + troublesModel.getTrains());
        train_code.setText(getResources().getString(R.string.carriage) + troublesModel.getCarriageNum());
        number.setText(getResources().getString(R.string.carriageNum)+ troublesModel.getCarriageName());
        level.setText(getResources().getString(R.string.trainLevel) + troublesModel.getTroubleLevel());
        gaojing_title.setText(troublesModel.getTroubleName());
        last.setText(Html.fromHtml(getResources().getString(R.string.trainLastTime)+"<font color='#ff0000'>"+troublesModel.getLastTime()+"</font>"));

        if (troublesModel.getTroubleType().equals("1")) {//供电
            type.setText(R.string.type_gongdian);
        } else if (troublesModel.getTroubleType().equals("2")) {//轴温
            type.setText(R.string.type_zhouwen);
        } else if (troublesModel.getTroubleType().equals("3")) {//制动
            type.setText(R.string.type_zhidong);
        }else if (troublesModel.getTroubleType().equals("4")) {//防滑器
            type.setText(R.string.type_fanghuaqi);
        } else if (troublesModel.getTroubleType().equals("5")) {//烟火
            type.setText(R.string.type_yaohuo);
        }else if (troublesModel.getTroubleType().equals("6")) {// 车门
            type.setText(R.string.type_chemen);
        } else if (troublesModel.getTroubleType().equals("7")) {// 制氧机
            type.setText(R.string.type_zhiyangji);
        }else if (troublesModel.getTroubleType().equals("8")) {// 空调
            type.setText(R.string.type_kongtian);
        }else if (troublesModel.getTroubleType().equals("9")) {//车下电源
            type.setText(R.string.type_dianyuan);
        }else{//未知
            type.setText(R.string.type_other);
        }
        if (troublesModel.getTroubleLevel().equals("1")) {//1级
            img.setImageResource(R.drawable.level_1);
            gaojing_title.setTextColor(Color.parseColor("#d9211e"));
        } else if (troublesModel.getTroubleLevel().equals("2")) {//2
            img.setImageResource(R.drawable.level_2);
            gaojing_title.setTextColor(Color.parseColor("#e46520"));
        } else if (troublesModel.getTroubleLevel().equals("3")) {//3
            img.setImageResource(R.drawable.level_3);
            gaojing_title.setTextColor(Color.parseColor("#ef9e3e"));
        } else {//处理完成
            img.setImageResource(R.drawable.level_4);
            gaojing_title.setTextColor(Color.parseColor("#23c975"));
        }
        GaoJingTableListViewAdapter mTableAdapter = new GaoJingTableListViewAdapter(mContext,troublesModel.getPersonsInfoList());
        mTableLv.setAdapter(mTableAdapter);
//
        getTroubleInfo();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            mContext.finish();
        }
    }

    /**
     * 获取故障（告警）处理详情
     */
    private void getTroubleInfo(){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.GETTROUBLEINFO, WebUrlConfig.getTroubleInfo(troublesModel.getTroubleID()));
        } else {
            ToastUtil.showBottomShort(this, RequestCode.NOLOGIN);
        }
    }

}
