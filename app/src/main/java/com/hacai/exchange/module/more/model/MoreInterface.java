package com.hacai.exchange.module.more.model;

import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.ui.ui.ViewInterface;

import java.util.Map;

/**
 * Created by Rongkui.xiao on 2017/4/27.
 *
 * @description
 */

public class MoreInterface {
    /**
     * 登录的View接口
     */
    public abstract class IMoreView implements ViewInterface {
        //清除密码框

        public void clearPassword() {
        }


    }

/*====================连接层===========================*/

    public interface IMorePresenter {
        void login(Map<String, Object> maps, int tag, boolean isShowProgress);//桥梁就是登录了
    }

/*====================业务层===========================*/

    /**
     * 登录的接口
     */
    interface IMoreModel {
        /*========登录=========*/
        void saveLoginReponse(Object o);//保存登录信息

        void login(Map<String, Object> maps, final HttpOnNextListener listener, boolean isShowProgress);//登录请求

        /*========忘记密码=========*/
        void resetPwd(Map<String, Object> maps, HttpOnNextListener listener);//重置密码请求

        /*========注册=========*/

        void register(Map<String, Object> maps, HttpOnNextListener listener);//注册
    }

}
