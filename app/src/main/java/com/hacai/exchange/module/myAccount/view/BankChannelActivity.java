package com.hacai.exchange.module.myAccount.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.BankChannelInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.BankChannelRecAdapter;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/12/20.
 */

public class BankChannelActivity extends BaseActivity implements MyAccountInterface.IMyAccountView {

    private static final int BANK_CHANNEL_TAG = 1;
    private TextView title_header_tv;
    private RecyclerView bank_channel_select_rv;
    private BankChannelRecAdapter bankChannelRecAdapter;
    private MyAccountPresenter myAccountPresenter;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        bank_channel_select_rv = (RecyclerView) findViewById(R.id.bank_channel_select_rv);
    }

    @Override
    protected void initData() {
        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.attachView(this);
        getBankCardChannelList(true);
    }

    private void getBankCardChannelList(boolean b) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        myAccountPresenter.getBankChannelList(baseMaps, BANK_CHANNEL_TAG, b);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.open_payment_bank_channel));
        initRecyclerView();
    }

    private List<BankChannelInfo.ObjBean> datas = new ArrayList<>();

    private void initRecyclerView() {
        bankChannelRecAdapter = new BankChannelRecAdapter<BankChannelInfo.ObjBean>(mContext,
                datas, R.layout.bank_channel_select_item);
        bank_channel_select_rv.setLayoutManager(new LinearLayoutManager(mContext));
        bank_channel_select_rv.setAdapter(bankChannelRecAdapter);
        bankChannelRecAdapter.setOnItemClickLitener(new BaseRecylerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, datas.get(position).getName());
                intent.putExtra(Constant.START_ACTIVITY_STRING_2, datas.get(position).getId());
                intent.putExtra(Constant.START_ACTIVITY_STRING_3, datas.get(position).getLogo());
                setResult(RechargeActivity.CHANNEL_ACTIVITY_RESULT_CODE, intent);
                AppManager.getInstance().removeCurrent();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_channel_select;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case BANK_CHANNEL_TAG:
                List<BankChannelInfo.ObjBean> bankChannelInfoObj = null;
                try {
                    BankChannelInfo bankChannelInfo = (BankChannelInfo) response;
                    bankChannelInfoObj = bankChannelInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (bankChannelInfoObj == null) {
                    toast("银行列表信息返回为空");
                    return;
                }
                datas.addAll(bankChannelInfoObj);
                bankChannelRecAdapter.notifyDataSetChanged();
                break;
        }
    }
}
