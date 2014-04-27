package com.pixel.singletune.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

import utils.LinkUserToInstallationHelper;


public class SignupActivity extends Activity {

    protected FlatEditText mUsername;
    protected FlatEditText mEmail;
    protected FlatEditText mPassword;
    protected FlatEditText mPasswordConfirmation;
    protected FlatButton mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FlatUI.setDefaultTheme(FlatUI.SAND);

        mUsername = (FlatEditText) findViewById(R.id.username_field);
        mEmail = (FlatEditText) findViewById(R.id.email_field);
        mPassword = (FlatEditText) findViewById(R.id.password_field);
        mPasswordConfirmation = (FlatEditText) findViewById(R.id.password_confirmation_field);
        mSignupButton = (FlatButton) findViewById(R.id.signup_button);

//        Signup button onClick listener
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String passwordConfirmation = mPasswordConfirmation.getText().toString();
                String email = mEmail.getText().toString();

                username = username.trim();
                password = password.trim();
                passwordConfirmation = passwordConfirmation.trim();
                email = email.trim();

                if (username.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty() || email.isEmpty()) {
                    // TODO: Turn fields to red highlights
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.title_signup_error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                // TODO This string comparism method will be helpful
                else if (!password.equals(passwordConfirmation)) {
                    // TODO: Turn fields to red highlights
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(R.string.signup_password_mismatch_message)
                            .setTitle(R.string.title_signup_error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // TODO: Do signup Here
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Success

                                LinkUserToInstallationHelper.LinkUserToInstallation.invoke();

                                // Associate the device with a user
                                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                                installation.put("user",ParseUser.getCurrentUser());
                                installation.saveInBackground();

                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                /* TODO Clean up returned error message */
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.title_signup_error)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        }
                    });
                }

            }
        });

    }

}
