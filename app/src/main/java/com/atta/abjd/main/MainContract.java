package com.atta.abjd.main;

public interface MainContract {



    interface View{

        void showMessage(String error);

        void setFlipperView();

        void hideItem();

        void renameItem();

        void checkIfSkipped();

        void setName();
    }

    interface Presenter{


    }
}
