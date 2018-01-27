package com.claudiodegio.msv;

public interface OnSearchViewListener {

    /**
     * called when search view and keyboard is shown
     */
    void onSearchViewShown();

    /**
     * called when search view and keyboard is closed
     */
    void onSearchViewClosed();

    /**
     *
     * @param query String submitted to search view
     * @return boolean whether the text submit was handled or not
     */
    boolean onQueryTextSubmit(String query);

    /**
     * handles change of text in search view
     * @param newText current text in search view
     */
    void onQueryTextChange(String newText);
}
