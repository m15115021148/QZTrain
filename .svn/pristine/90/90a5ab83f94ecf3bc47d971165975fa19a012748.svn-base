package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.TypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc  供电 横向 数据 listview  适配器
 * Created by chenmeng on 2017/2/14.
 */

public class DataPowerHListViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<TypeModel> mList;
    private Holder holder;

    public DataPowerHListViewAdapter(Context context, List<TypeModel> list,List<TypeModel> currList){
        this.mContext = context;
        this.mList = setData(list,currList);
    }

    /**
     * 数据
     * @param list1
     */
    public List<TypeModel> setData(List<TypeModel> list1,List<TypeModel> currList){
        List<TypeModel> list = new ArrayList<>();
        if (list1.size()==currList.size()){
            list = list1;
        }else{
            for (TypeModel model1:currList){
                for (TypeModel model:list1){
                    if (model1.getPos()==model.getPos()){
                        list.add(model);
                    }
                }
            }
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.data_power_recycle_item,null);
            holder = new Holder();
            holder.txt = (TextView) convertView.findViewById(R.id.txt);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.txt.setText(mList.get(position).getTypeName());

        return convertView;
    }

    private class Holder{
        TextView txt;
    }
}
