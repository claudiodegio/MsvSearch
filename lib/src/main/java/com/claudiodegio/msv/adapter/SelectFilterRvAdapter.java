package com.claudiodegio.msv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.claudiodegio.msv.R;
import com.claudiodegio.msv.model.BaseElement;
import com.claudiodegio.msv.model.Filter;
import com.claudiodegio.msv.model.Section;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectFilterRvAdapter extends RecyclerView.Adapter<SelectFilterRvAdapter.BaseViewHolder> implements Filterable {

    private List<BaseElement> mList;
    private List<BaseElement> mListFiltered;
    private boolean isContain;

    private Context mCtx;
    private LayoutInflater mInflater;

    public SelectFilterRvAdapter(Context context, boolean isContain){
        this.mCtx = context;
        this.mInflater = LayoutInflater.from(context);
        mList = new ArrayList<>();
        mListFiltered = mList;
        setHasStableIds(false);
        this.isContain = isContain;
    }

    public void addElement(BaseElement element) {
        mList.add(element);
        notifyDataSetChanged();
    }


    public void swapList(List<BaseElement> list) {
        mList = list;
        mListFiltered = list;
        notifyDataSetChanged();
    }

    public BaseElement getItem(int position) {
        return mListFiltered.get(position);
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        BaseViewHolder holder = null;

        switch (viewType) {
            case 0:
                view = mInflater.inflate(R.layout.msv_select_filter_section, parent, false);
                holder = new SectionViewHolder(view);
                break;

            case 1:
                view = mInflater.inflate(R.layout.msv_select_filter_item, parent, false);
                holder = new ItemViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final BaseElement element = mListFiltered.get(position);

        holder.bind(element);
    }

    @Override
    public int getItemCount() {
        if (mListFiltered == null) {
            return 0;
        }
        return mListFiltered.size();
    }

    @Override
    public int getItemViewType(int position) {

        BaseElement baseElement = mListFiltered.get(position);

        if (baseElement instanceof Section) {
            return 0;
        }

        return 1;
    }

    @Override
    public android.widget.Filter getFilter() {
        return new SelectFilter();
    }


    class SelectFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (mList == null) {
                return null;
            }

            FilterResults result = new FilterResults();

            List<BaseElement> filteredList;

            if (!TextUtils.isEmpty(charSequence)) {
                Section sezione = null;
                filteredList = new ArrayList<>();
                for (BaseElement filter : mList) {

                    if (filter instanceof Filter) {
                        final String charSequenceLowerCase = charSequence.toString().toLowerCase();
                        final String filterNameLowerCase = filter.getName().toLowerCase();

                        if ((!isContain && filterNameLowerCase.startsWith(charSequenceLowerCase))
                                || isContain && filterNameLowerCase.contains(charSequenceLowerCase)) {
                            if (sezione != null) {
                                filteredList.add(sezione);
                                sezione = null;
                            }
                            filteredList.add(filter);
                        }
                    } else {
                        sezione = (Section) filter;
                    }
                }
            } else {
                filteredList = mList;
            }

            result.values = filteredList;

            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mListFiltered = (List<BaseElement>) filterResults.values;

            notifyDataSetChanged();
        }
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(BaseElement element) {}
    }

    class ItemViewHolder extends BaseViewHolder {

        TextView mTextView;
        CircleImageView mCiv;
        ImageView mIv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_name);
            mCiv =  (CircleImageView)itemView.findViewById(R.id.civ_icon);
            mIv = (ImageView)itemView.findViewById(R.id.iv_icon);
        }

        @Override
        public void bind(BaseElement element) {
            mTextView.setText(element.getName());

            Filter filter = (Filter)element;

            // Colore
            int color = filter.getIconBgColor();

            if (color != View.NO_ID) {
               // mCiv.setBackgroundColor();
                mIv.setVisibility(View.GONE);
                mCiv.setVisibility(View.VISIBLE);

                mCiv.setFillColor(color);
                if (filter.hasIconRefId()) {
                    mCiv.setImageResource(filter.getIconRefId());
                } else {
                    mCiv.setImageDrawable(filter.getIconDrawable());
                }
            } else {
                mIv.setVisibility(View.VISIBLE);
                mCiv.setVisibility(View.GONE);

                if (filter.hasIconRefId()) {
                    mIv.setImageResource(filter.getIconRefId());
                } else {
                    mIv.setImageDrawable(filter.getIconDrawable());
                }
            }
        }
    }


    class SectionViewHolder extends BaseViewHolder {

        TextView mTextView;
        public SectionViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        @Override
        public void bind(BaseElement element) {
            mTextView.setText(element.getName());

            // Set del icona
        }
    }
}
