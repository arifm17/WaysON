package com.dnakreatif.wayson.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnakreatif.wayson.DrawerItem;
import com.dnakreatif.wayson.R;

import java.util.List;

/**
 * Created by arifraqilla on 5/1/2015.
 */
public class CustomListAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public CustomListAdapter(Context context, int layoutResourceID,
                               List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ItemHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.ItemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);
            itemHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            view.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        itemHolder.icon.setImageDrawable(view.getResources()
                .getDrawable(dItem.getImgResID()));
        itemHolder.ItemName.setText(dItem.getItemName());

        return view;
    }

    private static class ItemHolder {
        TextView ItemName;
        ImageView icon;
    }
}
