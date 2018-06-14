package com.hacai.exchange.module.more.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.CommonListInfo;
import com.hacai.exchange.common.CommonRecylerViewHolder;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class MoreRecycleAdapter<T> extends BaseRecylerAdapter {
    private Context mContext;
    private List<CommonListInfo> datas;

    public MoreRecycleAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = (List<CommonListInfo>) datas;
        this.mContext = context;
    }

    @Override
    public void convert(CommonRecylerViewHolder holder, int position) {
        TextView accoun_item_title_tv = holder.getTextView(R.id.accoun_item_title_tv);
        TextView accoun_item_content_tv = holder.getTextView(R.id.accoun_item_content_tv);
        View account_item_line = holder.getView(R.id.account_item_line);
        View account_list_item_view = holder.getView(R.id.account_list_item_view);
        View account_list_item_arrow_iv = holder.getView(R.id.account_list_item_arrow_iv);

        accoun_item_title_tv.setText(datas.get(position).getTitle());
        accoun_item_content_tv.setText(datas.get(position).getContent());

        if (position == 1 || position == 4) {
            account_item_line.setVisibility(View.INVISIBLE);
            account_list_item_view.setVisibility(View.VISIBLE);
        } else if (position == datas.size() - 1) {
            account_item_line.setVisibility(View.INVISIBLE);
            account_list_item_view.setVisibility(View.GONE);
        } else {
            account_item_line.setVisibility(View.VISIBLE);
            account_list_item_view.setVisibility(View.GONE);
        }
        if (position > 1) {
            account_list_item_arrow_iv.setVisibility(View.GONE);
        }
    }
}
