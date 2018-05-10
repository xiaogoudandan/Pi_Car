package com.william_zhang.pi_car.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by william_zhang on 2018/5/3.
 */
@Database(name = DataBase.db_name,version = DataBase.version)
public class DataBase {
    public static final String db_name="car_db";
    public static final int version = 1;
}
