package com.claudiodegio.msv;

public interface OnSearchViewListener {

    void onSearchViewShown();

    void onSearchViewClosed();

    boolean onQueryTextSubmit(String query);

    void onQueryTextChange(String newText);
}
