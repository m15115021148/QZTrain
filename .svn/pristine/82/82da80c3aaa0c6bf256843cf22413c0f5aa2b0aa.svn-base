package com.sitemap.qingzangtrain.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.sitemap.qingzangtrain.R;


/**
 * @desc 动画的显示
 * Created by chenmeng on 2016/11/29.
 */
public class AnimaUtil {

    /**
     * @param orientation true从左向右滑动，false 从右向左滑动
     */
    public static void setLayoutOrder(Context context,LinearLayout layout, boolean orientation){
        if(orientation){
            Animation ani = AnimationUtils.loadAnimation(context, R.anim.exit);
            LayoutAnimationController lc = new LayoutAnimationController(ani);
            lc.setOrder(LayoutAnimationController.ORDER_NORMAL);
            layout.setLayoutAnimation(lc);
        }else{
            Animation ani = AnimationUtils.loadAnimation(context, R.anim.enter);
            LayoutAnimationController lc = new LayoutAnimationController(ani);
            lc.setOrder(LayoutAnimationController.ORDER_NORMAL);
            layout.setLayoutAnimation(lc);
        }
    }

    /**
     * 动画的平移 以自身为中心 以x，y轴移动
     * @param view
     * @param duration
     */
    public static void setTranslateAnimation(View view, long duration){
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,-4.0f
        );

        animationSet.setDuration(duration);
        animationSet.addAnimation(translateAnimation);
        view.startAnimation(animationSet);
    }

    /**
     * 动画的显示 从完全的透明度，到完全的不透明度
     * @param view 对象
     * @param duration 时间
     */
    public static void setAlphaAnimation(View view,long duration){
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        animationSet.setDuration(duration);
        animationSet.addAnimation(alphaAnimation);
        view.startAnimation(animationSet);
    }

}
