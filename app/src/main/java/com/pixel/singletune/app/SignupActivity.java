package com.pixel.singletune.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SignupActivity extends Activity {

    protected EditText mUsername;
    protected EditText mEmail;
    protected EditText mPassword;
    protected EditText mPasswordConfirmation;
    protected Button   mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername = (EditText)findViewById(R.id.username_field);
        mEmail = (EditText)findViewById(R.id.email_field);
        mPassword = (EditText)findViewById(R.id.password_field);
        mPasswordConfirmation = (EditText)findViewById(R.id.password_confirmation_field);
        mSignupButton =(Button)findViewById(R.id.signup_button);

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

                if (username.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty() || email.isEmpty()){
                    // TODO: Turn fields to red highlights
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.title_signup_error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (password != passwordConfirmation) {
                    // TODO: Turn fields to red highlights
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(R.string.signup_password_mismatch_message)
                            .setTitle(R.string.title_signup_error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                else {
                    // TODO: Do signup Here
                }

            }
        });

    }

}
