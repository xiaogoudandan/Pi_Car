package com.william_zhang.pi_car.config;

import com.william_zhang.pi_car.mvp.activity.BlocklyActivity;
import com.william_zhang.pi_car.mvp.activity.HomeActivity;

import java.util.HashMap;

/**
 * Created by william_zhang on 2017/12/2.
 */

public class ActivityMapping {
    private static ActivityMapping mActivityMapping;
    private HashMap<String, ActivityStruct> mHashmap = new HashMap<>();

    public static ActivityMapping getInstance() {
        if (mActivityMapping == null) {
            synchronized (ActivityMapping.class) {
                if (mActivityMapping == null) {
                    mActivityMapping = new ActivityMapping();
                }
            }
        }
        return mActivityMapping;
    }

    public ActivityMapping() {
        mHashmap.put(ActivityId.BLOCKLY_CAR, new ActivityStruct(BlocklyActivity.class, "blockly"));
        mHashmap.put(ActivityId.HOME, new ActivityStruct(HomeActivity.class, "首页"));
    }

    public ActivityStruct getStruct(String id) {
        if (mHashmap.containsKey(id)) {
            return mHashmap.get(id);
        } else {
            return null;
        }
    }
}
