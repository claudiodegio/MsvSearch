package com.claudiodegio.sample.msv;

import android.util.Log;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;


public class ThemedMatSearchViewActivity extends BaseMatSearchViewActivity implements OnSearchViewListener {


    @Override
    protected void initCustom() {
        mSearchView.setOnSearchViewListener(this);
    }

    public int getLayoutId() {
        return R.layout.test_msv_themed_simple;
    }

    @Override
    public void onSearchViewShown() {
        Log.d("TAG", "onSearchViewShown: ");
    }

    @Override
    public void onSearchViewClosed() {
        Log.d("TAG", "onSearchViewClosed: ");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("TAG", "onQueryTextSubmit: " + query);
        return true;
    }

    @Override
    public void onQueryTextChange(String newText) {
        Log.d("TAG", "onQueryTextChange: " + newText);

    }

}
