package com.claudiodegio.msv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.claudiodegio.msv.adapter.SearchSuggestRvAdapter;
import com.claudiodegio.msv.recycleview.RecyclerItemClickListener;

import java.util.Arrays;
import java.util.List;

public class SuggestionMaterialSearchView extends BaseMaterialSearchView implements RecyclerItemClickListener.OnItemClickListener, Filter.FilterListener {

    private RecyclerView mRvSuggestion;
    private View mVOverlay;
    private RecyclerView.Adapter mSuggestsAdapter;

    public SuggestionMaterialSearchView(Context context) {
        this(context, null);
    }

    public SuggestionMaterialSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuggestionMaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }


    private void initView(){
        mRvSuggestion = (RecyclerView) findViewById(R.id.rv_suggestions);
        mVOverlay = findViewById(R.id.v_overlay);
        mVOverlay.setOnClickListener(this);
        mRvSuggestion.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.msv_suggestions;
    }

    @Override
    public void showSearch(boolean animate) {
        super.showSearch(animate);
        showKeyboard(mETSearchText);
        mVOverlay.setVisibility(VISIBLE);
    }

    public void setSuggestion(String[] suggestions) {
        setSuggestion(suggestions, false);
    }

    public void setSuggestion(String[] suggestions, Boolean isContain) {
        if (suggestions != null) {
            setSuggestion(Arrays.asList(suggestions), isContain);
        }
    }

    public void setSuggestion(List<String> suggestions) {
      setSuggestion(suggestions, false);
    }

    public void setSuggestion(List<String> suggestions, Boolean isContain) {
        if (suggestions != null && !suggestions.isEmpty()) {
            final SearchSuggestRvAdapter adapter = new SearchSuggestRvAdapter(getContext(), suggestions, isContain);

            setSuggestAdapter(adapter);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        super.onTextChanged(charSequence, i, i1, i2);

        if (!mIgnoreNextTextChange) {
            mVOverlay.setVisibility(VISIBLE);
            startFilter(mETSearchText.getText().toString());
        }
    }

    public void setSuggestAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter) {
        mSuggestsAdapter = adapter;
        mRvSuggestion.setAdapter(adapter);
        mVOverlay.setVisibility(VISIBLE);
    }

    public void showSuggestion(){
        mRvSuggestion.setVisibility(VISIBLE);
    }

    public void hideSuggestion(){
        mRvSuggestion.setVisibility(GONE);
    }

    private void startFilter(String text) {
        if (mSuggestsAdapter != null && mSuggestsAdapter instanceof Filterable) {
            ((Filterable) mSuggestsAdapter).getFilter().filter(text, this);
        }
    }

    @Override
    public void onItemClick(RecyclerView rv, View view, int position) {
        Log.d(TAG, "onItemClick: " + position);

        mIgnoreNextTextChange = true;

        if (mSuggestsAdapter instanceof SearchSuggestRvAdapter) {

            String suggestion = ((SearchSuggestRvAdapter) mSuggestsAdapter).getItem(position);
            setQuery(suggestion, true);
            if (mETSearchText.getText().length() > 0) {
                mETSearchText.setSelection(mETSearchText.getText().toString().length());
            }
        }

        mIgnoreNextTextChange = false;
        hideSuggestion();
        mVOverlay.setVisibility(GONE);
    }

    @Override
    public void onFilterComplete(int count) {
        if (count > 0) {
            showSuggestion();
        } else {
            hideSuggestion();
        }
    }

    @Override
    public void onClick(View view) {

        super.onClick(view);
        int i = view.getId();
        if (i == R.id.bt_back) {
            closeSearch();
        } else if (i == R.id.ed_search_text) {
            mVOverlay.setVisibility(VISIBLE);
        } else if (i == R.id.v_overlay) {
            closeSearch();
        }
    }
}
