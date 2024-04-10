package com.example.promojio.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.example.promojio.view.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final static String LOG_TAG = "LOGCAT_LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputEditText usernameEditText = (TextInputEditText) findViewById(R.id.username);
        final TextInputLayout passwordInputLayout =
                (TextInputLayout) findViewById(R.id.passwordLayout);
        final TextInputEditText passwordEditText = (TextInputEditText) findViewById(R.id.password);

        final Button loginButton = (Button) findViewById(R.id.login);
        final Button registerButton = (Button) findViewById(R.id.register);
        final ProgressBar loadingProgressBar = (ProgressBar) findViewById(R.id.loading);

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
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Register.class);
            startActivity(intent);
        });
    }
}