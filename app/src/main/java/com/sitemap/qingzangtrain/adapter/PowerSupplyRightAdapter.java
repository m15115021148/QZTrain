package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.model.TypeModel;
import com.sitemap.qingzangtrain.view.HorizontalListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 供电 右侧 适配器
 * Created by chenmeng on 2017/2/7.
 */
public class PowerSupplyRightAdapter extends BaseAdapter{
    private Context mContext;
    private Holder holder;
    private List<TypeModel> mTopList;//头部 数据
    private Map<Integer,List<TypeModel>> map;
    private LinearLayout.LayoutParams params;
    private TableHolder tableHolder;
    private OnCallBackListener mCallBack;

    public PowerSupplyRightAdapter(Context context ,Map<Integer,List<TypeModel>> map,
                                   List<TypeModel> topList,OnCallBackListener callBack){
        this.mContext = context;
        this.mTopList = topList;
        this.map = map;
        this.mCallBack = callBack;
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public interface OnCallBackListener{
        void onClickCharts(TypeModel model,int pos);
    }


    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.power_supply_right_item,null);
            holder = new Holder();
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.layout);
            holder.mList = setData(map.get(position),mTopList);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.mLayout.removeAllViews();
        for (TypeModel model:holder.mList){
            View tableView = getViewLayout(mContext,model,position);
            holder.mLayout.addView(tableView,params);
        }
//        for (int i=0;i<holder.mList.size();i++){
//            View tableView = getViewLayout(mContext,holder.mList.get(position),i);
//            holder.mLayout.addView(tableView,params);
//        }

        return convertView;
    }

    private class Holder{
        LinearLayout mLayout;
        List<TypeModel> mList;
    }

    private class TableHolder{
        TextView txt;
    }

    /**
     * 横向数据 显示
     * @param context
     * @param model
     * @return
     */
    private View getViewLayout(Context context,final TypeModel model,final int pos){
        View tableView = null;
        if (tableView == null){
            tableView = LayoutInflater.from(context).inflate(R.layout.data_power_recycle_item,null);
            tableHolder = new TableHolder();
            tableHolder.txt = (TextView) tableView.findViewById(R.id.txt);
            tableView.setTag(tableHolder);
        }else{
            tableHolder = (TableHolder) tableView.getTag();
        }
        tableHolder.txt.setText(model.getTypeName());
        if ("查看".equals(model.getTypeName())){
            tableHolder.txt.setTextColor(context.getResources().getColor(R.color.lan_dan));
        }else{
            tableHolder.txt.setTextColor(context.getResources().getColor(R.color.lightskyblue));
        }
        tableHolder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack!=null){
                    mCallBack.onClickCharts(model,pos);
                }
            }
        });
        return tableView;
    }

    /**
     * 数据 得到筛选的数据
     * @param list1
     */
    public List<TypeModel> setData(List<TypeModel> list1,List<TypeModel> currList){
        List<TypeModel> list = new ArrayList<>();
        list.clear();
        if (list1.size()==currList.size()){
            list = list1;
        }else{
            for (TypeModel model1:currList){
                for (TypeModel model:list1){
                    if (model1.getPos()==model.getPos()){
                        list.add(model);
                    }
                }
            }
        }
        return list;
    }

}
