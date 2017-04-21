package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.WarmsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 单个告警 页面 列表 适配器
 * Created by chenmeng on 2017/2/27.
 */

public class WarmListAdapter extends BaseAdapter{
    private Context mContext;
    private Holder holder;
    private OnWarmCallBack mCallBack;
    private List<WarmsModel> mList;

    public WarmListAdapter(Context context,List<WarmsModel> list,OnWarmCallBack callBack){
        this.mContext = context;
        this.mCallBack = callBack;
        this.mList = list;
    }

    public interface OnWarmCallBack{
        void onLookListener(int pos);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.warm_list_item,null);
            holder = new Holder();
            holder.trains = (TextView) convertView.findViewById(R.id.trains);
            holder.number = (TextView) convertView.findViewById(R.id.train_number);
            holder.result = (TextView) convertView.findViewById(R.id.train_result);
            holder.look = (TextView) convertView.findViewById(R.id.train_look);
            holder.isShow = (TextView) convertView.findViewById(R.id.isShow);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        WarmsModel model = mList.get(position);
        holder.trains.setText(model.getCarriage());
        holder.number.setText(model.getCarriageNum());
        holder.result.setText(model.getTroubleName());

        holder.look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack!=null){
                    mCallBack.onLookListener(position);
                }
            }
        });
        if (position==mList.size()-1){
            holder.isShow.setVisibility(View.VISIBLE);
        }else{
            holder.isShow.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class Holder{
        TextView trains,number,result;//车厢 编号  故障原因
        TextView look;//查看详情
        TextView isShow;//是否显示
    }
}
