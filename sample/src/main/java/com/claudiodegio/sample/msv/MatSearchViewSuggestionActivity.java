package com.claudiodegio.sample.msv;


import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.SuggestionMaterialSearchView;

public class MatSearchViewSuggestionActivity extends BaseMatSearchViewActivity implements BaseMaterialSearchView.OnSearchViewListener {


    @Override
    protected void initCustom() {
        super.initCustom();

        String[] arrays = getResources().getStringArray(R.array.query_suggestions);

      /*  SuggestionMaterialSearchView cast = LangUtil.cast(mSearchView);


        cast.setSuggestion(arrays);

       // cast.showSuggestion();

        mSearchView.setOnSearchViewListener(this);*/

    }

    @Override
    public int getLayoutId() {
        return R.layout.test_msv_suggestions;
    }

    @Override
    public void onSearchViewShown() {

    }

    @Override
    public void onSearchViewClosed() {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public void onQueryTextChange(String newText) {

    }
}
