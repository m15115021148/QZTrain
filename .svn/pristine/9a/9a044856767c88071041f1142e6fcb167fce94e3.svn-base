package com.sitemap.qingzangtrain.util;

import android.util.Log;

/**日志记录工具
 * Created by chenhao on 2016/11/17.
 */
public class LogUtil {

    /**
     * 标记
     */
    private static final  String FLAGE="will";

    /**
     * 是否开启，默认开启
     */
    private static boolean isOpen=true;

    /**
     * 开启模式
     * @param mode
     */
    public static void setDebug(boolean mode){
        isOpen=mode;
    }

    /**
     * 输出i级日志
     * @param msg
     */
    public static void i(String msg){
        if(isOpen){
            Log.i(FLAGE,msg);
        }
    }

    /**
     * 输出v级日志
     * @param msg
     */
    public static void v(String msg){
        if(isOpen){
            Log.v(FLAGE,msg);
        }
    }

    /**
     * 输出e级日志
     * @param msg
     */
    public static void e(String msg){
        if(isOpen){
            Log.e(FLAGE,msg);
        }
    }

    /**
     * 输出w级日志
     * @param msg
     */
    public static void w(String msg){
        if(isOpen){
            Log.w(FLAGE,msg);
        }
    }
}
