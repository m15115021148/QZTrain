package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.TypeModel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class DataPowerFilterAdapter extends BaseAdapter {
    private Context mContext;
    private List<TypeModel> mList;
    private Holder holder;
    private OnCallBackFilter mCallBack;

    public DataPowerFilterAdapter(Context context,List<TypeModel> list,OnCallBackFilter callBack){
        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;
    }

    public interface OnCallBackFilter{
        void onClickListener(int pos);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.data_power_filter_item,null);
            holder = new Holder();
            holder.cb = (TextView) convertView.findViewById(R.id.check_box);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.cb.setText(mList.get(position).getTypeName());
        if (mList.get(position).getIsSelect()==0){
            holder.cb.setSelected(false);
        }else{
            holder.cb.setSelected(true);
        }

        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack!=null){
                    mCallBack.onClickListener(position);
                }
            }
        });

        return convertView;
    }

    private class Holder{
        TextView cb;
    }
}
