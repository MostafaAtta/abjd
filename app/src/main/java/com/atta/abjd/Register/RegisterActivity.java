package com.atta.abjd.Register;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atta.abjd.R;
import com.atta.abjd.main.MainActivity;

import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity
        implements RegisterContract.View ,View.OnClickListener, AdapterView.OnItemSelectedListener {



    ProgressDialog progressDialog;

    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener date;
    // login button
    Button register;

    Spinner locationSpinner;

    List<String> locationsArray;

    ArrayAdapter<String> locationsAdapter;
    // National ID, password edit text
    EditText emailText, passwordText, nameText, phoneText, birthdayText, confirmPasswordText;

    TextView skip;

    String birthdayString, locationSting;


    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        setDialog();
        registerPresenter = new RegisterPresenter(this, progressDialog, this);

        initiateViews();

    }

    private void initiateViews() {

        // National ID, Password input text
        emailText = (EditText)findViewById(R.id.email);
        passwordText = (EditText)findViewById(R.id.password);
        nameText = (EditText) findViewById(R.id.name);
        confirmPasswordText = (EditText) findViewById(R.id.password_confirm);

        skip = (TextView) findViewById(R.id.btnSkip);
        skip.setOnClickListener(this);

        // Register button
        register = (Button)findViewById(R.id.btnRegister);
        register.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view == register) {
            if (!registerPresenter.validate(nameText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(),
                    confirmPasswordText.getText().toString(), phoneText.getText().toString(), birthdayString, locationSting)) {
                register.setEnabled(true);
                return;
            }


            progressDialog.show();
            registerPresenter.register(nameText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(),
                    phoneText.getText().toString(), birthdayString, locationSting);
        }else if (view == skip){

            navigateToMain();

        }else if (view == birthdayText){

            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0){
            locationSting = locationsArray.get(position);
        }else {
            locationSting = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showMessage(String error) {

        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showViewError(String view, String error) {

        int id = getResources().getIdentifier(view, "id", this.getPackageName());
        EditText editText = (EditText)findViewById(id);
        editText.setError(error);
    }

    @Override
    public void navigateToMain() {

        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void setDialog() {

        if(progressDialog != null){
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(RegisterActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating your profile...");
    }
}
