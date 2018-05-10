package com.william_zhang.pi_car.mvp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.william_zhang.base.mvp.baseImpl.BaseActivity;
import com.william_zhang.base.utils.ActivityManager;
import com.william_zhang.pi_car.R;
import com.william_zhang.pi_car.config.ActivityId;
import com.william_zhang.pi_car.config.ActivityMapping;
import com.william_zhang.pi_car.mvp.contact.SplashContact;
import com.william_zhang.pi_car.mvp.presenter.SplashPresenter;
import com.william_zhang.pi_car.utils.AppUtils;
import com.william_zhang.pi_car.utils.ForwardUtil;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william_zhang on 2018/5/1.
 */

public class SplashActivity extends BaseActivity<SplashContact.presenter> implements SplashContact.view {

    @BindView(R.id.splash_time)
    TextView splashTime;
    @BindView(R.id.splash_name)
    TextView splashName;
    @BindView(R.id.splash_c)
    TextView splashC;
    @BindView(R.id.splash_a)
    TextView splashA;
    @BindView(R.id.splash_r)
    TextView splashR;

    private TextView[] tvs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter.init();
        presenter.startTime(3);

    }

    @Override
    public SplashContact.presenter initPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public void init() {
        tvs = new TextView[]{splashA, splashC, splashR};
        initAnimation();
    }

    private void initAnimation() {
        splashName.post(new Runnable() {
            @Override
            public void run() {
                for (TextView textView : tvs) {
                    textView.setVisibility(View.VISIBLE);
                    startTextInAnim(textView);
                }
            }
        });
    }

    private void startTextInAnim(TextView textView) {
        Random r = new Random();
        DisplayMetrics metrics = AppUtils.getMetrics(this);
        int x = r.nextInt(metrics.widthPixels * 4 / 3);
        int y = r.nextInt(metrics.heightPixels * 4 / 3);
        float s = r.nextFloat() + 4.0f;
        //设置动画
        ValueAnimator tranY = ObjectAnimator.ofFloat(textView, "translationY", y - textView.getY(), 0);
        ValueAnimator tranX = ObjectAnimator.ofFloat(textView, "translationX", x - textView.getX(), 0);
        ValueAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", s, 1.0f);
        ValueAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", s, 1.0f);
        ValueAnimator alpha = ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1800);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.play(tranX).with(tranY).with(scaleX).with(scaleY).with(alpha);
        if (textView == splashR) {
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    startFinalAnim();
                }
            });
        }
        set.start();
    }


    private void startFinalAnim() {
        splashName.setVisibility(View.VISIBLE);
        ValueAnimator alphaN = ObjectAnimator.ofFloat(splashName, "alpha", 0.0f, 1.0f);
        alphaN.setDuration(1000);
        alphaN.start();
        alphaN.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                splashName.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void setTime(int time) {
        splashTime.setText(Integer.toString(time));
    }

    @Override
    public void forWord() {
        ForwardUtil.openActivity(this, ActivityId.HOME, new Intent());
        finish();
    }
}
