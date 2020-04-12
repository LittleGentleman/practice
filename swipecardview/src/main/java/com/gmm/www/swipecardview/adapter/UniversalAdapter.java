package com.gmm.www.swipecardview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:gmm
 * @date:2020/4/9
 * @类说明: 封装万能适配器
 * 泛型T  是Bean类
 */
public abstract class UniversalAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<T> mDatas;
    private ViewGroup recyclerView;
    private OnItemClickListener mOnItemClickListener;

    public UniversalAdapter(Context context,List<T> data,int layoutId) {
        this.mContext = context;
        this.mDatas = data;
        this.mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = ViewHolder.get(mContext,null,viewGroup,mLayoutId);
        if (null == recyclerView) {
            this.recyclerView = viewGroup;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        setListener(viewHolder,i);
        convert(viewHolder,mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    //将数据 显示在 ViewHolder 上
    public abstract void convert(ViewHolder holder,T data);

    public void setListener(final ViewHolder holder, final int position) {
        if (isEnabled(getItemViewType(position))) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(recyclerView,v,mDatas.get(position),position);
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemClickListener != null) {
                        int position = getPosition(holder);
                        return mOnItemClickListener.onItemLongClick(recyclerView,v,mDatas.get(position),position);
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected int getPosition(RecyclerView.ViewHolder holder) {
        return holder.getAdapterPosition();
    }

    public void setData(List<T> list) {
        if (null != mDatas) {
            if (null != list) {
                ArrayList temp = new ArrayList();
                temp.addAll(list);
                mDatas.clear();
                mDatas.addAll(temp);
            } else {
                mDatas.clear();
            }
        } else {
            this.mDatas = list;
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (null != mDatas && mDatas.size() > position && position > -1) {
            mDatas.remove(position);
            notifyDataSetChanged();
        }
    }

    public void addDatas(List<T> list) {
        if (null != list) {
            ArrayList temp = new ArrayList();
            temp.addAll(list);
            if (null != mDatas) {
                mDatas.addAll(temp);
            } else {
                mDatas = temp;
            }
            notifyDataSetChanged();
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public T getItem(int position) {
        return position > -1 && null != mDatas && position < mDatas.size() ? mDatas.get(position) : null;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public UniversalAdapter setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }


}
