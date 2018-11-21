package com.atta.abjd.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.atta.abjd.login.LoginActivity;
import com.atta.abjd.main.MainActivity;

public class SessionManager {


    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    private static SessionManager mInstance;
    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "HomeDeco";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // All Shared Preferences Keys
    private static final String IS_SKIPPED = "IsSkipped";

    // User name (make variable public to access from outside)
    public static final String KEY_ID = "user_id";

    // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";


    private static final String KEY_USER_LOCATION = "location";

    // User Name (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // User Name (make variable public to access from outside)
    public static final String KEY_USER_NAME = "userName";

    public static final String KEY_USER_IMAGE = "userImage";

    private static final String KEY_USER_BIRTHDAY = "birthday";

    private static final String KEY_USER_PHONE = "phone";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public static synchronized SessionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SessionManager(context);
        }
        return mInstance;
    }
    // Get Login State
    public String  getUserId(){
        return pref.getString(KEY_ID, "no User");
    }

    // Get Login State
    public String  getUserName(){
        return pref.getString(KEY_USER_NAME, "Guest");
    }

    // Get Login State
    public String  getEmail(){
        return pref.getString(KEY_EMAIL, "no email");
    }


    // Get Login State
    public String  getPassword(){
        return pref.getString(KEY_PASSWORD, "no password");
    }

    /**
     * Create login session
     * */
    public void createLoginSession(User user){

        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(IS_SKIPPED, false);
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();

    }

    /**
     * Create login session
     * */
    public void skip(){

        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Storing login value as TRUE
        editor.putBoolean(IS_SKIPPED, true);

        editor.putBoolean(IS_LOGIN, false);
        editor.apply();
    }

    public void updatePassword (String newpassword){
        // Storing Password in pref
        editor.putString(KEY_PASSWORD, newpassword);

        // commit changes
        editor.commit();
    }

    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){

            // user is logged in redirect him to Main Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

        }else if (this.isSkipped()){
            // user is logged in redirect him to Main Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

        }

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.putBoolean(IS_LOGIN, false);
        editor.putBoolean(IS_SKIPPED, false);
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    // Get Login State
    public boolean isSkipped(){
        return pref.getBoolean(IS_SKIPPED, false);
    }

    public String getUserImage() {
        return pref.getString(KEY_USER_IMAGE, "");
    }

    public void setUserImage(String image) {

        // Storing national ID in pref
        editor.putString(KEY_USER_IMAGE, image);


        // commit changes
        editor.commit();
    }
    public void setIsSkipped() {

        // Storing national ID in pref
        editor.putBoolean(IS_SKIPPED, true);


        // commit changes
        editor.commit();
    }

    public String getUserLocation() {
        return pref.getString(KEY_USER_LOCATION, "");
    }

}
