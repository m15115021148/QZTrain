package com.sitemap.qingzangtrain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * com.sitemap.wisdomjingjiang.views
 * 
 * @author	chenmeng
 * @Description	ScrollView与GridView 冲突的解决
 * 使 GridView 的子布局 能占满整个view
 * @date create at 2016年5月11日 上午9:31:12
 */
public class MyGridView extends GridView {

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
