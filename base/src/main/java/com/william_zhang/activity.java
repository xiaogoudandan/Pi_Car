package com.william_zhang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.william_zhang.base.R;
import com.william_zhang.base.R2;
import com.zyao89.view.zloading.ZLoadingTextView;
import com.zyao89.view.zloading.ZLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william_zhang on 2018/2/24.
 */

public class activity extends AppCompatActivity {
    @BindView(R2.id.z_loading_view)
    ZLoadingView zLoadingView;
    @BindView(R2.id.z_text_view)
    ZLoadingTextView zTextView;
    @BindView(R2.id.z_custom_text_view)
    TextView zCustomTextView;
    @BindView(R2.id.loading)
    LinearLayout loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_loading_dialog);
        ButterKnife.bind(this);
    }
}
