package com.claudiodegio.sample.msv;

import android.provider.SyncStateContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.FilterMaterialSearchView;
import com.claudiodegio.msv.model.MyFilter;
import com.claudiodegio.msv.model.Section;

import java.util.ArrayList;
import java.util.List;

public class MatSearchViewFilterActivity extends BaseMatSearchViewActivity implements BaseMaterialSearchView.OnSearchViewListener, FilterMaterialSearchView.OnFilterViewListener {

    @Override
    protected void initCustom() {
        super.initCustom();
        mSearchView.setOnSearchViewListener(this);

        FilterMaterialSearchView cast = (FilterMaterialSearchView)mSearchView;

        Section section = new Section("Animals");

        cast.addSection(section);

        MyFilter filter = new MyFilter(1, "Duck", 0, R.drawable.cat_duck_48dp, getResources().getColor(R.color.color_cat_duck));
        cast.addFilter(filter);

        filter = new MyFilter(1, "Elephant", 0, R.drawable.cat_elephant_48dp,getResources().getColor(R.color.color_cat_elephant));
        cast.addFilter(filter);

        filter = new MyFilter(1, "Frog", 0, R.drawable.cat_frog_48dp,getResources().getColor(R.color.color_cat_frog));
        cast.addFilter(filter);

        section = new Section("Outdoor");

        cast.addSection(section);

        filter = new MyFilter(2, "Forest", 1, R.drawable.cat_forest_48dp, getResources().getColor(R.color.color_cat_forest));
        cast.addFilter(filter);

        filter = new MyFilter(2, "Mountain", 1, R.drawable.cat_mountain_48dp,getResources().getColor(R.color.color_cat_mountain));
        cast.addFilter(filter);

        filter = new MyFilter(2, "Tent", 1, R.drawable.cat_tent_48dp,getResources().getColor(R.color.color_cat_tent));
        cast.addFilter(filter);

        cast.setOnFilterViewListener(this);
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

    /*
    public class SimpleRVAdapter extends RecyclerView.Adapter<BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder> {
        private List<String> dataSource;
        public SimpleRVAdapter(List<String> dataArgs){
            dataSource = dataArgs;
        }

        @Override
        public BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(BaseMatSearchViewActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
            BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder viewHolder = new BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder(view);
            return viewHolder;
        }

        public  class SimpleViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public SimpleViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }

        @Override
        public void onBindViewHolder(BaseMatSearchViewActivity.SimpleRVAdapter.SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSource.size();
        }
    }*/
}
