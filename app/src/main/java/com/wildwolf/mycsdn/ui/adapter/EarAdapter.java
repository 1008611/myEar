package com.wildwolf.mycsdn.ui.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wildwolf.mycsdn.R;

import com.wildwolf.mycsdn.data.EarData;
import com.wildwolf.mycsdn.ui.adapter.baseadapter.BaseAdapter;
import com.wildwolf.mycsdn.ui.adapter.baseadapter.ViewHolder;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/11/24.
 */
public class EarAdapter extends BaseAdapter<EarData> {

    public EarAdapter(Context context, List<EarData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, EarData data) {
        holder.setText(R.id.csdn_item_name, data.getName());
        holder.setText(R.id.csdn_item_sub, data.getSubtype());

        SimpleDraweeView image = holder.getView(R.id.csdn_item_icon);
        image.setImageURI(Uri.parse(data.getImgUrl()));

    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_ear_layout;
    }
}