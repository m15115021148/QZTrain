package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;

/**
 * 数据 页面 左侧适配器
 * Created by chenmeng on 2017/3/22.
 */

public class DataLeftAdapter extends BaseAdapter{
    private Context mContext;
    private int[] keys = {
            R.drawable.d_03,R.drawable.d_06,R.drawable.d_08,
            R.drawable.d_10,R.drawable.d_12,R.drawable.d_14,
            R.drawable.d_16,R.drawable.d_18,R.drawable.d_20,
            R.drawable.d_22,R.drawable.d_24
    };
    private String[] values = {
            "状态","起始/终点","车次组",
            "监控车","在线/编组","运行区间",
            "GPS状态","晚点时间","速度(km/h)",
            "维护建议","上报时间"
    };
    private Holder holder;

    public DataLeftAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.data_left_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.img.setImageResource(keys[position]);
        holder.name.setText(values[position]);
        return convertView;
    }

    private class Holder{
        ImageView img;
        TextView name;
    }
}
