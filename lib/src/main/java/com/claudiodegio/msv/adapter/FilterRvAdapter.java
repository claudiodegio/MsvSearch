package com.claudiodegio.msv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.claudiodegio.msv.R;
import com.claudiodegio.msv.model.Filter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FilterRvAdapter extends RecyclerView.Adapter<FilterRvAdapter.BaseViewHolder> {


    private Context mCtx;
    private LayoutInflater mInflater;
    private List<Filter> mList;

    public FilterRvAdapter(Context context){
        this.mCtx = context;
        this.mInflater = LayoutInflater.from(context);
        mList = new ArrayList<>();
        setHasStableIds(false);
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        BaseViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = mInflater.inflate(R.layout.msv_filter_iv, parent, false);
                holder = new IvViewHolder(view);
                break;
            case 1:
                view = mInflater.inflate(R.layout.msv_filter_civ, parent, false);
                holder = new CivViewHolder(view);
                break;
        }

        return holder;
    }

    public void addFilter(Filter mFilter) {

        for (int i = 0; i < mList.size(); ++i) {
            Filter filter = mList.get(i);

            if (filter.getType() == mFilter.getType()) {
                mList.set(i, mFilter);
                notifyItemChanged(i);
                return;
            }
        }

        mList.add(mFilter);
        notifyItemInserted(mList.size()-1);
    }

    public void clear(){
        mList.clear();
        notifyDataSetChanged();
    }

    public Filter getItem(int position) {
        return mList.get(position);
    }

    public Filter removeFilter(int position) {
        Filter filter = mList.remove(position);
        notifyItemRemoved(position);
        return filter;
    }

    public void setFilters(List<Filter> list){
        mList = list;
        notifyDataSetChanged();
    }

    public List<Filter> getFilters(){
        return mList;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Filter filter = mList.get(position);

        if (filter.getIconBgColor() == View.NO_ID){
            return 0;
        }
        return 1;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        public BaseViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bind(Filter filter) {
            mTextView.setText(filter.getName());
        }
    }

    class IvViewHolder extends BaseViewHolder {

        ImageView mIv;
        public IvViewHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_icon);
        }

        @Override
        public void bind(Filter filter) {
            super.bind(filter);

           if (filter.hasIconRefId()) {
               mIv.setImageResource(filter.getIconRefId());
           } else {
               mIv.setImageDrawable(filter.getIconDrawable());
           }
        }
    }

    class CivViewHolder extends BaseViewHolder {

        CircleImageView mCIV;

        public CivViewHolder(View itemView) {
            super(itemView);
            mCIV = (CircleImageView) itemView.findViewById(R.id.civ_icon);
        }

        @Override
        public void bind(Filter filter) {
            super.bind(filter);
            if (filter.hasIconRefId()) {
                mCIV.setImageResource(filter.getIconRefId());
            } else {
                mCIV.setImageDrawable(filter.getIconDrawable());
            }
            mCIV.setFillColor(filter.getIconBgColor());
        }
    }
}
