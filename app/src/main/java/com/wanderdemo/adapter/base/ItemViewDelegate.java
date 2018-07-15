package com.wanderdemo.adapter.base;


/**
 * Created by Chaitanya kumar.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
