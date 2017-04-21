package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.ZhouWenModel;

import java.util.List;

/**
 * @desc 轴温 适配器
 * Created by chenmeng on 2017/2/15.
 */

public class DataZhouWenAdapter extends BaseAdapter{
    private Context mContext;
    private List<ZhouWenModel> mList;
    private Holder holder;

    public DataZhouWenAdapter(Context context,List<ZhouWenModel> list){
        this.mContext = context;
        this.mList = list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.data_zhou_wen_item,null);
            holder = new Holder();
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        return convertView;
    }

    private class Holder{

    }
}
