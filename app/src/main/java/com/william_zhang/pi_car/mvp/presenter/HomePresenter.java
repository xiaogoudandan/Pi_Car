package com.william_zhang.pi_car.mvp.presenter;

import android.os.Handler;
import android.os.Message;

import com.raizlabs.android.dbflow.sql.SqlUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.william_zhang.base.mvp.baseImpl.BasePresenterImpl;
import com.william_zhang.base.utils.AppUtil;
import com.william_zhang.pi_car.MyApplication;
import com.william_zhang.pi_car.db.ProjectModel;
import com.william_zhang.pi_car.db.ProjectModel_Table;
import com.william_zhang.pi_car.mvp.contact.HomeContact;

import java.util.Date;
import java.util.List;

/**
 * Created by william_zhang on 2018/5/3.
 */

public class HomePresenter extends BasePresenterImpl<HomeContact.view> implements HomeContact.presenter {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    delayRefresh();
                    break;
            }
        }
    };

    public HomePresenter(HomeContact.view view) {
        super(view);
    }

    @Override
    public void init() {
        view.init();
    }

    @Override
    public void refresh() {
        handler.sendEmptyMessageDelayed(1, 1000);
    }


    public void delayRefresh() {
        //请求数据
        List<ProjectModel> list = SQLite.select().from(ProjectModel.class).queryList();
        //设置数据
        view.setListData(list);
    }

    @Override
    public void buildProject(String name) {
        //查询是否存在该名称
        List<ProjectModel> list = SQLite.select().from(ProjectModel.class).where(ProjectModel_Table.projectName.eq(name + ".xml")).queryList();
        if (list.size() > 0) {
            view.showSnacker("项目名称已经存在");
        } else {
            ProjectModel projectModel = new ProjectModel();
            projectModel.setBuildTime(new Date().toString());
            projectModel.setProjectName(name + ".xml");
            projectModel.setAutoSaveName(name + "_auto" + ".xml");
            projectModel.setType(1);
            if (projectModel.save()) {
                view.showSnacker("添加成功");
                view.setRefresh(true);
                refresh();
            } else {
                view.showSnacker("添加失败");
            }
        }
    }

    @Override
    public void setSearch() {
        //请求数据
        List<ProjectModel> list = SQLite.select().from(ProjectModel.class).queryList();
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getProjectName();
        }
        view.setSearchData(array);
    }

    @Override
    public void search(String s) {
        List<ProjectModel> list = SQLite.select().from(ProjectModel.class).where(ProjectModel_Table.projectName.eq(s)).queryList();
        if (list.size() == 1) {
            view.toBlockly(list.get(0));
        }
    }

    @Override
    public void deleteProject(ProjectModel projectModel) {
        boolean isDelete = projectModel.delete();
        AppUtil.deleteDataFile(projectModel.projectName, MyApplication.getApplication());
        AppUtil.deleteDataFile(projectModel.autoSaveName, MyApplication.getApplication());
        if (isDelete) {
            view.showSnacker("删除成功");
            view.setRefresh(true);
            refresh();
        } else {
            view.showSnacker("删除失败");
        }
    }

    @Override
    public void detach() {
        handler.removeCallbacksAndMessages(null);
        super.detach();
    }
}
