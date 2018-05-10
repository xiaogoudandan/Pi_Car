package com.william_zhang.pi_car.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.william_zhang.pi_car.R;
import com.william_zhang.pi_car.db.ProjectModel;

import java.util.List;

/**
 * Created by william_zhang on 2018/5/3.
 */

public class HomeListAdapter extends BaseQuickAdapter<ProjectModel, BaseViewHolder> {
    public HomeListAdapter(@Nullable List<ProjectModel> data) {
        super(data);
    }

    public HomeListAdapter(int layoutResId, @Nullable List<ProjectModel> data) {
        super(layoutResId, data);
    }

    public HomeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectModel item) {
        String name = item.getProjectName();

        if (name.length() > 4 && name.substring(name.length() - 4).equals(".xml"))
            helper.setText(R.id.app_home_item_name, name.substring(0, name.length() - 4));
        else {
            helper.setText(R.id.app_home_item_name, name);
        }
        helper.setText(R.id.app_home_item_time, item.getBuildTime());
    }
}
