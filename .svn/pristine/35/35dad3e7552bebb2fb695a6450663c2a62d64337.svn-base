package com.sitemap.qingzangtrain.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.PersonInfoModel;
import com.sitemap.qingzangtrain.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 表格数据 适配器
 * Created by chenmeng on 2017/2/10.
 */

public class GaoJingTableListViewAdapter extends BaseAdapter{
    private Activity mContext;
    private List<PersonInfoModel> mList;
    private TableHolder tableHolder;

    public GaoJingTableListViewAdapter(Activity context, List<PersonInfoModel> list){
        this.mContext = context;
        this.mList = list;
    }

    public void setData(List<PersonInfoModel> list){
        mList = list;
     }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.include_table_item, null);
            tableHolder = new TableHolder();
            tableHolder.type = (TextView) convertView.findViewById(R.id.type);
            tableHolder.name = (TextView) convertView.findViewById(R.id.name);
            tableHolder.tel = (TextView) convertView.findViewById(R.id.tel);
            tableHolder.status = (TextView) convertView.findViewById(R.id.state);
            tableHolder.mViewClick=new ViewClick();
            convertView.setTag(tableHolder);
        }else{
            tableHolder = (TableHolder) convertView.getTag();
        }

        PersonInfoModel model = mList.get(position);

        int process = Integer.parseInt(model.getProcess());
        if (model.getType().equals("1")) {
            tableHolder.type.setText("值班员");
            switch (process) {
                case 0:
                    tableHolder.status.setText("未下发");
                    tableHolder.status.setTextColor(Color.parseColor("#d8211e"));
                    break;
                case 1:
                    tableHolder.status.setText("下发中");
                    tableHolder.status.setTextColor(Color.parseColor("#ec760d"));
                    break;
                case 2:
                    tableHolder.status.setText("已下发");
                    tableHolder.status.setTextColor(Color.parseColor("#23c975"));
                    break;
                case 3:
                    tableHolder.status.setText("待指派");
                    tableHolder.status.setTextColor(Color.parseColor("#d8211e"));
                    break;
                case 4:
                    tableHolder.status.setText("误报");
                    tableHolder.status.setTextColor(Color.parseColor("#23c975"));
                    break;
                case 5:
                    tableHolder.status.setText("已指派");
                    tableHolder.status.setTextColor(Color.parseColor("#ec760d"));
                    break;
                case 6:
                    tableHolder.status.setText("已完成");
                    tableHolder.status.setTextColor(Color.parseColor("#23c975"));
                    break;
            }
        } else if (model.getType().equals("2")) {
            tableHolder.type.setText("乘务员");
            switch (process) {
                case 0://未处理
                    tableHolder.status.setText("— —");
                    tableHolder.status.setTextColor(Color.parseColor("#cccccc"));
                    break;
                case 1:
                    tableHolder.status.setText("处理中");
                    tableHolder.status.setTextColor(Color.parseColor("#ec760d"));
                    break;
                case 2:
                    tableHolder.status.setText("已完成");
                    tableHolder.status.setTextColor(Color.parseColor("#23c975"));
                    break;
                case 3:
                    tableHolder.status.setText("已完成");
                    tableHolder.status.setTextColor(Color.parseColor("#23c975"));
                    break;
            }
        } else if (model.getType().equals("3")) {
            tableHolder.type.setText("检修员");
            switch (process) {
                case 0://未处理
                    tableHolder.status.setText("— —");
                    tableHolder.status.setTextColor(Color.parseColor("#cccccc"));
                    break;
                case 1:
                    tableHolder.status.setText("检修中");
                    tableHolder.status.setTextColor(Color.parseColor("#ec760d"));
                    break;
                case 2:
                    tableHolder.status.setText("已完成");
                    tableHolder.status.setTextColor(Color.parseColor("#23c975"));
                    break;
            }
        }
        tableHolder.name.setText(model.getName());
        tableHolder.tel.setText(model.getPhone());
        tableHolder.mViewClick.setPosition(position);
        tableHolder.tel.setOnClickListener(tableHolder.mViewClick);
        if (model.getStatus().equals("1")){//值班中
            tableHolder.name.setTextColor(mContext.getResources().getColor(R.color.black));
            tableHolder.tel.setTextColor(mContext.getResources().getColor(R.color.lan_dan));
        }else{
            tableHolder.name.setTextColor(mContext.getResources().getColor(R.color.txt_hui));
            tableHolder.tel.setTextColor(mContext.getResources().getColor(R.color.txt_hui));
        }

        return convertView;
    }

    private class TableHolder {
        TextView type,name,tel,status;//类型 名称 电话 状态
       ViewClick mViewClick;
    }

    /**
     * view中的按钮点击事件
     */
    private class ViewClick implements View.OnClickListener{
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(mList.get(position).getStatus().equals("1")){//值班中
                View viewDialog = DialogUtil.customPromptDialog(mContext, "确定", "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.DIAL");
                                intent.setData(Uri.parse("tel:"+ mList.get(position).getPhone()));//mobile为你要拨打的电话号码，模拟器中为模拟器编号也可
                                mContext.startActivity(intent);
                            }
                        }, null);
                TextView tv = (TextView) viewDialog.findViewById(R.id.dialog_tv_txt);
                tv.setText("确定要拨打电话吗?");

            }else{
                View viewDialog = DialogUtil.customPromptDialog(mContext, "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:"+ mList.get(position).getPhone()));//mobile为你要拨打的电话号码，模拟器中为模拟器编号也可
                        mContext.startActivity(intent);
                    }
                },null);
                TextView tv = (TextView) viewDialog.findViewById(R.id.dialog_tv_txt);
                tv.setText("尚未在值班中，确定要拨打电话吗?");
            }

        }
    }
}
