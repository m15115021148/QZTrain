package com.sitemap.qingzangtrain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sitemap.qingzangtrain.R;
import com.sitemap.qingzangtrain.http.HttpImageUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_image)
public class ImageActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.img)
    ImageView img;//图片
    private String path;//图片地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path=getIntent().getStringExtra("path");
        HttpImageUtil.loadImage(img,path);
        img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==img){
            finish();
        }
    }
}
