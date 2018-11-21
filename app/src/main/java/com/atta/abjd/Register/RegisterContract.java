package com.atta.abjd.Register;

public interface RegisterContract {

    interface View{

        void showMessage(String error);

        void showViewError(String view, String error);

        void navigateToMain();

        void setDialog();
    }

    interface Presenter{

        void register(String name, String email, String password, String phone, String birthdayString, String locationSting);



        boolean validate(String name, String email, String password, String passwordConfirm, String phone, String birthdayString, String locationSting);
    }
}
