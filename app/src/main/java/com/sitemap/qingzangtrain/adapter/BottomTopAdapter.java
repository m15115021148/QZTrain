package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.WarmDataModel;
import com.sitemap.qingzangtrain.model.WarmNumsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 首页 底部 适配器
 * Created by chenmeng on 2017/2/24.
 */

public class BottomTopAdapter extends BaseAdapter{
    private Context mContext;
    private String[] values = {"供电","轴温","制动","防滑器"};
    private int[] keys = {
            R.drawable.gongdian_bg,R.drawable.zhouwen_bg,R.drawable.zhidong_bg,R.drawable.fanghuaqi_bg
    };
    private Holder holder;
    private String[] num;//数量
    private List<WarmDataModel> mList;

    public BottomTopAdapter(Context context,WarmNumsModel model){
        this.mContext = context;
        this.num = initData(model);
        this.mList = getData(values,num,keys);
    }

    /**
     * 得到数量
     * @param model
     * @return
     */
    private String[] initData(WarmNumsModel model){
        String[] num = {
                model.getGongdian(),model.getZhouwen(),model.getZhidong(),model.getFanghuaqi()
        };
        return num;
    }

    /**
     * 得到数据
     * @param txt
     * @param num
     * @param res
     * @return
     */
    private List<WarmDataModel> getData(String[] txt,String[] num,int[] res){
        List<WarmDataModel> list = new ArrayList<>();
        for (int i=0;i<txt.length;i++){
            WarmDataModel model = new WarmDataModel();
            model.setTxt(txt[i]);
            model.setNum(num[i]);
            model.setRes(res[i]);
            model.setType(i);
            list.add(model);
        }
        return list;
    }

    /**
     * 得到数据
     * @return
     */
    public List<WarmDataModel> getListData(){
        if (mList!=null&&mList.size()>0){
            return mList;
        }
        return null;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bottom_top_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.txt = (TextView) convertView.findViewById(R.id.txt);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        WarmDataModel model = mList.get(position);
        holder.img.setImageResource(model.getRes());
        holder.txt.setText(model.getTxt());
        holder.num.setText(model.getNum());
        if (model.getNum().equals("0")){
            holder.num.setVisibility(View.GONE);
            holder.layout.setSelected(false);
        }else{
            holder.num.setVisibility(View.VISIBLE);
            holder.layout.setSelected(true);
        }

        return convertView;
    }

    private class Holder{
        ImageView img;
        TextView txt,num;
        LinearLayout layout;
    }
}
