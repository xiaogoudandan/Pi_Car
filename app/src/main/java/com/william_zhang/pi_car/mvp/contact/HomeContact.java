package com.william_zhang.pi_car.mvp.contact;

import com.william_zhang.base.mvp.BasePresenter;
import com.william_zhang.base.mvp.BaseView;
import com.william_zhang.pi_car.db.ProjectModel;

import java.util.List;

/**
 * Created by william_zhang on 2018/5/3.
 */

public interface HomeContact  {
    interface view extends BaseView{

        void init();

        void setListData(List<ProjectModel> list);

        void showSnacker(String str);

        void setRefresh(boolean b);

        void setSearchData(String[] array);

        void toBlockly(ProjectModel projectModel);
    }

    interface presenter extends BasePresenter{

        void init();

        void refresh();

        void buildProject(String name);

        void setSearch();

        void search(String s);

        void deleteProject(ProjectModel projectModel);
    }
}
