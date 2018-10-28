package com.xiang.circlemenulayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by carl on 2018/9/11 0011.
 */

public class CircleMenuAdapter extends BaseAdapter {

    private List<MenuItemBean> list;

    public CircleMenuAdapter(List<MenuItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public MenuItemBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View itemView = mInflater.inflate(R.layout.circle_menu_item, parent, false);
        initMenuItem(itemView, position);
        return itemView;
    }

    private void initMenuItem(View itemView, int position) {
        MenuItemBean item = getItem(position);
        ImageView iv = (ImageView) itemView.findViewById(R.id.id_circle_menu_item_image);
        TextView tv = (TextView) itemView.findViewById(R.id.id_circle_menu_item_text);
        iv.setImageResource(item.imageId);
        tv.setText(item.title);
    }
}
