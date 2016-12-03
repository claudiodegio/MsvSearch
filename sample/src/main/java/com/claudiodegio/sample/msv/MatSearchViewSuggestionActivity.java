package com.claudiodegio.sample.msv;


import android.widget.Toast;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.claudiodegio.msv.SuggestionMaterialSearchView;

public class MatSearchViewSuggestionActivity extends BaseMatSearchViewActivity implements OnSearchViewListener {


    @Override
    protected void initCustom() {
        super.initCustom();

        String[] arrays = getResources().getStringArray(R.array.query_suggestions);

        SuggestionMaterialSearchView cast = (SuggestionMaterialSearchView)mSearchView;

        cast.setSuggestion(arrays);

        mSearchView.setOnSearchViewListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_msv_suggestions;
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
        Toast.makeText(this, "onQueryTextChange: " + newText, Toast.LENGTH_SHORT).show();
    }
}
