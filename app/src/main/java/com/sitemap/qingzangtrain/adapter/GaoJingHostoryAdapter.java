package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;

/**
 * @desc 历史告警  适配器
 * Created by chenmeng on 2017/2/8.
 */

public class GaoJingHostoryAdapter extends BaseAdapter{
    private Context mContext;
    private Holder holder;
    private OnCallBackLook mCallBack;

    public interface OnCallBackLook{
        void onLookInfo(int pos);
    }

    public GaoJingHostoryAdapter(Context context,OnCallBackLook callBack){
        this.mContext = context;
        this.mCallBack = callBack;
    }

    @Override
    public int getCount() {
        return 3;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gao_jing_hostory_item,null);
            holder = new Holder();
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.title = (TextView) convertView.findViewById(R.id.gaojing_title);
            holder.look = (TextView) convertView.findViewById(R.id.look_desc);
            holder.train = (TextView) convertView.findViewById(R.id.train);
            holder.trainCode = (TextView) convertView.findViewById(R.id.train_code);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.level = (TextView) convertView.findViewById(R.id.level);
            holder.mTableLayout = (LinearLayout) convertView.findViewById(R.id.table_layout);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack!=null){
                    mCallBack.onLookInfo(position);
                }
            }
        });

//        for (int i=0;i<3;i++){
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT
//            );
//            View tableView = getTableView(mContext);
//            holder.mTableLayout.addView(tableView,params);
//        }

        return convertView;
    }

    private class Holder{
        TextView time,look,title;//时间 查看详情 z
        TextView train,trainCode,number;//车次 车箱 编号
        TextView type,level;//类别  级别
        TextView systemName,systemTel,systemState;// 类别 名称  电话 状态
        LinearLayout mTableLayout;//表格布局
    }

    public View getTableView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.include_table_item,null);
        return view;
    }
}
