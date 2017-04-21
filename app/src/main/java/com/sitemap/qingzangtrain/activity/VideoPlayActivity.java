package com.sitemap.qingzangtrain.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import com.sitemap.qingzangtrain.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_video_play)
public class VideoPlayActivity extends BaseActivity {
    @ViewInject(R.id.videoView1)
    private VideoView videoView1;//视频播放控件
    private String file;//视频路径
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        file = bundle.getString("path");//获得拍摄的短视频保存地址
        videoView1.setVideoPath(file);
        videoView1.start();
        videoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }
}
