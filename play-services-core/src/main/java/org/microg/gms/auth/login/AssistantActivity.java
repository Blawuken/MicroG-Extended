/*
 * Copyright (C) 2013-2017 microG Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.microg.gms.auth.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import com.skydoves.elasticviews.ElasticButton;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.R;

public abstract class AssistantActivity extends AppCompatActivity {
    private static final int TITLE_MIN_HEIGHT = 64;
    private static final double TITLE_WIDTH_FACTOR = (8.0 / 18.0);
    private FullScreenVideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_assistant);
        mVideoView = (FullScreenVideoView) this.findViewById(R.id.videoView);
        playVideoView();
        formatTitle();
        findViewById(R.id.spoof_button).setOnClickListener(v -> onHuaweiButtonClicked());
        findViewById(R.id.next_button).setOnClickListener(v -> onNextButtonClicked());
    }

    @SuppressLint("WrongViewCast")
    private void formatTitle() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            double widthPixels = (double) (getResources().getDisplayMetrics().widthPixels);
            findViewById(R.id.title_container).getLayoutParams().height =
                    (int) (dpToPx(TITLE_MIN_HEIGHT) + (TITLE_WIDTH_FACTOR * widthPixels));
        } else {
            findViewById(R.id.title_container).getLayoutParams().height = dpToPx(TITLE_MIN_HEIGHT);
        }
    }

    private void playVideoView() {
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //播放
        mVideoView.start();
        //循环播放
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVideoView.start();
            }
        });
    }

    public void setSpoofButtonText(@StringRes int res) {
        setSpoofButtonText(getText(res));
    }

    public void setSpoofButtonText(CharSequence text) {
        if (text == null) {
            findViewById(R.id.spoof_button).setVisibility(View.GONE);
        } else {
            findViewById(R.id.spoof_button).setVisibility(View.VISIBLE);
            ((ElasticButton) findViewById(R.id.spoof_button)).setText(text);
        }
    }

    public void setNextButtonText(@StringRes int res) {
        setNextButtonText(getText(res));
    }

    public void setNextButtonText(CharSequence text) {
        if (text == null) {
            findViewById(R.id.next_button).setVisibility(View.GONE);
        } else {
            findViewById(R.id.next_button).setVisibility(View.VISIBLE);
            ((ElasticButton) findViewById(R.id.next_button)).setText(text);
        }
    }

    protected void onHuaweiButtonClicked() {

    }

    protected void onNextButtonClicked() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        formatTitle();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        ((TextView) findViewById(R.id.title)).setText(title);
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        playVideoView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        mVideoView.stopPlayback();
        super.onStop();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
