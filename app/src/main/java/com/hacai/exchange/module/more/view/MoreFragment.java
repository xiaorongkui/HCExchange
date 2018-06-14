package com.hacai.exchange.module.more.view;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseFragment;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.CommonListInfo;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.more.presenter.MoreRecycleAdapter;
import com.hacai.exchange.ui.widget.AlterDialogUtils;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.hacai.exchange.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/7.
 */

public class MoreFragment extends BaseFragment implements View.OnClickListener {

    private View title_left_back;
    private TextView title_header_tv;
    private LinearLayout fragment_more_ll;
    private RecyclerView more_rv;
    private MoreRecycleAdapter moreRecycleAdapter;
    private View more_sevice_phone_ll;
    private AlterDialogUtils alterDialogUtils;

    @Override
    protected void initView() {
        initStatus();
        iniRecyclerView();
    }

    @Override
    protected void findViewId(View rootView) {
        title_header_tv = (TextView) rootView.findViewById(R.id.title_header_tv);
        title_left_back = rootView.findViewById(R.id.title_left_back);
        fragment_more_ll = (LinearLayout) rootView.findViewById(R.id.fragment_more_ll);
        more_rv = (RecyclerView) rootView.findViewById(R.id.more_rv);
        more_sevice_phone_ll = rootView.findViewById(R.id.more_sevice_phone_ll);

        more_sevice_phone_ll.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private List<CommonListInfo> datas = new ArrayList<>();

    private void iniRecyclerView() {
        datas.add(new CommonListInfo("关于我们", ""));
        datas.add(new CommonListInfo("帮助中心", ""));
        datas.add(new CommonListInfo("官方网站", "www.hacai360.com"));
        datas.add(new CommonListInfo("客服邮箱", "hacai@hacai360.com"));
        datas.add(new CommonListInfo("微信公众号", "哈财金融"));
        //        datas.add(new CommonListInfo("清理缓存", "215k"));
        datas.add(new CommonListInfo("当前版本", "" + CommonUtil.getAppVersionName(mContext)));

        moreRecycleAdapter = new MoreRecycleAdapter<CommonListInfo>(mContext, datas, R.layout
                .account_list_rv_item);
        more_rv.setLayoutManager(new LinearLayoutManager(mContext));
        moreRecycleAdapter.setOnItemClickLitener(new BaseRecylerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        Intent helpintent = new Intent(mContext, AboutUsWebAcitivity.class);
                        helpintent.putExtra(Constant.WEBVIEW_AGREEMENT_TITLE, "关于我们");
                        CommonUtil.gotoActivity(mContext, helpintent);
                        break;
                    case 1:
                        Intent intent = new Intent(mContext, HelpWebAcitivity.class);
                        intent.putExtra(Constant.WEBVIEW_AGREEMENT_TITLE, "帮助中心");
                        CommonUtil.gotoActivity(mContext, intent);
                        break;
                }

            }
        });
        more_rv.setAdapter(moreRecycleAdapter);
    }

    @Override
    protected void initTitle() {
        title_left_back.setVisibility(View.INVISIBLE);
        title_header_tv.setText(CommonUtil.getString(R.string.more));
    }

    private void initStatus() {
        //状态栏占用的兼容性
        if (Build.VERSION.SDK_INT >= 21) {
            View statusView = new View(getActivity());
            statusView.setBackgroundColor(CommonUtil.getColor(R.color.bt_start_color));
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, CommonUtil.getStatusBarHeight());
            fragment_more_ll.addView(statusView, 0, lp);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_sevice_phone_ll:
                callPhoneStart();
                break;
        }
    }

    private void callPhoneStart() {
        alterDialogUtils = new AlterDialogUtils(mContext, R.style.payment_dialog_notice);
        alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen.x290));
        alterDialogUtils.setOneOrTwoBtn(false);
        alterDialogUtils.setMessage("是否拨打客服热线\n" + CommonUtil.getString(R.string
                .service_phone_num));
        alterDialogUtils.setTwoCancelBtn("否", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterDialogUtils.dismiss();
            }
        });
        alterDialogUtils.setTwoConfirmBtn("是", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndPermission.with(getContext()).requestCode(200).permission(Manifest.permission
                        .CALL_PHONE).rationale(new RationaleListener() {

                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale
                            rationale) {
                        AndPermission.rationaleDialog(getContext(), rationale).show();
                    }
                }).callback(listener).start();
            }
        });
        alterDialogUtils.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (alterDialogUtils != null && alterDialogUtils.isShowing()) alterDialogUtils.dismiss();
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            LogUtil.i("HomeFragment permission PHONE onSucceed requestCode=" + requestCode);
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                if (AndPermission.hasPermission(getContext(), Manifest.permission.CALL_PHONE)) {
                    LogUtil.i("HomeFragment permission PHONE success");
                    Intent intentPhone = new Intent();
                    intentPhone.setAction(Intent.ACTION_CALL);
                    intentPhone.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentPhone.setData(Uri.parse("tel:" + CommonUtil.getString(R.string
                            .service_phone_num)));
                    mContext.startActivity(intentPhone);
                    if (alterDialogUtils != null) alterDialogUtils.dismiss();
                } else {
                    ToastUtil.toast(mContext, "没有拨打电话的权限");
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            LogUtil.i("HomeFragment permission PHONE fialed requestCode=" + requestCode);
            // 是否有不再提示并拒绝的权限。
            if (requestCode == 200) {
                if (AndPermission.hasPermission(mContext, Manifest.permission.CALL_PHONE)) {
                    Intent intentPhone = new Intent();
                    intentPhone.setAction(Intent.ACTION_CALL);
                    intentPhone.setData(Uri.parse("tel:" + CommonUtil.getString(R.string
                            .service_phone_num)));
                    mContext.startActivity(intentPhone);
                    if (alterDialogUtils != null) alterDialogUtils.dismiss();
                } else {
                    if (AndPermission.hasAlwaysDeniedPermission(mContext, deniedPermissions)) {
                        AndPermission.defaultSettingDialog(mContext, 400).show();
                    }
                }
            }
        }
    };
}
