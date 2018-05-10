package com.william_zhang.pi_car.mvp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.william_zhang.base.mvp.baseImpl.BaseActivity;
import com.william_zhang.pi_car.R;
import com.william_zhang.pi_car.adapter.HomeListAdapter;
import com.william_zhang.pi_car.config.ActivityId;
import com.william_zhang.pi_car.constant.Key;
import com.william_zhang.pi_car.db.ProjectModel;
import com.william_zhang.pi_car.mvp.contact.HomeContact;
import com.william_zhang.pi_car.mvp.presenter.HomePresenter;
import com.william_zhang.pi_car.utils.ForwardUtil;
import com.william_zhang.pi_car.widget.PopupList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by william_zhang on 2018/5/3.
 */

public class HomeActivity extends BaseActivity<HomeContact.presenter> implements HomeContact.view, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.app_home_toolbar)
    Toolbar appHomeToolbar;
    @BindView(R.id.app_home_rv)
    RecyclerView appHomeRv;
    @BindView(R.id.app_home_refresh)
    SwipeRefreshLayout appHomeRefresh;
    @BindView(R.id.app_home_iv_kong)
    ImageView appHomeIvKong;
    @BindView(R.id.app_home_tv_kong)
    TextView appHomeTvKong;
    @BindView(R.id.app_home_add_fab)
    FloatingActionButton appHomeAddFab;
    @BindView(R.id.app_home_search)
    MaterialSearchView appHomeSearch;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    toBlocklyActivity((ProjectModel) msg.obj);
                    break;
            }
        }
    };
    private List<String> popupMenuItemList = new ArrayList<>();
    private float mRawX;
    private float mRawY;

    @OnClick(R.id.app_home_add_fab)
    void buildProject() {
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_input, null);
        new AlertDialog.Builder(this).setView(view)
                .setTitle("输入项目名称：")
                .setPositiveButton("创建", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = (EditText) view.findViewById(R.id.dialog_input);
                        String name = editText.getText().toString();
                        if (name.length() > 0) {
                            presenter.buildProject(name);
                        } else {
                            Snackbar.make(appHomeAddFab, "输入不为空", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private HomeListAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presenter.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem item = menu.findItem(R.id.app_menu_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_menu_search:
                presenter.setSearch();
                appHomeSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(HomeActivity.this, "第" + i + "行", Toast.LENGTH_LONG).show();
                        TextView textView = (TextView) view.findViewById(R.id.suggestion_text);
                        presenter.search(textView.getText().toString());
                    }
                });
                appHomeSearch.showSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public HomeContact.presenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void init() {
        popupMenuItemList.add("删除");
        appHomeToolbar.setTitle("项目管理");
        appHomeToolbar.setTitleTextColor(getResources().getColor(R.color._ffffff));
        setSupportActionBar(appHomeToolbar);
        mAdapter = new HomeListAdapter(R.layout.item_home_project, null);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        appHomeRv.setLayoutManager(mLinearLayoutManager);
        appHomeRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProjectModel projectModel = (ProjectModel) adapter.getData().get(position);
                Message message = new Message();
                message.obj = projectModel;
                message.what = 1;
                handler.sendMessageDelayed(message, 500);
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {
                PopupList popupList = new PopupList(view.getContext());
                int[] reat = new int[2];
                view.getLocationOnScreen(reat);
                popupList.showPopupListWindow(view, position, reat[0] + view.getWidth() / 2, reat[1], popupMenuItemList, new PopupList.AdapterPopupListListener() {
                    @Override
                    public String formatText(View adapterView, View contextView, int contextPosition, int position, String text) {
                        return text;
                    }

                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int p) {
                        presenter.deleteProject((ProjectModel) adapter.getData().get(position));
                    }
                });
                return true;
            }
        });
        appHomeRefresh.setOnRefreshListener(this);
        appHomeRefresh.setRefreshing(true);
        presenter.refresh();
        presenter.setSearch();
        appHomeSearch.setVoiceSearch(false);
        appHomeSearch.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(appHomeAddFab, "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
        appHomeRv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mRawX = motionEvent.getRawX();
                mRawY = motionEvent.getRawY();
                return false;
            }
        });
    }

    @Override
    public void setListData(List<ProjectModel> list) {
        if (list != null && list.size() > 0) {
            appHomeRv.setVisibility(View.VISIBLE);
            appHomeIvKong.setVisibility(View.GONE);
            appHomeTvKong.setVisibility(View.GONE);
            mAdapter.setNewData(list);
            appHomeRefresh.setRefreshing(false);
        } else {
            mAdapter.setNewData(null);
            appHomeRv.setVisibility(View.GONE);
            appHomeIvKong.setVisibility(View.VISIBLE);
            appHomeTvKong.setVisibility(View.VISIBLE);
        }
        appHomeRefresh.setRefreshing(false);
        showSnacker("刷新成功");
    }

    @Override
    public void showSnacker(String str) {
        Snackbar.make(appHomeAddFab, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setRefresh(boolean b) {
        appHomeRefresh.setRefreshing(b);
    }

    @Override
    public void setSearchData(String[] array) {
        appHomeSearch.setSuggestions(array);
    }

    @Override
    public void toBlockly(ProjectModel projectModel) {
//        Intent intent = new Intent();
//        intent.putExtra(Key.PROJECT_NAME, projectModel.getProjectName());
//        intent.putExtra(Key.AUTO_NAME, projectModel.getAutoSaveName());
//        ForwardUtil.openActivity(HomeActivity.this, ActivityId.BLOCKLY_CAR, intent);
        Message message = new Message();
        message.obj = projectModel;
        message.what = 1;
        handler.sendMessageDelayed(message, 500);
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    public void onBackPressed() {
        if (appHomeSearch.isSearchOpen()) {
            appHomeSearch.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void toBlocklyActivity(ProjectModel projectModel) {
        Intent intent = new Intent();
        intent.putExtra(Key.PROJECT_NAME, projectModel.getProjectName());
        intent.putExtra(Key.AUTO_NAME, projectModel.getAutoSaveName());
        ForwardUtil.openActivity(HomeActivity.this, ActivityId.BLOCKLY_CAR, intent);
    }
}


