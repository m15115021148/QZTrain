package com.sitemap.qingzangtrain.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.activity.MainBottomActivity;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.config.RequestCode;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.http.HttpUtil;
import com.sitemap.qingzangtrain.model.StatisticsInfoModel;
import com.sitemap.qingzangtrain.model.TrainModel;
import com.sitemap.qingzangtrain.model.WarmNumsModel;
import com.sitemap.qingzangtrain.util.AnimaUtil;
import com.sitemap.qingzangtrain.util.ParserUtil;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图 页面
 * Created by chenmeng on 2017/3/21.
 */

public class MapFragment extends Fragment implements View.OnClickListener {
    private View view;// 视图
    private Context mContext;//activity
    private RoundProgressDialog progressDialog;//加载条
    private HttpUtil http;//网络请求
    private LinearLayout mMenuBottom;//底部布局
    private TextView mBottomTitle;//底部内容
    private boolean isShow = true;//是否显示底部菜单

    private MapView mMapView;
    private BaiduMap mBaiduMap;//百度地图对象
    private BaiduMap.OnMarkerClickListener markClick;// 地图标注点击事件
    private List<LatLng> points = new ArrayList<LatLng>();//线的点的集合
    private List<TrainModel> ltrain = new ArrayList<TrainModel>();//火车列表
    private StatisticsInfoModel statisticsInfoModel = null;//统计数据
    private WarmNumsModel warmModel;//获取单车故障统计数据（首页底部）  实体类

    private LatLng latLng;//中心点
    private ImageView mMapStatus;//地图切换

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map_layout, null);
        }
        mContext = inflater.getContext();
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mMenuBottom = (LinearLayout) view.findViewById(R.id.menu_bottom);
        mBottomTitle = (TextView) view.findViewById(R.id.menu_txt);
        mMapStatus = (ImageView) view.findViewById(R.id.mapStatus);

        initData();
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HttpUtil.SUCCESS:
                    if (msg.arg1 == RequestCode.GETTRAININFO) {
                        ltrain.clear();
                        ltrain = ParserUtil.jsonToList(msg.obj.toString(), TrainModel.class);
                        Log.i("TAG", "ltrain.size()" + ltrain.size());
                        if (ltrain.size() > 0) {
                            mBaiduMap.clear();
                            points.clear();

                            for (int i = 0; i < ltrain.size(); i++) {
                                if (ltrain.get(i).getLat().equals("0") || ltrain.get(i).getLng().equals("0") || Double.parseDouble(ltrain.get(i).getLat()) < 0 || Double.parseDouble(ltrain.get(i).getLng()) < 0) {
                                    return;
                                }
                                points.add(new LatLng(Double.parseDouble(ltrain.get(i).getLat()), Double.parseDouble(ltrain.get(i).getLng())));
//                                    setPoint((new LatLng(Double.parseDouble(ltrain.get(i).getLat()), Double.parseDouble(ltrain.get(i).getLng()))),"当前时间："+ltrain.get(i).getTime() , "车次："+ltrain.get(i).getTrainNumber()+"    速度："+ltrain.get(i).getSpeed()+"km/h",1,ltrain.get(i).getImg(),i);
//                                       mBaiduMap.removeMarkerClickListener(markClick);
                                Marker marker = null;
                                // 构建用户绘制点Option对象
                                // OverlayOptions polygonOption = new DotOptions()
                                // .color(Integer.valueOf(Color.RED))
                                // .radius(14).center(points.get(i)).zIndex(i);
                                // 构建Marker图标
//                                        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                                .fromResource(R.drawable.tingche);
                                // 构建Marker图标
                                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromAssetWithDpi(ltrain.get(i).getImg());
                                Log.i("TAG", "points:" + points.size());
                                // 构建MarkerOption，用于在地图上添加Marker
                                OverlayOptions option = new MarkerOptions()
                                        .position(points.get(i)).icon(bitmap)
                                        .zIndex(2);
                                marker = (Marker) mBaiduMap.addOverlay(option);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("info", String.valueOf(i));
                                marker.setExtraInfo(bundle);
                                markClick = new BaiduMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker arg0) {
                                        if (arg0.getZIndex() == 3) {
                                            return false;
                                        }
                                        View contentView = getActivity().getLayoutInflater().inflate(R.layout.mapmark_layout,
                                                null);
                                        TextView location_address = (TextView) contentView
                                                .findViewById(R.id.location_address);
                                        TextView location_time = (TextView) contentView
                                                .findViewById(R.id.location_time);
                                        TextView xingbie = (TextView) contentView
                                                .findViewById(R.id.xingbie);
                                        TextView speed = (TextView) contentView
                                                .findViewById(R.id.speed);
                                        TextView haiba = (TextView) contentView
                                                .findViewById(R.id.haiba);
                                        TextView statu = (TextView) contentView
                                                .findViewById(R.id.statu);
                                        location_address.setText("车        次：" + ltrain.get(Integer.parseInt((String) arg0
                                                .getExtraInfo().get("info"))).getTrainNumber());
                                        location_address.setGravity(Gravity.CENTER_VERTICAL);
                                        location_time.setText("上报时间：" + ltrain.get(Integer.parseInt((String) arg0
                                                .getExtraInfo().get("info"))).getTime());
                                        location_time.setGravity(Gravity.CENTER_VERTICAL);
                                        if (ltrain.get(Integer.parseInt((String) arg0
                                                .getExtraInfo().get("info"))).getSx().equals("s")) {
                                            xingbie.setText("行        别：上行");
                                        } else if (ltrain.get(Integer.parseInt((String) arg0
                                                .getExtraInfo().get("info"))).getSx().equals("x")) {
                                            xingbie.setText("行        别：下行");
                                        } else {
                                            xingbie.setText("行        别：");
                                        }
                                        xingbie.setGravity(Gravity.CENTER_VERTICAL);
                                        speed.setText("速        度：" + ltrain.get(
                                                Integer.parseInt((String) arg0.getExtraInfo().get("info"))).getSpeed() + "km/h");
                                        speed.setGravity(Gravity.CENTER_VERTICAL);
                                        haiba.setText("海        拔：" + ltrain.get(Integer.parseInt((String) arg0
                                                .getExtraInfo().get("info"))).getAltitude() + "m");
                                        haiba.setGravity(Gravity.CENTER_VERTICAL);
                                        switch (Integer.parseInt(ltrain.get(Integer.parseInt((String) arg0
                                                .getExtraInfo().get("info"))).getStatus())) {
                                            case 0:
                                                statu.setText("运行状态：正常");
                                                break;
                                            case 1:
                                                statu.setText("运行状态：告警");
                                                break;
                                            case 2:
                                                statu.setText("运行状态：故障");
                                                break;
                                            case 3:
                                                statu.setText("运行状态：离线");
                                                break;
                                        }

                                        statu.setGravity(Gravity.CENTER_VERTICAL);
                                        contentView
                                                .setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        mBaiduMap.hideInfoWindow();
                                                    }
                                                });
                                        // 构建对话框用于显示
                                        // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                                        InfoWindow mInfoWindow = new InfoWindow(
                                                contentView, points.get(Integer
                                                .parseInt((String) arg0
                                                        .getExtraInfo().get(
                                                                "info"))), -20);
