package com.hacai.exchange.common;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hacai.exchange.HCExchangeApp;
import com.hacai.exchange.R;

/**
 */
public class CommonRecylerViewHolder extends RecyclerView.ViewHolder {

    //    public DisplayImageOptions options;
    private final SparseArray<View> mViews = new SparseArray<View>();
    private View mConvertView;

    public CommonRecylerViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
    }

    public View getHolderView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public ProgressBar getProgressBar(int viewId) {
        return (ProgressBar) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public LinearLayout getLinearLayout(int viewId) {
        return (LinearLayout) getView(viewId);
    }

    public RelativeLayout getRelativeLayout(int viewId) {
        return (RelativeLayout) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public RatingBar getRatingBar(int viewId) {
        return (RatingBar) getView(viewId);
    }

    public GridView getGridView(int viewId) {
        return (GridView) getView(viewId);
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommonRecylerViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public CommonRecylerViewHolder setTextColor(int viewId, int colorId) {
        TextView view = getView(viewId);
        view.setTextColor(colorId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public CommonRecylerViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public CommonRecylerViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public CommonRecylerViewHolder setImageByUrl(int viewId, String url) {
        ImageView img = getView(viewId);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(HCExchangeApp.getContext()).load(url).into(img);
        } else {
            img.setImageResource(R.mipmap.img_login_logo);
        }
        return this;
    }
//
//    /**
//     * 为ImageView设置图片     4
//     *
//     * @param viewId
//     * @return
//     */
//    public CommonRecylerViewHolder setImageByUrlHasTag(int viewId, final String url, DisplayImageOptions option) {
//        ImageView img = getView(viewId);
//        img.setTag(url);
//        ImageLoader.getInstance().displayImage(url, img, option, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                if (view.getTag().equals(url)) {
//                    ((ImageView) view).setImageBitmap(loadedImage);
//                }
//            }
//
//        });
//        return this;
//    }


}
