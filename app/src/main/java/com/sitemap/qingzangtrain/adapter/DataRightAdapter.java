package com.sitemap.qingzangtrain.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.http.HttpImageUtil;
import com.sitemap.qingzangtrain.model.CurrentTrainModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 实时数据 右侧适配器
 * Created by chenmeng on 2017/3/22.
 */

public class DataRightAdapter extends BaseAdapter {
    private Context mContext;
    private List<CurrentTrainModel> mList;
    private Holder holder;
    private LinearLayout.LayoutParams params;
    private TableHolder tableHolder;

    public DataRightAdapter(Context context, List<CurrentTrainModel> list) {
        this.mContext = context;
        this.mList = list;
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public int getCount() {
        return 1;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.data_right_item, null);
            holder = new Holder();
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.mLayout.removeAllViews();
        for (CurrentTrainModel model : mList) {
            View tableView = getViewLayout(mContext, model);
            holder.mLayout.addView(tableView, params);
        }

        return convertView;
    }

    private class Holder {
        LinearLayout mLayout;
    }

    private class TableHolder {
        ImageView img,state;
        TextView name, tn, jiankong, zaixian, qujian, lon, lasttime, speed, weihu, time;
    }

    /**
     * 横向数据 显示
     *
     * @param context
     * @param model
     * @return
     */
    private View getViewLayout(Context context, CurrentTrainModel model) {
        View tableView = null;
        if (tableView == null) {
            tableView = LayoutInflater.from(context).inflate(R.layout.data_right_name_item, null);
            tableHolder = new TableHolder();
            tableHolder.state = (ImageView) tableView.findViewById(R.id.state);
            tableHolder.img = (ImageView) tableView.findViewById(R.id.img);
            tableHolder.name = (TextView) tableView.findViewById(R.id.name);
            tableHolder.tn = (TextView) tableView.findViewById(R.id.tn);
            tableHolder.jiankong = (TextView) tableView.findViewById(R.id.jiankong);
            tableHolder.zaixian = (TextView) tableView.findViewById(R.id.zaixian);
            tableHolder.qujian = (TextView) tableView.findViewById(R.id.qujian);
            tableHolder.lon = (TextView) tableView.findViewById(R.id.lon);
            tableHolder.lasttime = (TextView) tableView.findViewById(R.id.lasttime);
            tableHolder.speed = (TextView) tableView.findViewById(R.id.speed);
            tableHolder.weihu = (TextView) tableView.findViewById(R.id.weihu);
            tableHolder.time = (TextView) tableView.findViewById(R.id.time);
            tableView.setTag(tableHolder);
        } else {
            tableHolder = (TableHolder) tableView.getTag();
        }
        if (model.getState().equals("0")){//0 正常；1 告警；2 故障；3离线
            tableHolder.state.setImageResource(R.drawable.state_1);
        }else if (model.getState().equals("1")){//
            tableHolder.state.setImageResource(R.drawable.state_2);
        }else if (model.getState().equals("2")){//
            tableHolder.state.setImageResource(R.drawable.state_3);
        }else if (model.getState().equals("3")){//
            tableHolder.state.setImageResource(R.drawable.state_4);
        }
//        tableHolder.img.setImageBitmap(getImageResources(mContext,model.getImg()));
        HttpImageUtil.loadImageGif(tableHolder.img,"assets://"+model.getImg());

        tableHolder.name.setText("沈阳/拉萨");
        tableHolder.tn.setText(model.getTn());
        tableHolder.jiankong.setText(model.getJianKong());
        tableHolder.zaixian.setText(model.getZaixian());
        tableHolder.qujian.setText(model.getQuJian());
        tableHolder.lon.setText(model.getLon().equals("0") ? "故障" : "正常");
        tableHolder.lasttime.setText(model.getLateTime().equals("")?"— —":(model.getLateTime()));
        tableHolder.speed.setText(model.getSpeed());
        tableHolder.weihu.setText(model.getWeiHu().equals("")?"— —":(model.getWeiHu()));
        String[] date = model.getTime().split(" ");
        String y = date[0].replace("-", "/");
        String t = date[1];
        String tex = y + "\n" + t;

        SpannableString msp = new SpannableString(tex);
        msp.setSpan(new RelativeSizeSpan(0.9f), 0, y.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new RelativeSizeSpan(1.0f), y.length(), tex.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tableHolder.time.setText(msp);

        return tableView;
    }

    /**
     * 从Assets中读取图片
     * @param context
     * @param fileName
     * @return
     */
    private Bitmap getImageResources(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