//                                               Log.i("TAG","zuobiao:"+)
                                        // 显示InfoWindow
                                        mBaiduMap.showInfoWindow(mInfoWindow);
                                        return false;
                                    }
                                };
//						               addArrow(points,20,60);
                                mBaiduMap.setOnMarkerClickListener(markClick);
                                // 定义地图状态

                                updateStatus(latLng, 5);
//                                       setPoint((new LatLng(Double.parseDouble(ltrackModel.get(i).getLat()), Double.parseDouble(ltrackModel.get(i).getLng()))), ltrackModel.get(i).getTime(), "车次：" + checi, 1);
                            }
                        }
                    }
                    //获取统计信息（首页）
                    if (msg.arg1 == RequestCode.GETSTATICTICSINFO) {
                        statisticsInfoModel = JSONObject.parseObject(msg.obj.toString(), StatisticsInfoModel.class);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();// 关闭进度条
                            if (isShow) {
                                mMenuBottom.callOnClick();
                                isShow = false;
                            }
                        }
                    }

                    //获取单车故障统计数据（首页底部）
                    if (msg.arg1 == RequestCode.GETWARMNUMS) {
                        warmModel = JSONObject.parseObject(msg.obj.toString(), WarmNumsModel.class);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();// 关闭进度条
                            if (isShow) {
                                mMenuBottom.callOnClick();
                                isShow = false;
                            }
                        }
                    }
                    break;
                case HttpUtil.EMPTY:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();// 关闭进度条
                    }
                    //获取统计信息（首页）
                    if (msg.arg1 == RequestCode.GETSTATICTICSINFO) {
                        statisticsInfoModel = null;
                    }

                    //获取单车故障统计数据（首页底部）
                    if (msg.arg1 == RequestCode.GETWARMNUMS) {
                        warmModel = null;
                    }
                    break;
                case HttpUtil.FAILURE:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();// 关闭进度条
                    }
                    ToastUtil.showBottomLong(mContext, RequestCode.ERRORINFO);
                    break;
                case HttpUtil.LOADING:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mMenuBottom.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (http == null) {
            http = new HttpUtil(handler);
        }
        if (MyApplication.loginModel.getType().equals("1") || MyApplication.loginModel.getType().equals("4")) {
            mBottomTitle.setText("运行状态");
        } else {
            mBottomTitle.setText("列车状态");
        }
        mMenuBottom.setOnClickListener(this);
        mMapStatus.setOnClickListener(this);

        // 隐藏缩放控件
        hidezoomView();
        mBaiduMap = mMapView.getMap();
        latLng = new LatLng(30.142316, 104.295398);
        updateStatus(latLng, 5);

        mMapStatus.setSelected(false);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    /**
     * 获取网络数据
     */
    public void getNetData() {
        isShow = true;
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(mContext);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            getTrainInfo(MyApplication.loginModel.getUserID());
            if (MyApplication.loginModel.getType().equals("2") || MyApplication.loginModel.getType().equals("3")) {
                getWarmNum();
            } else {
                getStatisticsInfo();
            }
        } else {
            ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
        }
    }

    /**
     * 查询列车运行状态
     */
    private void getTrainInfo(String userID) {
        http.sendGet(RequestCode.GETTRAININFO, WebUrlConfig.getTrainInfo(userID));
    }

    /**
     * 获取统计信息（首页）
     */
    private void getStatisticsInfo() {
        http.sendGet(RequestCode.GETSTATICTICSINFO, WebUrlConfig.getStatisticsInfo());
    }

    /**
     * 获取单车故障统计数据（首页底部）
     */
    private void getWarmNum() {
        http.sendGet(RequestCode.GETWARMNUMS, WebUrlConfig.getWarmNums(MyApplication.loginModel.getUserID()));
    }

    @Override
    public void onClick(View v) {
        if (v == mMenuBottom) {
            if (statisticsInfoModel != null || warmModel != null) {
                AnimaUtil.setAlphaAnimation(mMenuBottom, 500);
                mMenuBottom.setVisibility(View.GONE);
                Intent intent = new Intent(mContext, MainBottomActivity.class);
                intent.putExtra("StatisticsInfoModel", statisticsInfoModel);
                intent.putExtra("WarmNumsModel", warmModel);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.main_entry, R.anim.main_exit);
            } else {
                ToastUtil.showBottomLong(mContext, "暂无数据");
            }
        }
        if (v == mMapStatus){
            if(mMapStatus.isSelected()){
                mMapStatus.setSelected(false);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }else{
                mMapStatus.setSelected(true);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }
        }
    }

    /**
     * 隐藏图标
     */
    private void hidezoomView() {
        // 隐藏logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
//        final int count = mMapView.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View child = mMapView.getChildAt(i);
//            if (child instanceof ZoomControls) {
//                child.setVisibility(View.INVISIBLE);
//            }
//        }
    }


    /**
     * 自定义点标记
     *
     * @param point
     * @param adress
     * @param time
     * @param type   0默认点，1带点击事件
     */
    public void setPoint(LatLng point, String adress, String time, int type, String img, final int i) {
//        mBaiduMap.clear();
        // 创建InfoWindow展示的自定义view,显示详情对话框
        TextView button = setPop(adress);

        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromAssetWithDpi(img);

        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point)
                .icon(bitmap).zIndex(3);
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.mapmark_layout,
                null);
        TextView location_time = (TextView) contentView
                .findViewById(R.id.location_time);
        TextView location_address = (TextView) contentView
                .findViewById(R.id.location_address);

        location_address.setText(adress);
        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBaiduMap.hideInfoWindow();
            }
        });
        // 构建对话框用于显示
        // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        final InfoWindow mInfoWindow = new InfoWindow(contentView, point, -20);

        markClick = new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
