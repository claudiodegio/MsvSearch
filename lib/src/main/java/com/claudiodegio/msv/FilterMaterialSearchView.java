package com.claudiodegio.msv;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Filterable;
import android.widget.TextView;

import com.claudiodegio.msv.adapter.FilterRvAdapter;
import com.claudiodegio.msv.adapter.SelectFilterRvAdapter;
import com.claudiodegio.msv.model.BaseElement;
import com.claudiodegio.msv.model.Filter;
import com.claudiodegio.msv.model.Section;
import com.claudiodegio.msv.recycleview.RecyclerItemClickListener;

import java.util.List;

public class FilterMaterialSearchView extends BaseMaterialSearchView implements RecyclerItemClickListener.OnItemClickListener {

    private RecyclerView mRvFilter;
    private RecyclerView mRvSelectFilter;
    private SelectFilterRvAdapter mSelectFilterRvAdapter;
    private FilterRvAdapter mFilterRvAdapter;
    private OnFilterViewListener mOnFilterViewListener;
    private TextView mTvNoFilter;
    private boolean isContainFilter;

    public FilterMaterialSearchView(Context context) {
        super(context, null);
    }

    public FilterMaterialSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterMaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(attrs, defStyleAttr);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.msv_filter;
    }

    public void addSection(Section section) {
        mSelectFilterRvAdapter.addElement(section);
    }

    public void addFilter(Filter filter) {
        mSelectFilterRvAdapter.addElement(filter);
    }

    public void swapList(List<BaseElement> list) {
        mSelectFilterRvAdapter.swapList(list);
    }

    public List<Filter> getFilter(){
        return mFilterRvAdapter.getFilters();
    }

    public String getQuery(){
        return mETSearchText.getText().toString();
    }

    private void initView(){
        mRvFilter = (RecyclerView) findViewById(R.id.rv_filter);
        mRvSelectFilter = (RecyclerView) findViewById(R.id.rv_select_filter);
        mTvNoFilter = (TextView)  findViewById(R.id.tv_no_filter);

        mSelectFilterRvAdapter = new SelectFilterRvAdapter(getContext(), isContainFilter);
        mRvSelectFilter.setHasFixedSize(false);
        mRvSelectFilter.setAdapter(mSelectFilterRvAdapter);
        mRvSelectFilter.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));

        mFilterRvAdapter = new FilterRvAdapter(getContext());
        mRvFilter.setHasFixedSize(false);
        mRvFilter.setAdapter(mFilterRvAdapter);
        mRvFilter.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));

        // Griglia
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
        mRvFilter.setLayoutManager(gridLayoutManager);
        mFilterRvAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (mFilterRvAdapter.getItemCount() == 0){
                    mTvNoFilter.setVisibility(VISIBLE);
                } else {
                    mTvNoFilter.setVisibility(GONE);
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                if (mFilterRvAdapter.getItemCount() == 0){
                    mTvNoFilter.setVisibility(VISIBLE);
                } else {
                    mTvNoFilter.setVisibility(GONE);
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                if (mFilterRvAdapter.getItemCount() == 0){
                    mTvNoFilter.setVisibility(VISIBLE);
                } else {
                    mTvNoFilter.setVisibility(GONE);
                }
            }
        });
    }

    @Override
    public void showSearch(boolean animate) {
        super.showSearch(animate);
        showSelectFilter();
        mFilterRvAdapter.clear();
    }

    @Override
    public void closeSearch() {
        super.closeSearch();
        mFilterRvAdapter.clear();
    }

    private void showSelectFilter(){
        mRvSelectFilter.setVisibility(VISIBLE);
    }

    private void hideSelectFilter(){
        mRvSelectFilter.setVisibility(GONE);
    }

    public boolean isFilterVisible(){
        return mRvFilter.getVisibility() == VISIBLE;
    }

    public int getFilterHeight(){
       return mRvFilter.getHeight();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        Log.d(TAG, "onEditorAction: " + actionId);

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideSelectFilter();
            return super.onEditorAction(textView, actionId, keyEvent);
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view == mETSearchText) {
            if (mRvSelectFilter.getVisibility() == GONE) {
                showSelectFilter();
                hideKeyboard(mETSearchText);
                return;
            }
        }
        super.onClick(view);
    }

    @Override
    public void onItemClick(RecyclerView rv, View view, int position) {
        Log.d(TAG, "onItemClick: position:" + position);

        if (rv == mRvSelectFilter) {
            BaseElement element = mSelectFilterRvAdapter.getItem(position);

            if (element instanceof Filter) {
                mFilterRvAdapter.addFilter((Filter) element);
                hideSelectFilter();
                hideKeyboard(mETSearchText);
                mETSearchText.setText(null);

                if (mOnFilterViewListener != null) {
                    mOnFilterViewListener.onFilterAdded((Filter) element);
                }
            }
        } else {
            final Filter filter = mFilterRvAdapter.removeFilter(position);


            if (mFilterRvAdapter.getItemCount() == 0) {
                showSelectFilter();
            }

            if (mOnFilterViewListener != null) {
                mOnFilterViewListener.onFilterRemoved((Filter) filter);
            }
        }

        if (mOnFilterViewListener != null) {
            mOnFilterViewListener.onFilterChanged(mFilterRvAdapter.getFilters());
        }
    }

    private void startFilter(String text) {
        ((Filterable) mSelectFilterRvAdapter).getFilter().filter(text);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        super.onTextChanged(charSequence, i, i1, i2);

        startFilter(charSequence.toString());
    }

    public void setOnFilterViewListener(OnFilterViewListener mOnFilterViewListener) {
        this.mOnFilterViewListener = mOnFilterViewListener;
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof FilterMSVSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        FilterMSVSavedState mSavedState = (FilterMSVSavedState) state;

        super.onRestoreInstanceState(mSavedState.getSuperState());

        if (mSavedState.isFilterSelectOpen) {
            showSelectFilter();
        } else {
            hideSelectFilter();
        }

        mFilterRvAdapter.setFilters(mSavedState.filters);
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        FilterMSVSavedState savedState = new FilterMSVSavedState(super.onSaveInstanceState());

        savedState.isFilterSelectOpen = mRvSelectFilter.getVisibility() == VISIBLE;
        savedState.filters = mFilterRvAdapter.getFilters();

        return savedState;
    }

    class FilterMSVSavedState extends View.BaseSavedState {

        public boolean isFilterSelectOpen;
        public List<Filter> filters;

        public FilterMSVSavedState(Parcelable superState) {
            super(superState);
        }
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Msv, defStyleAttr, 0);

        if (a != null) {
            if (a.hasValue(R.styleable.Msv_msvIsContainEnabled)) {
                isContainFilter = a.getBoolean(R.styleable.Msv_msvIsContainEnabled, false);
            }

            a.recycle();
        }
    }
}
