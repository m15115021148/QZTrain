package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.DataLeftModel;
import com.sitemap.qingzangtrain.model.PowerSupplyModel;
import com.sitemap.qingzangtrain.util.ParserUtil;

import java.util.List;

import static com.baidu.location.h.i.T;

/**
 * @desc 供电 左侧 适配器
 * Created by chenmeng on 2017/2/7.
 */

public class PowerSupplyLeftAdapter extends BaseAdapter{
    private Context mContext;
    private Holder holder;
    private List<DataLeftModel> mList;

    public PowerSupplyLeftAdapter(Context context,List<DataLeftModel> list){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.power_supply_left_item,null);
            holder = new Holder();
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.train = (TextView) convertView.findViewById(R.id.train);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        DataLeftModel model = mList.get(position);
        String[] date = model.getTime().split(" ");
        holder.date.setText(date[0]);
        holder.time.setText(date[1]);

        String txt = model.getCarriageNum()+"/"+model.getCarriageName();
        SpannableString msp = new SpannableString(txt);
        msp.setSpan(new RelativeSizeSpan(2.0f), 0, model.getCarriageNum().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new RelativeSizeSpan(1.0f), model.getCarriageNum().length(), txt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.train.setText(msp);

        return convertView;
    }

    private class Holder{
        TextView time,date,train;//时间 日期  车厢/编号
    }
}
