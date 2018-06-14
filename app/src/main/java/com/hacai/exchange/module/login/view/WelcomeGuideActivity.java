package com.hacai.exchange.module.login.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hacai.exchange.MainActivity;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.SPHelper;

/**
 * Created by lenovo on 2017/12/7.
 */

public class WelcomeGuideActivity extends BaseActivity {
    private int[] imageDatas = new int[]{R.mipmap.feature_1, R.mipmap.feature_2, R.mipmap
            .feature_3};
    private ViewPager welcome_auto_ll;

    @Override
    protected void initView() {
        welcome_auto_ll = (ViewPager) findViewById(R.id.welcome_auto_ll);
    }

    @Override
    protected void initData() {
        SPHelper.getInstance().set(Constant.IS_FIRST_OPEN, true);
        CommonUtil.setScrollerTime(mContext, 100, welcome_auto_ll);
        welcome_auto_ll.setAdapter(adapter);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_guide;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    private PagerAdapter adapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return imageDatas == null ? 0 : imageDatas.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == imageDatas.length - 1) {
                RelativeLayout relView = new RelativeLayout(mContext);
                RelativeLayout.LayoutParams lpImage = new RelativeLayout.LayoutParams(ViewGroup
                        .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ImageView iv = new ImageView(mContext);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageResource(imageDatas[position]);
                relView.addView(iv, 0, lpImage);

                TextView button = new TextView(mContext);
                button.setText(CommonUtil.getString(R.string.splash_experience_now));
                button.setTextColor(CommonUtil.getColor(R.color.bt_end_color));
                button.setTextSize(18);
                button.setBackground(CommonUtil.getDrawable(R.drawable.welcome_bt_shape));
                button.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams lpButton = new RelativeLayout.LayoutParams((int)
                        CommonUtil.getDimen(R.dimen.x150), (int) CommonUtil.getDimen(R.dimen.y35));
                lpButton.bottomMargin = (int) CommonUtil.getDimen(R.dimen.y88);
                lpButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lpButton.addRule(RelativeLayout.CENTER_HORIZONTAL);
                relView.addView(button, 1, lpButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtil.gotoActivity(mContext, MainActivity.class);
                        AppManager.getInstance().removeCurrent();
                    }
                });
                container.addView(relView);
                return relView;
            } else {
                ImageView iv = new ImageView(mContext);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageResource(imageDatas[position]);
                container.addView(iv);
                return iv;
            }
        }
    };
}
