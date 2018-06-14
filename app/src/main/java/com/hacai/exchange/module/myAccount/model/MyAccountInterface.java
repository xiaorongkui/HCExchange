package com.hacai.exchange.module.myAccount.model;

import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.ui.ui.ViewInterface;

import java.util.Map;

/**
 * Created by Rongkui.xiao on 2017/4/27.
 *
 * @description
 */

public class MyAccountInterface {
    /**
     * 登录的View接口
     */
    public interface IMyAccountView extends ViewInterface<Object> {

    }

/*====================连接层===========================*/

    public interface IMyAccountPresenter {
        void getAccountInformation(Map<String, Object> maps, int tag, boolean isShowProgress);

        void getInvestmentRecode(Map<String, Object> maps, int tag, boolean isShowProgress);

        void recharge(Map<String, Object> maps, int tag, boolean isShowProgress);


        void getBankInfo(Map<String, Object> maps, int tag, boolean isShowProgress);

        void getBankChannelList(Map<String, Object> maps, int tag, boolean isShowProgress);

        void setExchangePwd(Map<String, Object> maps, int tag, boolean isShowProgress);

        void reviseExchangePwd(Map<String, Object> maps, int tag, boolean isShowProgress);

        void forgetExchangePwd(Map<String, Object> maps, int tag, boolean isShowProgress);

        void checkWithDraw(Map<String, Object> maps, int tag, boolean isShowProgress);

        void calculateWithDrawMoney(Map<String, Object> maps, int tag, boolean isShowProgress);

        void submitWithDraw(Map<String, Object> maps, int tag, boolean isShowProgress);

        void getCapitalRecode(Map<String, Object> maps, int tag, boolean isShowProgress);

        void getRiskLevel(Map<String, Object> maps, int tag, boolean isShowProgress);//获取风险评估等级
    }

/*====================业务层===========================*/

    /**
     * 登录的接口
     */
    interface IMyAccountModel {

        void getAccountInformation(Map<String, Object> maps, final HttpOnNextListener listener,
                                   boolean isShowProgress);

        void getInvestmentRecode(Map<String, Object> maps, final HttpOnNextListener listener,
                                 boolean isShowProgress);

        void recharge(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);

        void getBankInfo(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);

        void getBankChannelList(Map<String, Object> maps, final HttpOnNextListener listener,
                                boolean isShowProgress);

        void setExchangePwd(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);

        void reviseExchangePwd(Map<String, Object> maps, final HttpOnNextListener listener,
                               boolean isShowProgress);

        void forgetExchangePwd(Map<String, Object> maps, final HttpOnNextListener listener,
                               boolean isShowProgress);


        void checkWithDraw(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);

        void calculateWithDrawMoney(Map<String, Object> maps, final HttpOnNextListener listener,
                                    boolean isShowProgress);

        void submitWithDraw(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);

        void getCapitalRecode(Map<String, Object> maps, final HttpOnNextListener listener,
                              boolean isShowProgress);

        void getRiskLevel(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);

    }

}
