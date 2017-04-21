package com.sitemap.qingzangtrain.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.TroublesModel;

import java.util.List;

/**
 * @desc 检修员报警  适配器
 * Created by chenmeng on 2017/2/8.
 */

public class GaoJingRepairerAdapter extends BaseAdapter {
    private Activity mContext;
    private Holder holder;
    private OnCallBackLook mCallBack;
    List<TroublesModel> mList;
    boolean isExist=false;//子view是否已绘制

    public interface OnCallBackLook {
        void onLookInfo(int pos);
    }

    public GaoJingRepairerAdapter(Activity context, List<TroublesModel> list, OnCallBackLook callBack) {
        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gao_jing_trainman_item, null);
            holder = new Holder();
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.title = (TextView) convertView.findViewById(R.id.gaojing_title);
            holder.look = (TextView) convertView.findViewById(R.id.look_desc);
            holder.train = (TextView) convertView.findViewById(R.id.train);
            holder.trainCode = (TextView) convertView.findViewById(R.id.train_code);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.level = (TextView) convertView.findViewById(R.id.level);
            holder.last = (TextView) convertView.findViewById(R.id.last);
            holder.mTableLayout = (LinearLayout) convertView.findViewById(R.id.table_layout);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.mListView = (ListView) convertView.findViewById(R.id.include_listView);
            holder.mTableAdapter = new GaoJingTableListViewAdapter(mContext,null);
            holder.mViewClick=new ViewClick();
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            isExist=true;
        }

        TroublesModel model = mList.get(position);
        holder.train.setText(mContext.getResources().getString(R.string.trains) + model.getTrains());
        holder.trainCode.setText(mContext.getResources().getString(R.string.carriage) + model.getCarriageNum());
        holder.number.setText(mContext.getResources().getString(R.string.carriageNum) + model.getCarriageName());
        holder.level.setText(mContext.getResources().getString(R.string.trainLevel) + model.getTroubleLevel());
        holder.time.setText(model.getTime());
        holder.title.setText(model.getTroubleName());
        holder.last.setText(Html.fromHtml(mContext.getResources().getString(R.string.trainLastTime)+"<font color='#ff0000'>"+model.getLastTime()+"</font>"));

        if (model.getTroubleType().equals("1")) {//供电
            holder.type.setText(mContext.getResources().getString(R.string.type_gongdian));
        } else if (model.getTroubleType().equals("2")) {//轴温
            holder.type.setText(mContext.getResources().getString(R.string.type_zhouwen));
        } else if (model.getTroubleType().equals("3")) {//制动
            holder.type.setText(mContext.getResources().getString(R.string.type_zhidong));
        }else if (model.getTroubleType().equals("4")) {//防滑器
            holder.type.setText(mContext.getResources().getString(R.string.type_fanghuaqi));
        } else if (model.getTroubleType().equals("5")) {//烟火
            holder.type.setText(mContext.getResources().getString(R.string.type_yaohuo));
        }else if (model.getTroubleType().equals("6")) {// 车门
            holder.type.setText(mContext.getResources().getString(R.string.type_chemen));
        } else if (model.getTroubleType().equals("7")) {// 制氧机
            holder.type.setText(mContext.getResources().getString(R.string.type_zhiyangji));
        } else if(model.getTroubleType().equals("8")){//空调
            holder.type.setText(mContext.getResources().getString(R.string.type_kongtian));
        }else if(model.getTroubleType().equals("9")){//车下电源
            holder.type.setText(mContext.getResources().getString(R.string.type_dianyuan));
        }else {//未知
            holder.type.setText(mContext.getResources().getString(R.string.type_other));
        }

        if (model.getTroubleStatus().equals("0")){
            if (model.getTroubleLevel().equals("1")) {//1级
                holder.img.setImageResource(R.drawable.level_1);
                holder.title.setTextColor(Color.parseColor("#d9211e"));
            } else if (model.getTroubleLevel().equals("2")) {//2
                holder.img.setImageResource(R.drawable.level_2);
                holder.title.setTextColor(Color.parseColor("#e46520"));
            } else if (model.getTroubleLevel().equals("3")) {//3
                holder.img.setImageResource(R.drawable.level_3);
                holder.title.setTextColor(Color.parseColor("#ef9e3e"));
            }
        }else{//处理完成
            holder.img.setImageResource(R.drawable.level_4);
            holder.title.setTextColor(Color.parseColor("#23c975"));
        }

        if(isExist){
            holder.mTableAdapter.setData(model.getPersonsInfoList());
            holder.mTableAdapter.notifyDataSetChanged();
        }else{
            holder.mTableAdapter.setData(model.getPersonsInfoList());
            holder.mListView.setAdapter(holder.mTableAdapter);
        }

        if (model.getStatus().equals("0")){
            holder.look.setText("去检修");
            holder.look.setBackground(mContext.getResources().getDrawable(R.drawable.deal_sure_bg));
        }else if(model.getStatus().equals("1")){
            holder.look.setText("去检修");
            holder.look.setBackground(mContext.getResources().getDrawable(R.drawable.deal_sure_bg));
        } else{
            holder.look.setText("查看检修信息");
            holder.look.setBackground(mContext.getResources().getDrawable(R.drawable.deal_sure_over_bg));
        }

        holder.mViewClick.setPosition(position);
        holder.look.setOnClickListener(holder.mViewClick);

        return convertView;
    }

    private class Holder {
        ImageView img;//级别图片
        TextView time, look, title;//时间 查看详情 z
        TextView train, trainCode, number;//车次 车箱 编号
        TextView type, level,last;//类别  级别
        LinearLayout mTableLayout;//表格布局
        ListView mListView;//表格数据 listview
        GaoJingTableListViewAdapter mTableAdapter;//
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
            if (mCallBack != null) {
                mCallBack.onLookInfo(position);
            }
        }
    }
}
