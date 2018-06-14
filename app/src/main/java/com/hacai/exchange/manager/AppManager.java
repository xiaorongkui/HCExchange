package com.hacai.exchange.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator on 2016/3/3.
 * 进行单例
 * <p>
 * 添加、删除当前、删除指定的activity、清空栈、求栈大小
 */
public class AppManager {

    public static Stack<Activity> activityStack = new Stack<Activity>();

    private static AppManager appManager = null;

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    /**
     * 添加一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (!activityStack.contains(activity)) activityStack.add(activity);
    }


    /**
     * 删除指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 删除指定activity
     *
     * @param clazz
     */
    public void removeActivity(Class<?> clazz) {
        Activity act = null;
        if (activityStack != null && activityStack.size() > 0)
            for (int i = activityStack.size() - 1; i >= 0; i--) {
                act = activityStack.get(i);
                if (act.getClass() == clazz) {
                    act.finish();
                    activityStack.remove(act);
                    break;
                }
            }
    }

    /**
     * 删除当前activity
     */
    public void removeCurrent() {
        Activity lastElement = activityStack.lastElement();
        if (lastElement != null) {
            lastElement.finish();
            activityStack.remove(lastElement);
        }

    }

    /**
     * 删除当前activity
     */
    public Activity getCurrent() {
        return activityStack.lastElement();
    }

    /**
     * 清空栈
     */
    public void clear() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }


    /**
     * 求栈大小
     *
     * @return
     */
    public int getSize() {
        return activityStack.size();
    }

}
