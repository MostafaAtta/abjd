package com.atta.abjd.main;

import android.content.Context;

public class MainPresenter implements MainContract.Presenter {


    private MainContract.View mView;

    private Context mContext;

    public MainPresenter(MainContract.View view, Context context) {

        mView = view;

        mContext = context;
    }

}