//                mBaiduMap.showInfoWindow(mInfoWindow);
                Log.i("TAG", "getTrainID" + ltrain.get(i).getTrainID());
                return false;
            }
        };
        if (type == 1) {
            location_time.setText(time);
//             显示InfoWindow
//            mBaiduMap.showInfoWindow(mInfoWindow);
            mBaiduMap.setOnMarkerClickListener(markClick);
        }
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        // 正常显示
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        if (type == 1) {
            // 定义地图状态
            updateStatus(point, 5);
        } else {
            // 定义地图状态
            updateStatus(point, 5);
        }

    }

    /**
     * 自定义地图对话框，展示详情
     *
     * @param adress
     * @return
     */
    private TextView setPop(String adress) {
        TextView button = new TextView(mContext);
        button.setBackgroundColor(getResources().getColor(R.color.white));
        button.setTextSize(14);
        button.setTextColor(getResources().getColor(R.color.black));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                200, LinearLayout.LayoutParams.MATCH_PARENT);
        button.setText(adress);
        layoutParams.setMargins(50, 0, 50, 0);
        button.setLayoutParams(layoutParams);

        return button;
    }

    /**
     * update地图的状态与变化
     *
     * @param point
     * @param zoom
     */
    private void updateStatus(LatLng point, int zoom) {
        MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(zoom)
                .build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate =

                MapStatusUpdateFactory.newMapStatus(mMapStatus);
        // 改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

}
