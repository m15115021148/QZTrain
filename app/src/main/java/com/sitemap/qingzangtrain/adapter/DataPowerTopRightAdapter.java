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

import static android.R.id.list;

/**
 * @desc 供电 头部 右侧 适配器
 * Created by chenmeng on 2017/2/16.
 */

public class DataPowerTopRightAdapter extends BaseAdapter{
    private Context mContext;
    private List<TypeModel> mList;
    private Holder holder;

    public DataPowerTopRightAdapter(Context context, List<TypeModel> list){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.data_power_recycle_item,null);
            holder = new Holder();
            holder.txt = (TextView) convertView.findViewById(R.id.txt);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        TypeModel model = mList.get(position);
        if(model.getTypeName().length()>4){
            holder.txt.setText(setChars(model.getTypeName()));
        }else{
            holder.txt.setText(model.getTypeName());
        }

        return convertView;
    }

    private class Holder{
        TextView txt;
    }

    /**
     * 设置字符串换行
     * @return
     */
    private String setChars(String txt){
        StringBuffer sb = new StringBuffer(txt);
//        sb.insert(txt.length()-2,"\n");
        sb.insert(4,"\n");
        return sb.toString();
    }
}
