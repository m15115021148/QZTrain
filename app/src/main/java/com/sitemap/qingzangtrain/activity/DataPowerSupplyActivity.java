package com.sitemap.qingzangtrain.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.adapter.DataPowerTopRightAdapter;
import com.sitemap.qingzangtrain.adapter.PowerSupplyLeftAdapter;
import com.sitemap.qingzangtrain.adapter.PowerSupplyRightAdapter;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.config.RequestCode;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.http.HttpUtil;
import com.sitemap.qingzangtrain.model.CheMenModel;
import com.sitemap.qingzangtrain.model.CheXiaDianModel;
import com.sitemap.qingzangtrain.model.DataLeftModel;
import com.sitemap.qingzangtrain.model.FangHuaJiModel;
import com.sitemap.qingzangtrain.model.KongTiaoModel;
import com.sitemap.qingzangtrain.model.PowerSupplyModel;
import com.sitemap.qingzangtrain.model.TrainsModel;
import com.sitemap.qingzangtrain.model.TypeModel;
import com.sitemap.qingzangtrain.model.YanHuoModel;
import com.sitemap.qingzangtrain.model.ZhiDongModel;
import com.sitemap.qingzangtrain.model.ZhiYangJiModel;
import com.sitemap.qingzangtrain.model.ZhouWenModel;
import com.sitemap.qingzangtrain.util.ParserUtil;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.HorizontalListView;
import com.sitemap.qingzangtrain.view.MyListView;
import com.sitemap.qingzangtrain.view.MySyncHorizontalScrollView;
import com.sitemap.qingzangtrain.view.PullToRefreshLayout;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供电 页面
 * @author chenmeng created by 2017/2/7
 */
@ContentView(R.layout.activity_power_supply)
public class DataPowerSupplyActivity extends FragmentActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener,PowerSupplyRightAdapter.OnCallBackListener {
    private DataPowerSupplyActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.more)
    private LinearLayout mFilter;//筛选
    @ViewInject(R.id.content)
    private TextView filter;//标题 右侧
    @ViewInject(R.id.power_list_left)
    private MyListView mLvLeft;//listView
    @ViewInject(R.id.power_list_right)
    private MyListView mLvRight;//listView
    @ViewInject(R.id.power_scrollview_title)
    private MySyncHorizontalScrollView mSvTitle;//头部scrollview
    @ViewInject(R.id.power_scrollview_right)
    private MySyncHorizontalScrollView mSvContent;//主题scrollview
    private RoundProgressDialog progressDialog;//加载条
    private HttpUtil http;//网络请求
    private String trainID = "",trainStatus= "",startTime= "",endTime= "";//车次id 列车状态  开始时间 结束时间
    private List<TypeModel> mTopList = new ArrayList<>();//顶部头部数据
    private List<TypeModel> mCurrTopList = new ArrayList<>();//当前选择的头部数据
    private List<TypeModel> mAllTopList = new ArrayList<>();//所以数据 头部
