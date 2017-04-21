package com.sitemap.qingzangtrain.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.adapter.DealDetailAdapter;
import com.sitemap.qingzangtrain.adapter.GaoJingUploadAdapter;
import com.sitemap.qingzangtrain.adapter.GaoJingTableListViewAdapter;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.config.RequestCode;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.http.HttpUtil;
import com.sitemap.qingzangtrain.model.DesInfoModel;
import com.sitemap.qingzangtrain.model.ResultModel;
import com.sitemap.qingzangtrain.model.TroublesModel;
import com.sitemap.qingzangtrain.util.DialogUtil;
import com.sitemap.qingzangtrain.util.FileNames;
import com.sitemap.qingzangtrain.util.ImageUtil;
import com.sitemap.qingzangtrain.util.SystemFunUtil;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.MyGridView;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 核实详情 或者 检修详情 页面
 * @author chenmeng created by 2017/2/7
 */
@ContentView(R.layout.activity_gaojing_upload)
public class GaojingUploadActivity extends BaseActivity implements View.OnClickListener,GaoJingUploadAdapter.OnCallBackResultDes{
    private GaojingUploadActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.deal_note)
    private EditText mNote;//备注
    @ViewInject(R.id.deal_gridView)
    private MyGridView mGv;//图片
    @ViewInject(R.id.deal_sure)
    private TextView mSubmit;//提交
    @ViewInject(R.id.photo)
    private TextView photo;//照相
    @ViewInject(R.id.video)
    private TextView video;//小视屏
    private List<String> mListUpload;//图片集合 上传
    private static final int IMG_RESULT_CODE = 0x001;//标识符 相机
    private static final int IMG_RESULT_PHONE_CODE = 0x002;//相册
    private static final String[] values = {"相机","相册"};
    private File imgFile = null;//保存图片的文件
    private SystemFunUtil imgUtil;// 拍照
    private DealDetailAdapter mImgAdapter;//图片适配器
    private PopupWindow popDialog;//dialog
    @ViewInject(R.id.include_listView)
    private ListView mTableLv;//表格布局listView
    @ViewInject(R.id.result_gridView)
    private MyGridView mDetailGv;//告警详情 gridview
    @ViewInject(R.id.img)
    private ImageView mTableImg;//图片类型
    @ViewInject(R.id.gaojing_title)
    private TextView mTableTitle;//表格标题
    @ViewInject(R.id.train)
    private TextView mTableTrains;//车次
    @ViewInject(R.id.train_code)
    private TextView mTableTrainCode;//车箱
    @ViewInject(R.id.number)
    private TextView mTableNumber;//编号
    @ViewInject(R.id.type)
    private TextView mTableType;//类别
    @ViewInject(R.id.level)
    private TextView mTableLevel;//级别
    private GaoJingUploadAdapter mResultAdapter;//详情 适配器
    private int typeActivity = 1;//1 核实详情页面  2 检修详情页面
    private TroublesModel troublesModel;//数据
    private RoundProgressDialog progressDialog;//加载条
    private HttpUtil http;//网络请求
    private List<DesInfoModel> mDesInfoList;//常用语
    private String desID = "",troubleID;//信息id 故障ID
    private int type = 1;//属实情况（1：真实存在，2：误报）
    private List<Bitmap> mListImg = new ArrayList<>();//图片显示 集合
    private File saveFile;//图片上传 缓存文件夹
    @ViewInject(R.id.last)
    private TextView last;//历时
    private static final int REQUEST_EXTERNAL_STORAGE = 1;//动态添加权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
    private final int VIDEO=0x1009;
    private boolean isvideo=false;
    private int index=0;
    private String path="";//视频路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
        initImgGridView();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HttpUtil.SUCCESS:
                    //获取故障（告警）列表
                    if (msg.arg1 == RequestCode.GETDESINFO){
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();// 关闭进度条
                        }
                        mDesInfoList = new ArrayList<>();
                        mDesInfoList.clear();
                        mDesInfoList = JSONObject.parseArray(msg.obj.toString(),DesInfoModel.class);
                        initResultGridView(mDesInfoList);
                    }
                    //提交核查信息
                    if (msg.arg1 == RequestCode.UPLOADCHECKINFO){
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();// 关闭进度条
                        }
                        ResultModel model = JSONObject.parseObject(msg.obj.toString(),ResultModel.class);
                        if (model.getResult().equals("1")){
                            ToastUtil.showBottomLong(mContext,"提交成功");
                            setResult(100);
                            File sampleDir = new File(Environment.getExternalStorageDirectory()
                                    + File.separator + "ysb/video/");//录制视频的保存地址
//                            ImageUtil.deleteFolder(sampleDir);
                            mContext.finish();
                        }else{
                            ToastUtil.showBottomLong(mContext,model.getErrorMsg());
                        }
                    }
                    //提交维修信息
                    if (msg.arg1 == RequestCode.UPLOADFIXINFO){
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();// 关闭进度条
                        }
                        ImageUtil.deleteFolder(saveFile);
                        ResultModel model = JSONObject.parseObject(msg.obj.toString(),ResultModel.class);
                        if (model.getResult().equals("1")){
                            ToastUtil.showBottomLong(mContext,"提交成功");
                            setResult(100);
                            File sampleDir = new File(Environment.getExternalStorageDirectory()
                                    + File.separator + "ysb/video/");//录制视频的保存地址
                            ImageUtil.deleteFolder(sampleDir);
                            mContext.finish();
                        }else{
                            ToastUtil.showBottomLong(mContext,model.getErrorMsg());
                        }
                    }
                    break;
                case HttpUtil.EMPTY:
                    break;
                case HttpUtil.FAILURE:
                    ToastUtil.showBottomLong(mContext,RequestCode.ERRORINFO);
                    break;
                case HttpUtil.LOADING:
                    if (msg.arg1 == RequestCode.UPLOADCHECKINFO) {
                        progressDialog.setMessage(msg.arg2 + "%");
                    }
                    //提交维修信息
                    if (msg.arg1 == RequestCode.UPLOADFIXINFO){
                        progressDialog.setMessage(msg.arg2 + "%");
                    }
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
        verifyStoragePermissions(this);
        typeActivity = getIntent().getIntExtra("type",1);
        troublesModel = (TroublesModel) getIntent().getSerializableExtra("TroublesModel");
        mTableTrains.setText(getResources().getString(R.string.trains) + troublesModel.getTrains());
        mTableTrainCode.setText(getResources().getString(R.string.carriage) + troublesModel.getCarriageNum());
        mTableNumber.setText(getResources().getString(R.string.carriageNum) + troublesModel.getCarriageName());
        mTableLevel.setText(getResources().getString(R.string.trainLevel) + troublesModel.getTroubleLevel());
        mTableTitle.setText(troublesModel.getTroubleName());
        last.setText(Html.fromHtml(getResources().getString(R.string.trainLastTime)+"<font color='#ff0000'>"+troublesModel.getLastTime()+"</font>"));
        if (troublesModel.getTroubleType().equals("1")) {//供电
            mTableType.setText(R.string.type_gongdian);
        } else if (troublesModel.getTroubleType().equals("2")) {//轴温
            mTableType.setText(R.string.type_zhouwen);
        } else if (troublesModel.getTroubleType().equals("3")) {//制动
            mTableType.setText(R.string.type_zhidong);
        }else if (troublesModel.getTroubleType().equals("4")) {//防滑器
            mTableType.setText(R.string.type_fanghuaqi);
        } else if (troublesModel.getTroubleType().equals("5")) {//烟火
            mTableType.setText(R.string.type_yaohuo);
        }else if (troublesModel.getTroubleType().equals("6")) {// 车门
            mTableType.setText(R.string.type_chemen);
        } else if (troublesModel.getTroubleType().equals("7")) {// 制氧机
            mTableType.setText(R.string.type_zhiyangji);
        }else if (troublesModel.getTroubleType().equals("8")) {// 空调
            mTableType.setText(R.string.type_kongtian);
        }else if (troublesModel.getTroubleType().equals("9")) {//车下电源
            mTableType.setText(R.string.type_dianyuan);
        }else{//未知
            mTableType.setText(R.string.type_other);
        }

        if (troublesModel.getTroubleLevel().equals("1")) {//1级
            mTableImg.setImageResource(R.drawable.level_1);
            mTableTitle.setTextColor(Color.parseColor("#d9211e"));
        } else if (troublesModel.getTroubleLevel().equals("2")) {//2
            mTableImg.setImageResource(R.drawable.level_2);
            mTableTitle.setTextColor(Color.parseColor("#e46520"));
        } else if (troublesModel.getTroubleLevel().equals("3")) {//3
            mTableImg.setImageResource(R.drawable.level_3);
            mTableTitle.setTextColor(Color.parseColor("#ef9e3e"));
        } else {//处理完成
            mTableImg.setImageResource(R.drawable.level_4);
            mTableTitle.setTextColor(Color.parseColor("#23c975"));
        }

        if (typeActivity==1){
            mTitle.setText("核实详情");
        }else{
            mTitle.setText("检修详情");
        }
        if (http == null){
            http = new HttpUtil(handler);
        }
        mBack.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        photo.setOnClickListener(this);
        video.setOnClickListener(this);
        GaoJingTableListViewAdapter mTableAdapter = new GaoJingTableListViewAdapter(mContext,troublesModel.getPersonsInfoList());
        mTableLv.setAdapter(mTableAdapter);
        getDesInfo(String.valueOf(typeActivity));
    }

    /**
     * 常用语详情
     */
    private void initResultGridView(List<DesInfoModel> list){
        for (int i=0;i<list.size();i++){
            if (i==0){
                list.get(i).setIsSelect("1");
            }else{
                list.get(i).setIsSelect("0");
            }
        }
        DesInfoModel model = new DesInfoModel();
        model.setIsSelect("0");
        model.setDesInfo("误报");
        model.setDesID("-1");
        list.add(model);
        desID = list.get(0).getDesID();
        mResultAdapter = new GaoJingUploadAdapter(mContext, list,this);
        mDetailGv.setAdapter(mResultAdapter);
    }

    /**
     * 获取常用语
     */
    private void getDesInfo(String type){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.GETDESINFO, WebUrlConfig.getDesInfo(type));
        } else {
            ToastUtil.showBottomShort(this, RequestCode.NOLOGIN);
        }
    }

    /**
     * 提交核查信息
     */
    private void upLoadCheckInfo(String userID,String desID,String desNote,String troubleID,String type){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            RequestParams params = http.getParams(WebUrlConfig.upLoadCheckInfo());
            params.addBodyParameter("userID",userID);
            params.addBodyParameter("desID",desID);
            params.addBodyParameter("desNote",desNote);
            params.addBodyParameter("troubleID",troubleID);
            params.addBodyParameter("type",type);
            for (String str: mListUpload){
                if (!TextUtils.isEmpty(str)){
                    params.addBodyParameter("img", new File(str));
                }
            }
            if (isvideo){
                Log.i("jack","path:"+path);
                File video = new File(path);//录制视频的保存地址
                params.addBodyParameter("video",video);
            }
            http.uploadFile(RequestCode.UPLOADCHECKINFO,params);
        } else {
            ToastUtil.showBottomShort(this, RequestCode.NOLOGIN);
        }
    }

    /**
     * 提交维修信息
     */
    private void upLoadFixInfo(String userID,String desID,String desNote,String troubleID,String type){
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(this);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            RequestParams params = http.getParams(WebUrlConfig.upLoadFixInfo());
            params.addBodyParameter("userID",userID);
            params.addBodyParameter("desID",desID);
            params.addBodyParameter("desNote",desNote);
            params.addBodyParameter("troubleID",troubleID);
//            params.addBodyParameter("type",type);
            for (String str: mListUpload){
                if (!TextUtils.isEmpty(str)){
                    params.addBodyParameter("img", new File(str));
                }
            }
            if (isvideo){
                File video = new File(path);//录制视频的保存地址
                params.addBodyParameter("video",video);
            }
            http.uploadFile(RequestCode.UPLOADFIXINFO,params);
        } else {
            ToastUtil.showBottomShort(this, RequestCode.NOLOGIN);
        }
    }

    /**
     * 初始化图片
     */
    private void initImgGridView(){
        imgUtil = new SystemFunUtil(mContext);
        mListUpload = new ArrayList<>();
        saveFile = imgUtil.createRootDirectory("upload");
        initImg();
        mImgAdapter = new DealDetailAdapter(mContext, mListImg);
        mGv.setAdapter(mImgAdapter);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                View dialog = DialogUtil.customPromptDialog(mContext, "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (position==index){
                            isvideo=false;
                        }
                        mListImg.remove(position);
                        mImgAdapter.notifyDataSetChanged();
                        if (mListImg.size()>0){
                            mSubmit.setSelected(true);
                        }else{
                            mSubmit.setSelected(false);
                        }
                    }
                },null);
                TextView tv = (TextView) dialog.findViewById(R.id.dialog_tv_txt);
                tv.setText("确定要删除吗？");
            }
        });
    }

    /**
     * 获取图片文件夹下的所有图片
     */
    private void initImg() {
        // 加入一个空对象
//        mListImg.add(null);
        if (imgFile == null) {
            return;
        }
    }

    @Override
    public void onClick(View v) {
        if (v==photo){

            if (isvideo) {
                if (mListImg.size() == 5) {
                    ToastUtil.showBottomShort(mContext, "最多上传四张图片");
                    return;
                }
            }else {
                if (mListImg.size() == 4) {
                    ToastUtil.showBottomShort(mContext, "最多上传四张图片");
                    return;
                }
            }
            popDialog = DialogUtil.customPopShowWayDialog(mContext, DialogUtil.DialogShowWay.FROM_DOWN_TO_UP, values,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tv = (TextView) v;
                            String name = tv.getText().toString().trim();
                            if (name.equals(values[0])){//拍照
                                imgUtil.openCamera(SystemFunUtil.SYSTEM_IMAGE, IMG_RESULT_CODE);
                            }
                            if (name.equals(values[1])){
                                imgUtil.openCamera(SystemFunUtil.SYSTEM_IMAGE_PHONE, IMG_RESULT_PHONE_CODE);
                            }
                            popDialog.dismiss();
                        }
                    });

        }
        if(v==video){
            if (isvideo){
                ToastUtil.showBottomShort(mContext,"最多上传一个短视频");
                return;
            }else {
                Intent intent=new Intent(GaojingUploadActivity.this,VideoActivity.class);
                startActivityForResult(intent,VIDEO);
            }

        }
        if (v == mBack){
            onBackPressed();
            mContext.finish();
        }
        if (v == mSubmit){
            ImageUtil.deleteFolder(saveFile);
            if (mListImg.size() <1) {
                ToastUtil.showBottomLong(mContext,"上传图片至少一张");
                return;
            }
            for (int i=0;i<mListImg.size();i++){
                if (isvideo){
                    if (i==index){
                        continue;
                    }
                }
                FileNames names = new FileNames();
                mListUpload.add(ImageUtil.saveBitmap(saveFile.getPath(),mListImg.get(i),names.getImageName(i)));
            }
            if (desID.equals("-1")){//误报
                type = 2;
            }else{
                type = 1;
            }
            if (typeActivity==1){
                upLoadCheckInfo(MyApplication.loginModel.getUserID(),desID,mNote.getText().toString(),troublesModel.getTroubleID(),String.valueOf(type));
            } else{
                upLoadFixInfo(MyApplication.loginModel.getUserID(),desID,mNote.getText().toString(),troublesModel.getTroubleID(),String.valueOf(type));
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMG_RESULT_CODE){//相机
                try{
                    imgFile = imgUtil.getImgFile();
                    mListImg.add(ImageUtil.getSmallBitmap(imgFile.getPath()));
                    //删除原有图片
                    ImageUtil.deleteFilePath(imgFile.getPath());
                    mImgAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(requestCode == IMG_RESULT_PHONE_CODE){//相册
                try {
                    Uri uri = data.getData();
                    String imageAbsolutePath = imgUtil.getImageAbsolutePath(uri);
                    Bitmap bt = ImageUtil.getSmallBitmap(imageAbsolutePath);
                    mListImg.add(bt);
                    mImgAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (resultCode==VIDEO){
            if (requestCode==VIDEO){
                 path=data.getStringExtra("path");
                if (path!=null&&!path.equals("")){
                    Bitmap bmp = ThumbnailUtils.createVideoThumbnail(path,
                            MediaStore.Video.Thumbnails.MINI_KIND);
                    mListImg.add(bmp);
                    mImgAdapter.notifyDataSetChanged();
                    index=mListImg.size()-1;
                    isvideo=true;
                    Log.i("jack","path:"+path);
                }
            }
        }
        if (mListImg.size()>0){
            mSubmit.setSelected(true);
        }else{
            mSubmit.setSelected(false);
        }
    }

    @Override
    public void onBackPressed() {
        ImageUtil.deleteFolder(saveFile);
        super.onBackPressed();
    }

    @Override
    public void onResultClick(int pos) {
        for (int i=0;i<mDesInfoList.size();i++){
            mDesInfoList.get(i).setIsSelect("0");
        }
        mDesInfoList.get(pos).setIsSelect("1");
        mResultAdapter.notifyDataSetChanged();
        desID = mDesInfoList.get(pos).getDesID();
    }

    /**
     * 获取手机拍照 相册 权限
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
