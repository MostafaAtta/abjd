package com.atta.abjd.Register;

import android.app.ProgressDialog;
import android.content.Context;

import com.atta.abjd.model.APIService;
import com.atta.abjd.model.APIUrl;
import com.atta.abjd.model.Result;
import com.atta.abjd.model.SessionManager;
import com.atta.abjd.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPresenter implements RegisterContract.Presenter{

    private RegisterContract.View mView;

    private ProgressDialog mProgressDialog;

    private Context mContext;


    public RegisterPresenter(RegisterContract.View view, ProgressDialog progressDialog, Context context) {

        mView = view;

        mProgressDialog = progressDialog;

        mContext = context;
    }

    @Override
    public void register(String name, String email, String password, String phone, String birthdayString, String locationSting) {

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(name, email, password);

        //defining the call
        Call<Result> call = service.createUser(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                if(mProgressDialog != null || mProgressDialog.isShowing() ){
                    mProgressDialog.dismiss();
                }

                //displaying the message from the response as toast
                mView.showMessage(response.body().getMessage());
                //if there is no error
                if (!response.body().getError()) {
                    //starting profile activity
                    SessionManager.getInstance(mContext).createLoginSession(response.body().getUser());
                    mView.navigateToMain();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                if(mProgressDialog != null || mProgressDialog.isShowing() ){
                    mProgressDialog.dismiss();
                }
                mView.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public boolean validate(String name, String email, String password, String passwordConfirm, String phone,
                            String birthdayString, String locationSting) {
        boolean valid = true;

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (name.isEmpty()) {

            mView.showViewError("name","Enter your name");
            valid = false;
        } else {

            mView.showViewError("name",null);
        }

        if (!email.matches(emailPattern) || email.isEmpty())
        {
            mView.showViewError("email","Invalid email address");
            valid = false;

        }else {
            mView.showViewError("email",null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mView.showViewError("password","password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (passwordConfirm.isEmpty() || passwordConfirm.length() < 4 || passwordConfirm.length() > 10 ) {

            mView.showViewError("password_confirm","password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (!password.equals(passwordConfirm)){

            mView.showViewError("password","passwords not Matched");
            valid = false;
        }else {
            mView.showViewError("password",null);
            mView.showViewError("password_confirm",null);
        }



        return valid;
    }
}
