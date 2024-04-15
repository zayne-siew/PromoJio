package com.example.promojio.view.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.example.promojio.view.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final static String LOG_TAG = "LOGCAT_LoginActivity";

    private SharedPreferences mPreferences;
    private String loginSharedPrefFile = "com.example.android.loginsharedprefs";
    public static final String USER_KEY = "User_Key";
    public static final String PASS_KEY = "Pass_Key";
    public static final String LOGIN_KEY = "Login_Key";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPreferences = getSharedPreferences(loginSharedPrefFile, MODE_PRIVATE);
        String user_text = mPreferences.getString(USER_KEY,"");
        String pass_text = mPreferences.getString(PASS_KEY,"");

        final TextInputEditText usernameEditText = (TextInputEditText) findViewById(R.id.username);
        final TextInputLayout passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordLayout);
        final TextInputEditText passwordEditText = (TextInputEditText) findViewById(R.id.password);

        final Button loginButton = (Button) findViewById(R.id.login);
        final Button registerButton = (Button) findViewById(R.id.register);
        final ProgressBar loadingProgressBar = (ProgressBar) findViewById(R.id.loading);

        usernameEditText.setText(user_text);
        passwordEditText.setText(pass_text);
        boolean validPassword = passwordInputLayout.getLengthCounter().countLength(passwordEditText.getEditableText()) > 5;
        loginButton.setEnabled(validPassword);
        passwordInputLayout.setError(
                validPassword ? null : getString(R.string.invalid_password)
        );

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                boolean validPassword = passwordInputLayout.getLengthCounter().countLength(s) > 5;
                loginButton.setEnabled(validPassword);
                passwordInputLayout.setError(
                        validPassword ? null : getString(R.string.invalid_password)
                );
            }
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);

            // Perform user login
            String username = Objects.requireNonNull(usernameEditText.getText()).toString(),
                    password = Objects.requireNonNull(passwordEditText.getText()).toString();
            UserService.newInstance().userLogin(
                    getApplicationContext(),
                    response -> {
                        loadingProgressBar.setVisibility(View.GONE);
                        if (response == null) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.login_failed,
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }
                        setResult(Activity.RESULT_OK);

                        // Switch to main activity once logged in
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    },
                    username,
                    password
            );
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(UserService.newInstance());
            preferencesEditor.putString(LOGIN_KEY,json);
            preferencesEditor.putString(USER_KEY, username);
            preferencesEditor.putString(PASS_KEY, password);
            preferencesEditor.apply();
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Register.class);
            startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        final TextInputEditText usernameEditText = (TextInputEditText) findViewById(R.id.username);
        final TextInputEditText passwordEditText = (TextInputEditText) findViewById(R.id.password);

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(USER_KEY, Objects.requireNonNull(usernameEditText.getText()).toString());
        preferencesEditor.putString(PASS_KEY, Objects.requireNonNull(passwordEditText.getText()).toString());
        preferencesEditor.apply();
    }
}