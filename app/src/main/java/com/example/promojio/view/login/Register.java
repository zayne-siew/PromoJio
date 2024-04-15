package com.example.promojio.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Register extends AppCompatActivity {

    private final static String LOG_TAG = "LOGCAT_Register";

    private SharedPreferences mPreferences;
    private String loginSharedPrefFile = "com.example.android.loginsharedprefs";
    public static final String USER_KEY = "User_Key";
    public static final String PASS_KEY = "Pass_Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPreferences = getSharedPreferences(loginSharedPrefFile, MODE_PRIVATE);
        String user_text = mPreferences.getString(USER_KEY,"");
        String pass_text = mPreferences.getString(PASS_KEY,"");

        final TextInputEditText nameEditText = (TextInputEditText) findViewById(R.id.name);
        final TextInputEditText usernameEditText = (TextInputEditText) findViewById(R.id.username);
        final TextInputLayout passwordInputLayout = (TextInputLayout) findViewById(R.id.setPasswordLayout);
        final TextInputEditText passwordEditText = (TextInputEditText) findViewById(R.id.password);

        final Button registerButton = (Button) findViewById(R.id.register);
        final ProgressBar loadingProgressBar = (ProgressBar) findViewById(R.id.loading);

        usernameEditText.setText(user_text);
        passwordEditText.setText(pass_text);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                boolean validPassword = passwordInputLayout.getLengthCounter().countLength(s) > 5;
                registerButton.setEnabled(validPassword);
                passwordInputLayout.setError(
                        validPassword ? null : getString(R.string.invalid_password)
                );
            }
        });

        registerButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);

            // Perform user login
            String name = Objects.requireNonNull(nameEditText.getText()).toString(),
                    username = Objects.requireNonNull(usernameEditText.getText()).toString(),
                    password = Objects.requireNonNull(passwordEditText.getText()).toString();
            UserService.newInstance().registerUser(
                    getApplicationContext(),
                    response -> {
                        loadingProgressBar.setVisibility(View.GONE);
                        if (response == null) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "User already exists, try again",
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }
                        setResult(Activity.RESULT_OK);

                        Intent intent = new Intent(Register.this, LoginActivity.class);
                        startActivity(intent);
                    },
                    name,
                    username,
                    password
            );
        });
    }
}