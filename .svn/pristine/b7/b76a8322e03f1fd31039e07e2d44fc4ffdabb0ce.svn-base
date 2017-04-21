package com.sitemap.qingzangtrain.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.adapter.DealDetailAdapter;
import com.sitemap.qingzangtrain.util.DialogUtil;
import com.sitemap.qingzangtrain.util.SystemFunUtil;
import com.sitemap.qingzangtrain.view.MyGridView;
import com.sitemap.qingzangtrain.view.WheelView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理详情页面
 * @author chenmeng created by 2017/2/7
 */
@ContentView(R.layout.activity_gaojing_deal_details)
public class GaojingDealDetailsActivity extends BaseActivity implements View.OnClickListener{
    private GaojingDealDetailsActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.deal_detail)
    private TextView mDetail;//描述
    @ViewInject(R.id.deal_note)
    private EditText mNote;//备注
    @ViewInject(R.id.deal_gridView)
    private MyGridView mGv;//图片
    @ViewInject(R.id.deal_sure)
    private TextView mSubmit;//提交
    private List<String> mList;//图片集合
    private static final int IMG_RESULT_CODE = 0x001;//标识符 相机
    private static final int IMG_RESULT_PHONE_CODE = 0x002;//相册
    private static final String[] values = {"相机","相册"};
    private File imgFile = null;//保存图片的文件
    private SystemFunUtil imgUtil;// 拍照
    private DealDetailAdapter adapter;//适配器
    private PopupWindow popDialog;//dialog
    private String[] detailResult ={"不知道","差评","知道你的好","其他"};//选择的结果集合
    private int currSel = 0;//当前选中的选项
    @ViewInject(R.id.table_layout)
    private LinearLayout mTableLayout;//表格布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
        initGridView();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mTitle.setText("处理详情");
        mBack.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mDetail.setOnClickListener(this);
        for (int i=0;i<1;i++){
            View view = getLayoutInflater().inflate(R.layout.include_table_item,null);
            TextView type = (TextView) view.findViewById(R.id.type);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView tel = (TextView) view.findViewById(R.id.tel);
            TextView state = (TextView) view.findViewById(R.id.state);
            type.setText("乘务员"+i);
            name.setText("陈笑"+i);
            tel.setText("1509898232"+i);
            state.setText("已派遣"+i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            mTableLayout.addView(view,params);
        }
    }

    /**
     * 初始化图片
     */
    private void initGridView(){
        imgUtil = new SystemFunUtil(mContext);
        mList = new ArrayList<>();
        initImg();
//        adapter = new DealDetailAdapter(mContext,mList);
        mGv.setAdapter(adapter);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position == 0) {//添加图片
//                    if (mList.size() == 4) {
//                        ToastUtil.showBottomShort(mContext, "最多上传三张图片");
//                        return;
//                    }
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

                }else{
                    View dialog = DialogUtil.customPromptDialog(mContext, "确定", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    },null);
                    TextView tv = (TextView) dialog.findViewById(R.id.dialog_tv_txt);
                    tv.setText("确定要删除吗？");
                }
            }
        });
    }

    /**
     * 获取图片文件夹下的所有图片
     */
    private void initImg() {
        // 加入一个空对象
        mList.add("");
        if (imgFile == null) {
            return;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            mContext.finish();
        }
        if (v == mDetail){
            View outerView = LayoutInflater.from(this).inflate(
                    R.layout.wheel_view, null);
            WheelView wv = (WheelView) outerView
                    .findViewById(R.id.wheel_view_wv);
            wv.setOffset(2);
            wv.setItems(Arrays.asList(detailResult));
            wv.setSeletion(currSel);
            wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex,
                                       String item) {
                   currSel = selectedIndex-2;
                }
            });

            new AlertDialog.Builder(this).setTitle("选择车次")
                    .setView(outerView)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDetail.setText(detailResult[currSel]);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
        if (v == mSubmit){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMG_RESULT_CODE){//相机
                try{
                    imgFile = imgUtil.getImgFile();
                    mList.add(imgFile.getPath());
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(requestCode == IMG_RESULT_PHONE_CODE){//相册
                try {
                    Uri uri = data.getData();
                    String imageAbsolutePath = imgUtil.getImageAbsolutePath(uri);
                    mList.add(imageAbsolutePath);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
