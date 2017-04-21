package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.TypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */

public class TypeGridAdapter extends BaseAdapter {
    private Context mContext;//本类
    private Holder holder;//帮助类
    private List<TypeModel> list=new ArrayList<TypeModel>();
    private OnCallBackSelect mCallBack;
    private int type;

    public TypeGridAdapter(Context context, List<TypeModel> list,int type,OnCallBackSelect callBack){
        this.mContext = context;
        this.list=list;
        this.type = type;
        this.mCallBack = callBack;
    }

    public interface OnCallBackSelect{
        void onSelected(int pos,int type);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.type_grid_item,null);
            holder = new Holder();
            holder.type_grid_name = (TextView) convertView.findViewById(R.id.type_grid_name);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.type_grid_name.setText(list.get(position).getTypeName());
        if (list.get(position).getIsSelect()==0){
            holder.type_grid_name.setBackgroundResource(R.drawable.qz_32);
            holder.type_grid_name.setTextColor(Color.parseColor("#333333"));
        }else{
            holder.type_grid_name.setBackgroundResource(R.drawable.qz_30);
            holder.type_grid_name.setTextColor(Color.parseColor("#ffffff"));
        }
        holder.type_grid_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack!=null){
                    mCallBack.onSelected(position,type);
                }
            }
        });
        return convertView;
    }

    private class Holder{
        TextView type_grid_name;
    }
}
