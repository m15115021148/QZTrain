package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.http.HttpImageUtil;

import java.util.List;

/**
 * @desc 处理详情 图片 适配器
 * Created by chenmeng on 2017/2/7.
 */
public class DealDetailAdapter extends BaseAdapter{
    private Context mContext;
    private List<Bitmap> mList;
    private Holder holder;

    public DealDetailAdapter(Context context,List<Bitmap> list){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.deal_details_img_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

//        if (position==0){
//
//        }else{
//            HttpImageUtil.loadImage(holder.img,mList.get(position));
            holder.img.setImageBitmap(mList.get(position));
//        }

        return convertView;
    }

    private class Holder{
        ImageView img;
    }
}
