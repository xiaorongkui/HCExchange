package com.hacai.exchange.module.login.model;

import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.ui.ui.ViewInterface;

import java.util.Map;

/**
 * Created by Rongkui.xiao on 2017/4/27.
 *
 * @description
 */

public class LoginInterface {
    /**
     * 登录的View接口
     */
    public interface ILoginView extends ViewInterface<Object> {
        //清除密码框

        void clearPassword();

    }

      /*====================连接层===========================*/

    public interface ILoginPresenter {
        void login(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void getLoginPwdCode(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void checkForgetLoginCode(Map<String, Object> maps, int tag, boolean isShowProgress);
        //桥梁就是登录了

        //重置登录密码
        void getRegisterPwdCode(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void resetPwd(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void register(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void loginOut(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了

        void getDrawWithCode(Map<String, Object> maps, int tag, boolean isShowProgress);////获取提现验证码

        void modifyLoginPwd(Map<String, Object> maps, int tag, boolean isShowProgress);////获取提现验证码

        void getExchangePwdCode(Map<String, Object> maps, int tag, boolean isShowProgress);

        void setForgetExchangePwd(Map<String, Object> maps, int tag, boolean isShowProgress);

        void checkUpdate(Map<String, Object> maps, int tag, boolean isShowProgress);
    }

/*====================业务层===========================*/

    /**
     * 登录的接口
     */
    interface ILoginModel {
        /*========登录=========*/
        void saveLoginReponse(Object o);//保存登录信息

        void login(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);//登录请求

        //忘记登录密码验证码
        void getLoginPwdCode(Map<String, Object> maps, final HttpOnNextListener listener, boolean
                isShowProgress);//登录请求

        void checkForgetLoginCode(Map<String, Object> maps, final HttpOnNextListener listener,
                                  boolean isShowProgress);//登录请求

        /*========忘记密码=========*/
        void resetPwd(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);//重置密码请求

        /*========注册=========*/

        void getRegisterPwdCode(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);//注册

        void register(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);//注册

        void loginOut(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);//注册

        void getDrawWithCode(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);//获取提现验证

        void modifyLoginPwd(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);//修改登录密码

        void getExchangePwdCode(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);//忘记交易密码

        void setForgetExchangePwd(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);

        void checkUpdate(Map<String, Object> maps, HttpOnNextListener listener, boolean
                isShowProgress);
    }

}
