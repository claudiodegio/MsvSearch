package com.claudiodegio.sample.msv;

import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.FilterMaterialSearchView;
import com.claudiodegio.msv.model.MyFilter;
import com.claudiodegio.msv.model.Section;

import java.util.List;

public class MatSearchViewFilterActivity extends BaseMatSearchViewActivity implements BaseMaterialSearchView.OnSearchViewListener, FilterMaterialSearchView.OnFilterViewListener {


    @Override
    protected void initCustom() {
        super.initCustom();
        mSearchView.setOnSearchViewListener(this);

     /*   FilterMaterialSearchView cast = LangUtil.cast(mSearchView);

        Section section = new Section("Sezione 1");

        cast.addSection(section);

        MyFilter filter = new MyFilter(0, "Filtro 1", 0, SyncStateContract.Constants.Category.iconsIdToResId(0),Constants.Category.colors()[0]);
        cast.addFilter(filter);

       filter = new MyFilter(2, "Filtro 2", 0, Constants.Category.iconsIdToResId(1),Constants.Category.colors()[1]);
        cast.addFilter(filter);

        filter = new MyFilter(3, "Filtro 3",  0, Constants.Category.iconsIdToResId(2),Constants.Category.colors()[2]);
        cast.addFilter(filter);

        filter = new MyFilter(4, "Filtro 4",  0, Constants.Category.iconsIdToResId(3),Constants.Category.colors()[3]);
        cast.addFilter(filter);

        section = new Section("Sezione 2");
        cast.addSection(section);

        filter = new MyFilter(5, "Filtro 21", 0, R.drawable.ic_shopping_grey600_24dp, View.NO_ID);
        cast.addFilter(filter);

        filter = new MyFilter(6, "Filtro 22", 0, R.drawable.ic_shopping_grey600_24dp, View.NO_ID);
        cast.addFilter(filter);

        filter = new MyFilter(7, "Filtro 23", 0, R.drawable.ic_shopping_grey600_24dp, View.NO_ID);
        cast.addFilter(filter);


        cast.setOnFilterViewListener(this);*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_msv_filter;
    }

    @Override
    public void onSearchViewShown(){
    }

    @Override
    public void onSearchViewClosed() {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "onQueryTextSubmit:" + query, Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public void onQueryTextChange(String newText) {

    }

    @Override
    public void onFilterAdded(MyFilter filter) {

        Log.d("TAG", "onFilterAdded:" + filter.getName());

    }

    @Override
    public void onFilterRemoved(MyFilter filter) {
        Log.d("TAG", "onFilterRemoved:" + filter.getName());

    }

    @Override
    public void onFilterChanged(List<MyFilter> list) {
        Log.d("TAG", "onFilterChanged:" + list.size());

    }
}
