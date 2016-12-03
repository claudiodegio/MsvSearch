package com.claudiodegio.msv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class BaseMaterialSearchView extends FrameLayout implements View.OnClickListener, View.OnFocusChangeListener, TextView.OnEditorActionListener, TextWatcher {

    final static String TAG = "BMSV";

    protected EditText mETSearchText;
    protected ImageButton mBtBack;
    protected ImageButton mBtClear;
    protected ViewGroup mSearchTopBar;
    protected OnSearchViewListener mListener;

    private boolean mIsOpen = false;
    protected boolean mIgnoreNextTextChange = false;

    public BaseMaterialSearchView(Context context) {
        this(context, null);
    }

    public BaseMaterialSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseMaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

        initStyle(attrs, defStyleAttr);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Msv, defStyleAttr, 0);

        if (a != null) {
            if (a.hasValue(R.styleable.Msv_searchBackgroud)) {
                setBackground(a.getDrawable(R.styleable.Msv_searchBackgroud));
            }

            if (a.hasValue(R.styleable.Msv_hint)) {
                mETSearchText.setHint(a.getString(R.styleable.Msv_hint));
            }

            if (a.hasValue(R.styleable.Msv_textColor)) {
                mETSearchText.setTextColor(a.getColor(R.styleable.Msv_textColor, defStyleAttr));
            }

            if (a.hasValue(R.styleable.Msv_textColorHint)) {
                mETSearchText.setHintTextColor(a.getColor(R.styleable.Msv_textColorHint, defStyleAttr));
            }

            a.recycle();
        }
    }

    public void setMenuItem(MenuItem menuItem) {
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showSearch(true);
                return true;
            }
        });
    }

    public void showSearch(){
        showSearch(false);
    }

    public void showSearch(boolean animate){
        if (mIsOpen) {
            return;
        }

        if (animate) {
            setVisibleWithAnimation();
        } else {
            mIsOpen = true;

            this.setVisibility(VISIBLE);

            if (mListener != null) {
                mListener.onSearchViewShown();
            }
            mETSearchText.requestFocus();
        }
    }
    public void closeSearch(){
        setVisibility(GONE);
        hideKeyboard(mETSearchText);
        mETSearchText.getEditableText().clear();
        mIsOpen = false;

        if (mListener != null) {
            mListener.onSearchViewClosed();
        }
    }

    public boolean isOpen(){
        return mIsOpen;
    }

    public void setQuery(String query, boolean submit) {
        mETSearchText.setText(query);

        if (submit) {
            handleSubmitQuery();
        }
    }

    private void setVisibleWithAnimation() {
        AnimationUtil.AnimationListener animationListener = new AnimationUtil.AnimationListener() {
            @Override
            public boolean onAnimationStart(View view) {
                return false;
            }

            @Override
            public boolean onAnimationEnd(View view) {
                mIsOpen = true;
                mETSearchText.requestFocus();
                if (mListener != null) {
                    mListener.onSearchViewShown();
                }
                return false;
            }

            @Override
            public boolean onAnimationCancel(View view) {
                return false;
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setVisibility(View.VISIBLE);
            AnimationUtil.reveal(this, animationListener);

        } else {
            // TODO da sistemare
            AnimationUtil.fadeInView(this, 1000, animationListener);
        }
    }

    private void initView(){
        Log.v(TAG, "initView: ");
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);

        mETSearchText = (EditText) findViewById(R.id.ed_search_text);
        mBtBack = (ImageButton) findViewById(R.id.bt_back);
        mBtClear = (ImageButton) findViewById(R.id.bt_clear);
        mSearchTopBar = (ViewGroup) findViewById(R.id.search_top_bar);

        setVisibility(GONE);

        mBtBack.setOnClickListener(this);
        mBtClear.setOnClickListener(this);
        mETSearchText.setOnClickListener(this);

        mETSearchText.setOnFocusChangeListener(this);
        mETSearchText.setOnEditorActionListener(this);
        mETSearchText.addTextChangedListener(this);
    }

    abstract protected  int getLayoutId();

    @Override
    public void onClick(View view) {

        Log.d(TAG, "onClick: ");
        if (view.getId() == R.id.bt_back) {
            closeSearch();
        } else if (view.getId() == R.id.bt_clear) {
            setQuery("", false);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
       // Log.d(TAG, "onFocusChange: " + b);

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        Log.d(TAG, "onEditorAction: " + actionId);

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            handleSubmitQuery();
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mIgnoreNextTextChange) {
            Log.d(TAG, "onTextChanged: " + charSequence+ " ignored");
            return;
        }

        Log.d(TAG, "onTextChanged: " + charSequence);

        if (mListener != null) {
            mListener.onQueryTextChange(charSequence.toString());
        }

        if (charSequence.length() > 0) {
            mBtClear.setVisibility(VISIBLE);
        } else {
            mBtClear.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void handleSubmitQuery(){
        boolean submitHandled = false;

        if (mListener != null) {
            submitHandled = mListener.onQueryTextSubmit(mETSearchText.getText().toString());
        }

        if (!submitHandled) {
            closeSearch();
        } else {
            hideKeyboard(mETSearchText);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboard(View view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1 && view.hasFocus()) {
            view.clearFocus();
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public void setOnSearchViewListener(OnSearchViewListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof BaseMSVSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        BaseMSVSavedState mSavedState = (BaseMSVSavedState) state;

        if (mSavedState.isOpen) {
            showSearch(false);
        }

        super.onRestoreInstanceState(mSavedState.getSuperState());
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        BaseMSVSavedState savedState = new BaseMSVSavedState(super.onSaveInstanceState());

        savedState.isOpen = mIsOpen;

        return savedState;
    }

    class BaseMSVSavedState extends BaseSavedState {
        public boolean isOpen;

        public BaseMSVSavedState(Parcelable superState) {
            super(superState);
        }
    }

    @Override
    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mSearchTopBar.setBackground(background);
        } else {
            mSearchTopBar.setBackgroundDrawable(background);
        }
    }
}
