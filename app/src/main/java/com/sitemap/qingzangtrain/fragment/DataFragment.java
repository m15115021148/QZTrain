package com.sitemap.qingzangtrain.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.adapter.DataLeftAdapter;
import com.sitemap.qingzangtrain.adapter.DataRightAdapter;
import com.sitemap.qingzangtrain.application.MyApplication;
import com.sitemap.qingzangtrain.config.RequestCode;
import com.sitemap.qingzangtrain.config.WebUrlConfig;
import com.sitemap.qingzangtrain.http.HttpUtil;
import com.sitemap.qingzangtrain.model.CurrentTrainModel;
import com.sitemap.qingzangtrain.util.ParserUtil;
import com.sitemap.qingzangtrain.util.ToastUtil;
import com.sitemap.qingzangtrain.view.MyListView;
import com.sitemap.qingzangtrain.view.RoundProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 实时数据 页面
 * Created by chenmeng on 2017/3/21.
 */

public class DataFragment extends Fragment {
    private View view;// 视图
    private Context mContext;//activity
    private RoundProgressDialog progressDialog;//加载条
    private HttpUtil http;//网络请求
    private List<CurrentTrainModel> mList = new ArrayList<>();//数据
    private ListView mLeftLv;//左侧listView
    private MyListView mRightLv;//右侧listView
    private DataRightAdapter adapter;//适配器

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_data_layout, null);
        }
        mContext = inflater.getContext();
        mLeftLv = (ListView) view.findViewById(R.id.ListView_Left);
        mRightLv = (MyListView) view.findViewById(R.id.listView_right);

        initData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mList != null && mList.size() > 0) {
            mList.clear();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();// 关闭进度条
            }
            switch (msg.what) {
                case HttpUtil.SUCCESS:
                    //获取列车实时数据
                    if (msg.arg1 == RequestCode.SEARCHCURRENTTRAININFO) {
                        mList.clear();
                        mList = ParserUtil.jsonToList(msg.obj.toString(), CurrentTrainModel.class);
                        initRightListViewData(mList);
                    }
                    break;
                case HttpUtil.EMPTY:
                    //获取列车实时数据
                    if (msg.arg1 == RequestCode.SEARCHCURRENTTRAININFO) {
                        mList.clear();
                        if (adapter!=null)
                            adapter.notifyDataSetChanged();
                    }
                    break;
                case HttpUtil.FAILURE:
                    //获取列车实时数据
                    if (msg.arg1 == RequestCode.SEARCHCURRENTTRAININFO) {
                        mList.clear();
                        if (adapter!=null)
                            adapter.notifyDataSetChanged();
                    }
                    ToastUtil.showBottomLong(mContext, RequestCode.ERRORINFO);
                    break;
                case HttpUtil.LOADING:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化数据
     */
    private void initData() {
        if (http == null) {
            http = new HttpUtil(handler);
        }
        searchCurrentTrainInfo();
        initLeftListViewData();
    }

    /**
     * 获取网络数据
     */
    public void getNetData() {
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(mContext);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            searchCurrentTrainInfo();
        } else {
            ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
        }
    }

    /**
     * 查询列车运行状态
     */
    private void searchCurrentTrainInfo() {
        http.sendGet(RequestCode.SEARCHCURRENTTRAININFO, WebUrlConfig.searchCurrentTrainsInfo());
    }

    /**
     * 初始化左侧数据
     */
    private void initLeftListViewData() {
        DataLeftAdapter adapter = new DataLeftAdapter(mContext);
        mLeftLv.setAdapter(adapter);
    }

    /**
     * 初始化右侧数据
     *
     * @param list
     */
    private void initRightListViewData(List<CurrentTrainModel> list) {
        adapter = new DataRightAdapter(mContext, list);
        mRightLv.setAdapter(adapter);
    }


}
