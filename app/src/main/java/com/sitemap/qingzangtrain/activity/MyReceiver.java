package com.sitemap.qingzangtrain.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.sitemap.qingzangtrain.application.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * com.sitemap.na2ne.activities.MyReceiver
 *
 * @author zhang
 *         极光自定义广播
 *         create at 2016年3月9日 下午5:13:05
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";//推送
    private Handler mhandler;//传过来的handler
    private final int JPUSH = 1002;//极光handler

    public MyReceiver(Handler mhandler) {
        this.mhandler = mhandler;
    }

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e(TAG,"bundle:"+JSON.toJSONString(bundle));
        Log.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + "\nextras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (message != null) {
                Log.i(TAG, message);
            }
            if (extras != null) {
                Log.i(TAG, extras);
            }
//            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//            Log.i(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            Log.e("result","接收到推送下来的通知");
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Log.i(TAG, "[MyReceiver] 用户点击打开了通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Log.e(TAG, "extras:"+extras);
            Log.e(TAG, "message:"+message);
            if (extras != null) {
                try {
//                    Intent q = new Intent();
//                    q.setAction("com.app2.activity");
//                    q.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(q);

                    JSONObject ob = new JSONObject(extras);
                    String  troubleID = ob.getString("troubleID");

                    Intent in = new Intent();
                    if (MyApplication.loginModel.getType().equals("1")||MyApplication.loginModel.getType().equals("4")){
                        in.setClass(context,GaojingLeadActivity.class);
                    }else if (MyApplication.loginModel.getType().equals("2")){
                        in.setClass(context,GaojingTrainmanActivity.class);
                    }else {
                        in.setClass(context,GaojingRepairerActivity.class);
                    }
                    in.putExtra("type",3);
                    in.putExtra("troubleLevel","1;2");
                    in.putExtra("troubleID",troubleID);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(in);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//            Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//            Log.i(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
//            Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }






    }

    /**
     * 打印所有的 intent extra 数据
     *
     * @param bundle
     * @return
     */
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            Log.e("result","key:"+key+"--value:"+bundle.getInt(key)+"\n");
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.i(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * 发送数据到页面
     *
     * @param context
     * @param bundle
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//        if (!message.equals("")&&message!=null) {
//            msgIntent.putExtra("jPushTitle", message);
//        }
//        if (!extras.equals("")&&extras!=null) {
//            msgIntent.putExtra("jPushMsg", extras);
//        }
        context.sendBroadcast(msgIntent);
    }

}
