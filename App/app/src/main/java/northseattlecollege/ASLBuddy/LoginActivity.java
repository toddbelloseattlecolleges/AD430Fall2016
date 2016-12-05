package northseattlecollege.ASLBuddy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.*;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import northseattlecollege.ASLBuddy.PasswordUtilities;

/**
 * Author: Kellan Nealy
 * Created 10/10/2016
 *
 * A login screen that offers login via email/password.
 * Also supports creating new accounts and password security.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    // ID to identity READ_CONTACTS permission request.
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * foo@example.com is interpreter, bar@example.com is HOH
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "interpreter@example.com:password", "hoh@example.com:password"
    };

    // Variables for user login
    private boolean isInterpreter;

    // Variables for new user Sign Up
    private boolean isNewUser = false;
    private boolean isNewInterpreter;

    // Variables keeping track of the login task
    private UserLoginTask mAuthTask = null;
    private String successfulUserID = "";

    // UI references.
    private AutoCompleteTextView existingEmailView;
    private EditText existingPasswordView;
    private AutoCompleteTextView newEmailView;
    private EditText newPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        existingEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        newEmailView = (AutoCompleteTextView) findViewById(R.id.create_email);
        populateAutoCompletes();
        existingPasswordView = (EditText) findViewById(R.id.password);
        newPasswordView = (EditText) findViewById(R.id.create_password);
        existingPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // events for SIGN IN
        Button SignInButton = (Button) findViewById(R.id.email_sign_in_button);
        SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isNewUser = false;
                attemptLogin();
            }
        });

        // events for SIGN UP
        Button SignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        SignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isNewUser = true;
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // Hide keyboard that by default comes up automatically
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt or API request is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        existingEmailView.setError(null);
        existingPasswordView.setError(null);
        newEmailView.setError(null);
        newPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email, password;
        EditText curPasswordView;
        AutoCompleteTextView curEmailView;
        if (isNewUser) {
            email = newEmailView.getText().toString();
            password = newPasswordView.getText().toString();
            curPasswordView = newPasswordView;
            curEmailView = newEmailView;
        } else {
            email = existingEmailView.getText().toString();
            password = existingPasswordView.getText().toString();
            curPasswordView = existingPasswordView;
            curEmailView = existingEmailView;
        }
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            curPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = curPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            curEmailView.setError(getString(R.string.error_field_required));
            focusView = curEmailView;
            cancel = true;

        } else if (!isEmailValid(email)) {
            curEmailView.setError(getString(R.string.error_invalid_email));
            focusView = curEmailView;
            cancel = true;
        }

        if (cancel) {
            // Error, request focus of form field
            focusView.requestFocus();
        } else {
            // get rid of the keyboard, should not come up on create
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(curPasswordView.getWindowToken(), 0);

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Method for client-side email validation
     */
    private boolean isEmailValid(String email) {
        // regex validation 
        return email.contains("@");
    }

    /**
     * Method for client-side password validation
     */
    private boolean isPasswordValid(String password) {
        // regex validation 
        return password.length() > 4;
    }

    /**
     * handles the radio button choices for new user sign-up
     * @param view selected view
     */
    protected void selectUserType(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            switch (view.getId()) {

                case R.id.HohUser:
                    isNewInterpreter = false;
                    break;

                case R.id.InterpreterUser:
                    isNewInterpreter = true;
                    break;
            }
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Populates emails in the email inputs
     */
    private void populateAutoCompletes() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Checks to see if this application can request contacts
     */
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(existingEmailView, R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                @TargetApi(Build.VERSION_CODES.M)
                public void onClick(View v) {
                    requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                }
            });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoCompletes();
            }
        }
    }

    /**
     * Handler method for populating email input with emails read from
     * the Android device, and prefers primary email.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
            // Retrieve data rows for the device user's 'profile' contact.
            Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                    ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

            // Select only email addresses.
            ContactsContract.Contacts.Data.MIMETYPE +
                    " = ?", new String[]{ContactsContract.CommonDataKinds.Email
            .CONTENT_ITEM_TYPE},

            // Show primary email addresses first. Note that there won't be
            // a primary email address if the user hasn't specified one.
            ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    /**
     * Handler method for storing the emails loaded for autocomplete
     */
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    /**
     * Handler method for when the loader resets
     */
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    /**
     * Adds the emails stored from the loader to the email autocomplete
     */
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
            new ArrayAdapter<>(LoginActivity.this,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        existingEmailView.setAdapter(adapter);
        newEmailView.setAdapter(adapter);
    }

    /**
     * Interface necessary for email input autocomplete
     */
    private interface ProfileQuery {
        String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    
    /**
     * Represents an asynchronous login/signup task used to authenticate
     * the user and possibly create a new account in the database.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String response = "";
            try {
                response = loginHTTP(isNewUser, mEmail, mPassword);

            } catch (Exception e) {
                Log.d("ERROR", "error with login HTTP request:\n" + e.getMessage()
                    + "\nStack Trace:\n" + e.getStackTrace().toString());
            }

            if (!response.isEmpty()) {
                Log.d("RESPONSE", response);

                // continue with JSON parsing
                if (isNewUser) {
                    // simply parse and store the user ID
                    try {
                        JSONObject responseJSON = new JSONObject(response);
                        boolean wasSuccessful = responseJSON.getBoolean("success");
                        if (wasSuccessful) {
                            successfulUserID = responseJSON.getString("user_id");
                            // find out if this is an interpreter user
                            return true;
                        } else {
                            return false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // validate password on the client side, set userID and user type
                    try {
                        JSONObject responseJSON = new JSONObject(response);
                        String hashFromDB = responseJSON.getString("hashed_password");

                        boolean isMatch = PasswordUtilities.VerifyPasswordMatch(mPassword,
                                hashFromDB);
                        if (isMatch) {
                            successfulUserID = responseJSON.getString("user_id");
                            isInterpreter = responseJSON.getBoolean("is_interpreter");
                            return true;
                        } else {
                            return runDummyCredentials();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return runDummyCredentials();
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                // start next activity depending on the logged in user-type
                Intent navigationIntent;
                if (isInterpreter || isNewInterpreter) {
                    navigationIntent = new Intent(LoginActivity.this, MenuInterpreter.class);
                } else {
                    navigationIntent = new Intent(LoginActivity.this, MenuHOH.class);
                }
                LoginActivity.this.startActivity(navigationIntent);

            } else {
                if (isNewUser) {
                    newEmailView.setError(getString(R.string.error_failed_login));
                    newEmailView.requestFocus();
                } else {
                    existingEmailView.setError(getString(R.string.error_failed_login));
                    existingEmailView.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        /**
         * Runs the dummy credentials if server login fails for any reason
         */
        public boolean runDummyCredentials() {
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    if (pieces[1].equals(mPassword)) {

                        // for debugging, if email is foo@example.com we login as interpreter
                        isInterpreter = mEmail.equals("interpreter@example.com");
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Queries the Server API using different paths depending on login/signup
     * and returns the response JSON as a String
     * @param isNewUser
     * @param email
     * @param pass
     * @return
     * @throws IOException
     */
    private String loginHTTP(boolean isNewUser, String email, String pass) throws IOException {
        InputStream is = null;
        int length = 1000;

        try {
            URL url;
            if (isNewUser) {
                // New user API workflow
                String hashSalt = PasswordUtilities.HashAndSaltPassword(pass);
                Log.d("NEW USER HASH", "\n\n\n" + hashSalt + "\n\n");
                int isInterpreterNum = isNewInterpreter ? 1 : 0;

                url = new URL("http://54.69.18.19/createUser?userEmail="
                        + email + "&isInterpreter=" + isInterpreterNum + "&hashedPassword="
                        + hashSalt);
            } else {
                // Existing user API workflow
                Log.d("EXISTING USER LOGIN", "\n" + email);
                url = new URL("http://54.69.18.19/getPassword?userEmail=" + email);
            }
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("INFO", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is, length);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * Converts the HTTP Response JSON into a String and returns that String
     * @param stream
     * @param length
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public String convertInputStreamToString(InputStream stream, int length) throws IOException,
            UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }
}