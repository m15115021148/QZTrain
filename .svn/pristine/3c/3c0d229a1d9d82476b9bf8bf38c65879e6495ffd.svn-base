package com.sitemap.qingzangtrain.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.StatisticsInfoModel;
import com.sitemap.qingzangtrain.model.StatisticsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 首页 右侧 布局 适配器
 * Created by chenmeng on 2017/2/13.
 */

public class MainDrawerRightAdapter extends BaseAdapter{
    private Context mContext;
    private List<StatisticsModel> mList;
    private Holder holder;
    private static final int[] resImg = {
            R.drawable.qz_25,R.drawable.qz_27,R.drawable.qz_41,R.drawable.qz_42,
            R.drawable.qz_48,R.drawable.qz_49,R.drawable.qz_54,R.drawable.qz_55
    };
    private static final String[] txt = {
            "列车总数量","在线总数量","离线数据","备用车数",
            "行驶中数量","停止中数量","GPS故障数量","故障车数量"
    };

    public MainDrawerRightAdapter(Context context, StatisticsInfoModel model){
        this.mContext = context;
        mList = getData(model);
    }

    private List<StatisticsModel> getData(StatisticsInfoModel model){
        String[] num = {
                model.getNum1(),model.getNum2(),model.getNum3(),model.getNum4(),
                model.getNum5(),model.getNum6(),model.getNum7(),model.getNum8()
        };

        List<StatisticsModel> list = new ArrayList<>();
        for (int i=0;i<num.length;i++){
            StatisticsModel model1 = new StatisticsModel();
            model1.setName(txt[i]);
            model1.setResImg(resImg[i]);
            model1.setNum(num[i]);
            list.add(model1);
        }
        return list;
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
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_drawer_right_item,null);
            holder = new Holder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        StatisticsModel model = mList.get(position);

        holder.num.setText(model.getNum());
        holder.name.setText(model.getName());
        holder.img.setImageResource(model.getResImg());

        return convertView;
    }

    private class Holder{
        TextView name,num;
        ImageView img;
    }
}
