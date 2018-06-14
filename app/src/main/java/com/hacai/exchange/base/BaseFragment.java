package com.hacai.exchange.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hacai.exchange.utils.LogUtil;
import com.hacai.exchange.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/3/6.
 */
public abstract class BaseFragment extends Fragment {


    protected View rootView;
    protected Activity mContext;

    protected boolean isViewInitiated = false;
    protected boolean isVisibleToUser=false;
    protected boolean isDataInitiated=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (rootView == null) {// 生命周期方法会反复调用，但如果反复去加载布局是浪费，所以避免反复加载
            rootView = inflater.inflate(getLayoutId(), null);
            findViewId(rootView);
            initView();
        }
        return rootView;
    }

    protected abstract void initView();

    protected abstract void findViewId(View rootView);

    protected abstract void initData();

    protected abstract void initTitle();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isViewInitiated) {
            initTitle();
            isViewInitiated = true;
            prepareFetchData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }


    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        LogUtil.i("isVisibleToUser=" + isVisibleToUser + ";isViewInitiated=" + isViewInitiated +
                ";isDataInitiated=" + isDataInitiated);
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            initData();//走网络请求数据
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void toast(int res) {
        toast(mContext.getResources().getText(res));
    }

    protected void toastdefine(int str) {
        toastdefine(mContext.getResources().getText(str));
    }

    protected void toastdefine(CharSequence s) {
        ToastUtil.showToastWithImg(mContext, s);
    }

    protected void toast(CharSequence res) {
//        Toast mToast = null;
//        if (mToast == null) {
//            mToast = Toast.makeText(mContext, res, Toast.LENGTH_SHORT);
//        } else {
//            mToast.setText(res);
//            mToast.setDuration(Toast.LENGTH_SHORT);
//        }
//        mToast.show();
        toastdefine(res);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNeedCountPage()) MobclickAgent.onPageStart(getSimpleNme());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isNeedCountPage()) MobclickAgent.onPageEnd(getSimpleNme());
    }

    protected abstract String getSimpleNme();

    protected boolean isNeedCountPage() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewInitiated = false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        prepareFetchData();
        LogUtil.i("onHiddenChanged=" + hidden);
    }
}