//    @ViewInject(R.id.horizontalListView)
//    private HorizontalListView mTopLv;//头部 右侧  listview
//    private DataPowerTopRightAdapter mTopAdapter;//头部 适配器
    private int troubleType = 0;//0 轴温 1供电 2制动 3防滑剂 4://车门 5://烟火 6://制氧机 7://空调
    private String netData = "";//网络数据  json格式
    private PowerSupplyRightAdapter mAdapterRight;//右侧适配器
    private PowerSupplyLeftAdapter mAdapterLeft;//左侧适配器
    private Map<Integer,List<TypeModel>> mMap = new HashMap<>();//数据
    private Map<Integer,List<TypeModel>> mMapMore = new HashMap<>();//数据
    @ViewInject(R.id.refresh_layout)
    private PullToRefreshLayout mRefresh;//刷新布局
    @ViewInject(R.id.data_layout)
    private LinearLayout mShowLayout;//刷新布局
    private int page = 1;//当前页
    private List<DataLeftModel> mLeftList = new ArrayList<>();//左侧数据
    private List<DataLeftModel> mLeftListMore = new ArrayList<>();//左侧数据
    @ViewInject(R.id.isShow)
    private TextView isShow;// 无数据 显示的内容
    private int currPos = 0;//当前位置
    @ViewInject(R.id.layout)
    private LinearLayout mTopLayout;//头部布局
    private String carriageNum = "";//车厢编号
    private String runRanges = "";//运行区间
    private String stopRanges = "";//停留区间
    private String dataType = "1";//曲线类型
    private List<TrainsModel> mTrainsList = new ArrayList<>();//车次id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 反射注解机制初始化
        x.view().inject(this);
        mContext = this;
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("result","onResume....");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("result","onPause....");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("result","onDestroy....");
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        Log.e("result","onConfigurationChanged....");
    }

    /**
     *  ondestroy 在上个页面onresume后在执行 则返回的时候 先释放资源
     */
    @Override
    public void onBackPressed() {
        mMapMore.clear();
        mMap.clear();
        super.onBackPressed();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();//
//            }
            switch (msg.what){
                case HttpUtil.SUCCESS:
                    //轴温
                    if (msg.arg1 == RequestCode.DATALOAD){
                        mShowLayout.setVisibility(View.VISIBLE);
                        isShow.setVisibility(View.GONE);
                        currPos = 0;
                        page = 1;
                        netData = msg.obj.toString();
                        mLeftList.clear();
                        mLeftList = ParserUtil.jsonToList(netData,DataLeftModel.class);
                        if (troubleType==0){//轴温
                            mMap = getZhouWenData(ParserUtil.jsonToList(netData,ZhouWenModel.class));
                        }else if(troubleType==1){//1供电
                            mMap = getGongDianData(ParserUtil.jsonToList(netData,PowerSupplyModel.class));
                        }else if(troubleType==2){//2制动
                            mMap = getZhiDongData(ParserUtil.jsonToList(netData,ZhiDongModel.class));
                        }else if(troubleType==3){//3防滑剂
                            mMap = getFangHuaJiData(ParserUtil.jsonToList(netData,FangHuaJiModel.class));
                        }else if(troubleType==4){//车门
                            mMap = getCheMenData(ParserUtil.jsonToList(netData,CheMenModel.class));
                        }else if(troubleType==5){//烟火
                            mMap = getYanHuoData(ParserUtil.jsonToList(netData,YanHuoModel.class));
                        }else if(troubleType==6){//制氧机
                            mMap = getZhiYangJiData(ParserUtil.jsonToList(netData,ZhiYangJiModel.class));
                        }else if(troubleType==7){//空调
                            mMap = getKongTiaoData(ParserUtil.jsonToList(netData,KongTiaoModel.class));
                        } else if (troubleType==8) {//车下电源
                            mMap = getCheXiaDianYuanData(ParserUtil.jsonToList(netData,CheXiaDianModel.class));
                        }
                        initLeftListView(mLeftList);
                        initRightListView(mMap,mCurrTopList);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();// 关闭进度条
                        }
                        mRefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
                        page+=1;
                    }
                    //更多
                    if(msg.arg1 == RequestCode.DATALOADMORE){
                        netData = msg.obj.toString();
                        mLeftListMore.clear();
                        mLeftListMore = ParserUtil.jsonToList(netData,DataLeftModel.class);
                        if (troubleType==0){//轴温
                            mMapMore = getZhouWenData(ParserUtil.jsonToList(netData,ZhouWenModel.class));
                        }else if(troubleType==1){//1供电
                            mMapMore = getGongDianData(ParserUtil.jsonToList(netData,PowerSupplyModel.class));
                        }else if(troubleType==2){//2制动
                            mMapMore = getZhiDongData(ParserUtil.jsonToList(netData,ZhiDongModel.class));
                        }else if(troubleType==3){//3防滑剂
                            mMapMore = getFangHuaJiData(ParserUtil.jsonToList(netData,FangHuaJiModel.class));
                        }else if(troubleType==4){//车门
                            mMapMore = getCheMenData(ParserUtil.jsonToList(netData,CheMenModel.class));
                        }else if(troubleType==5){//烟火
                            mMapMore = getYanHuoData(ParserUtil.jsonToList(netData,YanHuoModel.class));
                        }else if(troubleType==6){//制氧机
                            mMapMore = getZhiYangJiData(ParserUtil.jsonToList(netData,ZhiYangJiModel.class));
                        }else if(troubleType==7){//空调
                            mMapMore = getKongTiaoData(ParserUtil.jsonToList(netData,KongTiaoModel.class));
                        } else if (troubleType==8) {//车下电源
                            mMapMore = getCheXiaDianYuanData(ParserUtil.jsonToList(netData,CheXiaDianModel.class));
                        }

                        for (DataLeftModel model:mLeftListMore){
                            mLeftList.add(model);
                        }
//                        //第二种
//                        Set<Map.Entry<Integer,List<TypeModel>>> entryset = mMapMore.entrySet();
//                        Iterator iter = entryset.iterator();
//                        while(iter.hasNext()){
//                            Map.Entry<Integer,List<TypeModel>> entry = (Map.Entry<Integer,List<TypeModel>>)iter.next();
//                            mMap.put(entry.getKey(),entry.getValue());
//                        }
                        //第三种
                        int size = mMap.size();
                        currPos = size - 1;
                        for(Map.Entry<Integer,List<TypeModel>> entry : mMapMore.entrySet()){
                            mMap.put(size+entry.getKey(),entry.getValue());
                        }
                        initLeftListView(mLeftList);
                        initRightListView(mMap,mCurrTopList);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();// 关闭进度条
                        }
                        mRefresh.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        page+=1;

                    }
                    break;
                case HttpUtil.EMPTY:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();// 关闭进度条
                    }
                    if (msg.arg1 == RequestCode.DATALOAD){
                        mShowLayout.setVisibility(View.GONE);
                        isShow.setVisibility(View.VISIBLE);
                        mRefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                    if (msg.arg1 == RequestCode.DATALOADMORE){
                        ToastUtil.showBottomLong(mContext,"暂无更多数据");
                        mRefresh.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                    break;
                case HttpUtil.FAILURE:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();// 关闭进度条
                    }
                    mRefresh.refreshFinish(PullToRefreshLayout.FAIL);
                    ToastUtil.showBottomLong(mContext, RequestCode.ERRORINFO);
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
        filter.setText("筛选");
        mFilter.setVisibility(View.VISIBLE);
        mBack.setOnClickListener(this);
        mFilter.setOnClickListener(this);
        mSvContent.setmSyncView(mSvTitle);
        mSvTitle.setmSyncView(mSvContent);
        if (http == null){
            http = new HttpUtil(handler);
        }
        troubleType = getIntent().getIntExtra("troubleType",0);
        trainID = getIntent().getStringExtra("trainID");
        trainStatus = getIntent().getStringExtra("trainStatus");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        carriageNum = getIntent().getStringExtra("carriageNum");
        runRanges = getIntent().getStringExtra("moveRange");
        stopRanges = getIntent().getStringExtra("stopRange");
        mTrainsList = ParserUtil.jsonToList(getIntent().getStringExtra("trainName"),TrainsModel.class);
        mRefresh.setOnRefreshListener(this);
        if (troubleType==0){//轴温
            mTitle.setText("轴温");
            dataType = "1";
            mTopList = getTopRightData(RequestCode.zhouWen);
            mAllTopList = getTopRightData(RequestCode.zhouWen);
        }else if(troubleType==1){//1供电
            mTitle.setText("供电");
            mTopList = getTopRightData(RequestCode.gongDian);
            mAllTopList = getTopRightData(RequestCode.gongDian);
        }else if(troubleType==2){//2制动
            mTitle.setText("制动");
            dataType = "3";
            mTopList = getTopRightData(RequestCode.zhiDong);
            mAllTopList = getTopRightData(RequestCode.zhiDong);
        }else if(troubleType==3){//3防滑剂
            mTitle.setText("防滑器");
            mTopList = getTopRightData(RequestCode.fangHuaJi);
            mAllTopList = getTopRightData(RequestCode.fangHuaJi);
        }else if(troubleType==4){//车门
            mTitle.setText("车门");
            mTopList = getTopRightData(RequestCode.cheMen);
            mAllTopList = getTopRightData(RequestCode.cheMen);
        }else if(troubleType==5){//烟火
            mTitle.setText("烟火");
            mTopList = getTopRightData(RequestCode.yanHuo);
            mAllTopList = getTopRightData(RequestCode.yanHuo);
        }else if(troubleType==6){//制氧机
            mTitle.setText("制氧机");
            mTopList = getTopRightData(RequestCode.zhiYangJi);
            mAllTopList = getTopRightData(RequestCode.zhiYangJi);
        }else if(troubleType==7){//空调
            mTitle.setText("空调");
            dataType = "8";
            mTopList = getTopRightData(RequestCode.kongTiao);
            mAllTopList = getTopRightData(RequestCode.kongTiao);
        }else if (troubleType==8) {//车下电源
            mTitle.setText("车下电源");
            mTopList = getTopRightData(RequestCode.cheXiaDianYuan);
            mAllTopList = getTopRightData(RequestCode.cheXiaDianYuan);
        }
        mCurrTopList = mTopList;
        initTopData(mCurrTopList);
        getNetData(troubleType,trainID,trainStatus,startTime,endTime,String.valueOf(page),carriageNum,runRanges,stopRanges);

    }

    /**
     * 初始化头部数据
     */
    private void initTopData(List<TypeModel> list) {
//        mTopAdapter = new DataPowerTopRightAdapter(mContext,list);
//        mTopLv.setAdapter(mTopAdapter);
        mTopLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (TypeModel model:list){
            View tableView = getLayoutInflater().inflate(R.layout.data_power_recycle_item,null);
            TextView txt = (TextView) tableView.findViewById(R.id.txt);
            if(model.getTypeName().length()>4){
                txt.setText(setChars(model.getTypeName()));
            }else{
                txt.setText(model.getTypeName());
            }
            mTopLayout.addView(tableView,params);
        }

    }

    /**
     * 设置字符串换行
     * @return
     */
    private String setChars(String txt){
        StringBuffer sb = new StringBuffer(txt);
//        sb.insert(txt.length()-2,"\n");
        sb.insert(4,"\n");
        return sb.toString();
    }

    /**
     * 获取网络数据 首次 跟刷新
     */
    private void getNetData(int troubleType ,String trainID,String trainStatus,String startTime,String endTime,String page,String carriageNum,String runRanges,String stopRanges){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            if (troubleType==0){//轴温
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getZhouwen(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==1){//1供电
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getGongdian(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==2){//2制动
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getZhidong(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==3){//3防滑剂
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getFanghuaqi(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==4){//车门
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getChemen(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==5){//烟火
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getYanhuo(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==6){//制氧机
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getZhiyangji(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==7){//空调
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getKongtiao(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            } else if (troubleType==8) {//车下电源
                http.sendGet(RequestCode.DATALOAD,WebUrlConfig.getCheXiaDianYuan(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }
        } else {
//            mRefresh.refreshFinish(PullToRefreshLayout.FAIL);
            ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
        }
    }

    /**
     * 获取网络数据 加载更多
     */
    private void getNetDataMore(int troubleType ,String trainID,String trainStatus,String startTime,String endTime,String page,String carriageNum,String runRanges,String stopRanges){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            if (troubleType==0){//轴温
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getZhouwen(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==1){//1供电
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getGongdian(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==2){//2制动
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getZhidong(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==3){//3防滑剂
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getFanghuaqi(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==4){//车门
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getChemen(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==5){//烟火
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getYanhuo(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==6){//制氧机
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getZhiyangji(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }else if(troubleType==7){//空调
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getKongtiao(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            } else if (troubleType==8) {//车下电源
                http.sendGet(RequestCode.DATALOADMORE,WebUrlConfig.getCheXiaDianYuan(trainID, trainStatus, startTime, endTime, page,carriageNum,runRanges,stopRanges));
            }
        } else {
//            mRefresh.loadmoreFinish(PullToRefreshLayout.FAIL);
            ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
        }
    }

    /**
     * 初始化数据 左侧
     */
    private void initLeftListView(List<DataLeftModel> list){
        mAdapterLeft = new PowerSupplyLeftAdapter(mContext,list);
        mLvLeft.setAdapter(mAdapterLeft);
        mLvLeft.setSelection(currPos);
    }

    /**
     * 初始化数据 右侧
     */
    private void initRightListView(Map<Integer,List<TypeModel>> map,List<TypeModel> topList){
        mAdapterRight = new PowerSupplyRightAdapter(mContext,map,topList,this);
        mLvRight.setAdapter(mAdapterRight);
        mLvRight.setSelection(currPos);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            onBackPressed();
            finish();
        }
        if (v == mFilter){
            Intent intent = new Intent(mContext,FilterActivity.class);
            intent.putExtra("filterData", JSON.toJSONString(mAllTopList));
            startActivityForResult(intent,100);
        }
    }

    /**
     * 得到头部数据
     * @param values
     * @return
     */
    private List<TypeModel> getTopRightData(String[] values){
        List<TypeModel> list = new ArrayList<>();
        list.clear();
        for (int i=0;i<values.length;i++){
            TypeModel model = new TypeModel();
            model.setTypeName(values[i]);
            model.setIsSelect(1);
            model.setPos(i);
            list.add(model);
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100){
            if(data!=null){
                Log.e("result","得到数据:"+data.getStringExtra("values"));
                String txt = data.getStringExtra("values");
                String all = data.getStringExtra("all");
                mAllTopList.clear();
                mAllTopList = ParserUtil.jsonToList(all,TypeModel.class);
                mCurrTopList.clear();
                mCurrTopList = ParserUtil.jsonToList(txt,TypeModel.class);
                initTopData(mCurrTopList);
                initLeftListView(mLeftList);
                initRightListView(mMap,mCurrTopList);
                mAdapterLeft.notifyDataSetChanged();
                mAdapterRight.notifyDataSetChanged();
            }
        }
    }

    /**
     *  供电 数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getGongDianData(List<PowerSupplyModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getLouDianDL());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getMuXianDY());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getZhenLiuDY());
            model3.setPos(2);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getGongDianZS());
            model5.setPos(3);
            TypeModel model7 = new TypeModel();
            model7.setTypeName(list.get(i).getGongDian());
            model7.setPos(4);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getGongZuoDL());
            model8.setPos(5);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getGongZuoDY());
            model9.setPos(6);
            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model5);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     *  轴温  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getZhouWenData(List<ZhouWenModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getTem1());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getTem2());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getTem3());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();
            model4.setTypeName(list.get(i).getTem4());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getTem5());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getTem6());
            model6.setPos(5);
            TypeModel model7= new TypeModel();
            model7.setTypeName(list.get(i).getTem7());
            model7.setPos(6);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getTem8());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getTemp());
            model9.setPos(8);
            TypeModel model10 = new TypeModel();
            model10.setTypeName(list.get(i).getAlarm());
            model10.setPos(9);
            TypeModel model11 = new TypeModel();
            model11.setTypeName(list.get(i).getSpeed());
            model11.setPos(10);
            TypeModel model12 = new TypeModel();
            model12.setTypeName(list.get(i).getMoveRange());
            model12.setPos(11);
            TypeModel model13 = new TypeModel();
            model13.setTypeName("查看");
            model13.setPos(12);

            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            mRightList.add(model10);
            mRightList.add(model11);
            mRightList.add(model12);
            mRightList.add(model13);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     *  制动数据  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getZhiDongData(List<ZhiDongModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getZhiDongGYL());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getLieCheGuanYL());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getGongZuoFenGYL());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();
            model4.setTypeName(list.get(i).getZhiDongGZDYL());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getGpsSpeed());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getPreventSpeed());
            model6.setPos(5);
            TypeModel model7 = new TypeModel();
            model7.setTypeName(list.get(i).getLocation());
            model7.setPos(6);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getZhidongState());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName("查看");
            model9.setPos(8);

            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     *  防滑剂  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getFangHuaJiData(List<FangHuaJiModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getCheSu());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getSuDu1());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getSuDu2());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();
            model4.setTypeName(list.get(i).getSuDu3());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getSuDu4());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getPaiFen1());
            model6.setPos(5);
            TypeModel model7= new TypeModel();
            model7.setTypeName(list.get(i).getPaiFen2());
            model7.setPos(6);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getPaiFen3());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getPaiFen4());
            model9.setPos(8);
            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     * 车门  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getCheMenData(List<CheMenModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getCheMen1YGL());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getCheMen1ZGL());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getCheMen1YKG());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();
            model4.setTypeName(list.get(i).getCheMen1ZKG());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getCheMen2YGL());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getCheMen2ZGL());
            model6.setPos(5);
            TypeModel model7= new TypeModel();
            model7.setTypeName(list.get(i).getCheMen2YKG());
            model7.setPos(6);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getCheMen2ZKG());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getCheMen1GZM());
            model9.setPos(8);
            TypeModel model10 = new TypeModel();
            model10.setTypeName(list.get(i).getCheMen2GZM());
            model10.setPos(9);
            TypeModel model11 = new TypeModel();
            model11.setTypeName(list.get(i).getPreventSpeed());
            model11.setPos(10);
            TypeModel model12 = new TypeModel();
            model12.setTypeName(list.get(i).getLocation());
            model12.setPos(11);
            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            mRightList.add(model10);
            mRightList.add(model11);
            mRightList.add(model12);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     * 烟火  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getYanHuoData(List<YanHuoModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getNum1());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getNum2());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getNum3());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();
            model4.setTypeName(list.get(i).getNum4());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getNum5());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getNum6());
            model6.setPos(5);
            TypeModel model7= new TypeModel();
            model7.setTypeName(list.get(i).getNum7());
            model7.setPos(6);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getNum8());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getNum9());
            model9.setPos(8);
            TypeModel model10 = new TypeModel();
            model10.setTypeName(list.get(i).getNum10());
            model10.setPos(9);
            TypeModel model11 = new TypeModel();
            model11.setTypeName(list.get(i).getNum11());
            model11.setPos(10);
            TypeModel model12 = new TypeModel();
            model12.setTypeName(list.get(i).getNum12());
            model12.setPos(11);
            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            mRightList.add(model10);
            mRightList.add(model11);
            mRightList.add(model12);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     * 制氧机  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getZhiYangJiData(List<ZhiYangJiModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getZhiYangJiXTGZ());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getMoZhiYangXTGZ());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getKongYaJi1GZ());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();
            model4.setTypeName(list.get(i).getKongYaJi2GZ());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getKeShiYangNDCB());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getZhiYangShiNDCB());
            model6.setPos(5);
            TypeModel model7= new TypeModel();
            model7.setTypeName(list.get(i).getFenMenTiaoJieGZ());
            model7.setPos(6);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getFuYangChuKouND());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getKeSheYangND());
            model9.setPos(8);
            TypeModel model10 = new TypeModel();
            model10.setTypeName(list.get(i).getKongYaJiYL());
            model10.setPos(9);
            TypeModel model11 = new TypeModel();
            model11.setTypeName(list.get(i).getZhiYangJiGuZhang());
            model11.setPos(10);
            TypeModel model12 = new TypeModel();
            model12.setTypeName(list.get(i).getAltitude());
            model12.setPos(11);
            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            mRightList.add(model10);
            mRightList.add(model11);
            mRightList.add(model12);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     * 空调  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getKongTiaoData(List<KongTiaoModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getCheXiangWD());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();//换温
            model2.setTypeName(list.get(i).getTemp());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();
            model3.setTypeName(list.get(i).getFen());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();//制暖？
            model4.setTypeName(list.get(i).getQuanNuan());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();//全冷
            model5.setTypeName(list.get(i).getQuanLen());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getZhuangTai());
            model6.setPos(5);
            TypeModel model7 = new TypeModel();
            model7.setTypeName(list.get(i).getFengMenKaiDu());
            model7.setPos(6);
            TypeModel model8= new TypeModel();
            model8.setTypeName(list.get(i).getZhiNuan11GZ());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getZhiNuan12GZ());
            model9.setPos(8);
            TypeModel model10 = new TypeModel();
            model10.setTypeName(list.get(i).getZhiNuan21GZ());
            model10.setPos(9);
            TypeModel model11 = new TypeModel();
            model11.setTypeName(list.get(i).getZhiNuan22GZ());
            model11.setPos(10);
            TypeModel model12 = new TypeModel();
            model12.setTypeName(list.get(i).getZhiLen11GZ());
            model12.setPos(11);
            TypeModel model13 = new TypeModel();
            model13.setTypeName(list.get(i).getZhiLen12GZ());
            model13.setPos(12);
            TypeModel model14 = new TypeModel();
            model14.setTypeName(list.get(i).getZhiLen21GZ());
            model14.setPos(13);
            TypeModel model15 = new TypeModel();
            model15.setTypeName(list.get(i).getZhiLen22GZ());
            model15.setPos(14);
            TypeModel model16 = new TypeModel();
            model16.setTypeName(list.get(i).getLenNingFJ1GZ());
            model16.setPos(15);
            TypeModel model17 = new TypeModel();
            model17.setTypeName(list.get(i).getLenNingFJ2GZ());
            model17.setPos(16);
            TypeModel model18 = new TypeModel();
            model18.setTypeName("查看");
            model18.setPos(17);

            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            mRightList.add(model10);
            mRightList.add(model11);
            mRightList.add(model12);
            mRightList.add(model13);
            mRightList.add(model14);
            mRightList.add(model15);
            mRightList.add(model16);
            mRightList.add(model17);
            mRightList.add(model18);
            map.put(i,mRightList);
        }
        return map;
    }

    /**
     * 车下电源  数据
     * @param list
     */
    private Map<Integer,List<TypeModel>> getCheXiaDianYuanData(List<CheXiaDianModel> list){
        Map<Integer,List<TypeModel>> map = new HashMap<>();
        for (int i=0;i<list.size();i++){
            List<TypeModel> mRightList = new ArrayList<>();
            TypeModel model1 = new TypeModel();
            model1.setTypeName(list.get(i).getChongDianJiDL());
            model1.setPos(0);
            TypeModel model2 = new TypeModel();
            model2.setTypeName(list.get(i).getDianLiu());
            model2.setPos(1);
            TypeModel model3 = new TypeModel();//
            model3.setTypeName(list.get(i).getChongDianJiGZM());
            model3.setPos(2);
            TypeModel model4 = new TypeModel();//
            model4.setTypeName(list.get(i).getNiBianQi1SCDY());
            model4.setPos(3);
            TypeModel model5 = new TypeModel();
            model5.setTypeName(list.get(i).getNiBianQi1DYPL());
            model5.setPos(4);
            TypeModel model6 = new TypeModel();
            model6.setTypeName(list.get(i).getNiBianQi1GZM());
            model6.setPos(5);
            TypeModel model7= new TypeModel();
            model7.setTypeName(list.get(i).getNiBianQi2SCDY());
            model7.setPos(6);
            TypeModel model8 = new TypeModel();
            model8.setTypeName(list.get(i).getNiBianQi2DYPL());
            model8.setPos(7);
            TypeModel model9 = new TypeModel();
            model9.setTypeName(list.get(i).getNiBianQi2GZM());
            model9.setPos(8);
            mRightList.add(model1);
            mRightList.add(model2);
            mRightList.add(model3);
            mRightList.add(model4);
            mRightList.add(model5);
            mRightList.add(model6);
            mRightList.add(model7);
            mRightList.add(model8);
            mRightList.add(model9);
            map.put(i,mRightList);
        }
        return map;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getNetData(troubleType,trainID,trainStatus,startTime,endTime,String.valueOf(1),carriageNum,runRanges,stopRanges);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getNetDataMore(troubleType,trainID,trainStatus,startTime,endTime,String.valueOf(page),carriageNum,runRanges,stopRanges);
    }

    /**
     * 曲线点击事件
     * @param model
     */
    @Override
    public void onClickCharts(TypeModel model,int pos) {
        Log.e("result","pos:"+mLeftList.get(pos).getCarriageName());
        String trainName = "";//车次
        String id = mLeftList.get(pos).getTrainID();
        for (TrainsModel m : mTrainsList){
            if (id.equals(m.getTrainID())){
                trainName = m.getTrainName();
            }
        }
        //轴温地图
        if ("查看".equals(model.getTypeName())){
            Intent intent = new Intent(mContext,DataStatisticsActivity.class);
            intent.putExtra("dataType",dataType);
            intent.putExtra("type",trainStatus);
            intent.putExtra("chexiang",mLeftList.get(pos).getCarriageName());
            intent.putExtra("isValid",stopRanges);
            intent.putExtra("quJian",runRanges);
            intent.putExtra("s_time",startTime);
            intent.putExtra("e_time",endTime);
            intent.putExtra("name",trainName);//车次名称
            startActivity(intent);
        }

    }
}
