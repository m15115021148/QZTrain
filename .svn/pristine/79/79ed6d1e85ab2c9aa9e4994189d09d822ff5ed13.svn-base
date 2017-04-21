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
 * @desc 首页 左侧 布局 适配器
 * Created by chenmeng on 2017/2/13.
 */
public class MainDrawerLeftAdapter extends BaseAdapter{
    private Context mContext;
    private static final int[] resImg = {
            R.drawable.qz_39,R.drawable.qz_44,R.drawable.qz_50,R.drawable.qz_59
    };
    private static final String[] txt = {
            "实时监测","告警查询","数据统计","设置"
    };
    private Holder holder;

    public MainDrawerLeftAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return resImg.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_drawer_left_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.img.setImageResource(resImg[position]);
        holder.name.setText(txt[position]);

        return convertView;
    }

    private class Holder{
        ImageView img;
        TextView name;
    }
}
