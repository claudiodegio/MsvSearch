package com.claudiodegio.sample.msv;

import android.util.Log;
import android.widget.Toast;

import com.claudiodegio.msv.BaseMaterialSearchView;


public class MatSearchViewActivity extends BaseMatSearchViewActivity implements BaseMaterialSearchView.OnSearchViewListener {


    @Override
    protected void initCustom() {
        mSearchView.setOnSearchViewListener(this);
    }

    public int getLayoutId() {
        return R.layout.test_msv_simple;
    }

    @Override
    public void onSearchViewShown() {

        Toast.makeText(this, "onSearchViewShown", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchViewClosed() {

        Toast.makeText(this, "onSearchViewClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "onQueryTextSubmit: " + query, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onQueryTextChange(String newText) {

    }
}
