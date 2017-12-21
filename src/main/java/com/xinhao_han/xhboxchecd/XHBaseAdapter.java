package com.xinhao_han.xhboxchecd;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by 14178 on 2017/12/12.
 */

public abstract class XHBaseAdapter<T> extends BaseAdapter {

    private View view;
    private Context context;
    private ArrayList<T> arrayList;
    private T t;
    private int layoutId;
    private boolean isFor;


    public XHBaseAdapter(int layoutId, Context context, ArrayList<T> arrayList, boolean isFor) {
        this.context = context;
        this.view = XHUIUtils.getView(layoutId);
        this.arrayList = arrayList;
        this.layoutId = layoutId;
        this.isFor = isFor;
    }

    public XHBaseAdapter(int layoutId, Context context, T t, boolean isFor, String str) {
        this.context = context;
        this.view = XHUIUtils.getView(layoutId);
        this.t = t;
        this.layoutId = layoutId;
        this.isFor = isFor;
    }


    @Override
    public int getCount() {

        if (arrayList == null)
            return 0;

        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        if (arrayList == null)
            return null;

        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;

        if (view == null) {
            viewHolder = new ViewHolder(XHUIUtils.getView(layoutId));
            viewHolder.view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        itemPosition(i, t, (isFor) ? arrayList.get(i) : null, viewHolder);
        return viewHolder.view;
    }


    public abstract void itemPosition(int position, T t, T arrayItem, ViewHolder viewHolder);


    public static class ViewHolder {
        private View view;


        public ViewHolder(View view) {

            this.view = view;


        }

        public View findViewById(int id) {

            return view.findViewById(id);
        }


        /**
         * 设置文本
         *
         * @param text
         * @param id
         */
        public void setText(String text, int id) {
            ((TextView) view.findViewById(id)).setText(text);
        }

        /**
         * 设置图片
         *
         * @param bitmap
         * @param id
         */
        public void setImage(Bitmap bitmap, int id) {
            ((ImageView) view.findViewById(id)).setImageBitmap(bitmap);
        }

        /**
         * 设置图片
         *
         * @param imageid
         * @param id
         */
        public void setImage(int imageid, int id) {

            ((ImageView) view.findViewById(id)).setImageResource(imageid);

        }

        /**
         * 获取View
         *
         * @return
         */

        public View getViewHolderView() {
            return view;
        }

        /**
         * 设置点击事件
         */

        public void setOnClickListener(View.OnClickListener l) {
            view.setOnClickListener(l);
        }
    }

}
