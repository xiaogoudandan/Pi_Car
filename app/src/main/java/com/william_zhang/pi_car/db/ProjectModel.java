package com.william_zhang.pi_car.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by william_zhang on 2018/5/3.
 */
@Table(database = DataBase.class)
public class ProjectModel extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    public String projectName;
    @Column
    public String buildTime;
    @Column
    public String autoSaveName;
    @Column
    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getAutoSaveName() {
        return autoSaveName;
    }

    public void setAutoSaveName(String autoSaveName) {
        this.autoSaveName = autoSaveName;
    }
}
