package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.DesInfoModel;
import com.sitemap.qingzangtrain.model.TypeModel;

import java.util.List;

/**
 * @desc 核实 检修 适配器 告警详情
 * Created by chenmeng on 2017/2/9.
 */

public class GaoJingUploadAdapter extends BaseAdapter {
    private Context mContext;
    private List<DesInfoModel> mList;
    private Holder holder;
    private OnCallBackResultDes mCallBack;

    public GaoJingUploadAdapter(Context context, List<DesInfoModel> list, OnCallBackResultDes callBack){
        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;
    }

    public interface OnCallBackResultDes{
        void onResultClick(int pos);
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
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gao_jing_fix_or_check_des_item,null);
            holder = new Holder();
            holder.result = (TextView) convertView.findViewById(R.id.result);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.result.setText(mList.get(position).getDesInfo());
        holder.result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack!=null){
                    mCallBack.onResultClick(position);
                }
            }
        });
        if (mList.get(position).getIsSelect().equals("1")){
            holder.result.setSelected(true);
        }else{
            holder.result.setSelected(false);
        }
        return convertView;
    }

    private class Holder{
        TextView result;
    }
}
