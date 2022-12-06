package com.example.coins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private TextView underEmail, underPassword, CreateNewAccount;
    private Button btnLogin;

    FirebaseAuth mAuthLogin;

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        loginEmail = findViewById(R.id.email);
        loginPassword = findViewById(R.id.password);
        underEmail = findViewById(R.id.wrongEmail);
        underPassword = findViewById(R.id.wrongPassword);
        CreateNewAccount = findViewById(R.id.newAcct);
        btnLogin = findViewById(R.id.loginButton);

        mAuthLogin = FirebaseAuth.getInstance();

        // Toggle password visibility using eye icon
        ImageView togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);
        togglePasswordVisibility.setImageResource(R.drawable.password_invisible);
        togglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //If password is visible, hide it
                    loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // Change icon to reflect that
                    togglePasswordVisibility.setImageResource(R.drawable.password_invisible);
                } else {
                    // If password is hidden, show it
                    loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // Change icon to reflect that
                    togglePasswordVisibility.setImageResource(R.drawable.password_visible);
                }
            }
        });


        Intent intent = new Intent(this, Cryptocurrency.class);

        EditText enterPassword = findViewById(R.id.password);
        String password = enterPassword.getText().toString();

        TextView wrongPassword = findViewById(R.id.wrongPassword);
        wrongPassword.setText("");


        EditText enterEmail = findViewById(R.id.email);
        String email = enterEmail.getText().toString();

        TextView wrongEmail = findViewById(R.id.wrongEmail);
        wrongEmail.setText("");


        btnLogin.setOnClickListener(view -> {
            onLoginClick();
        });

        CreateNewAccount.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, SignUp.class));
        });
    }

    private void onLoginClick() {
        // Login user
        btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                Boolean valid = true;

                /*
                String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                */

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    loginEmail.setError("Email is required.");
                    loginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    /*
                    if (!matcher.matches()){
                        valid = false;
                        underEmail.setText("Please enter a valid email address.");
                    }
                    */

                    Toast.makeText(Login.this, "Please re-enter your email.", Toast.LENGTH_SHORT).show();
                    loginEmail.setError("Valid email is required.");
                    loginEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {

                    /*
                    if (password.equals("")) {
                        valid = false;
                        underPassword.setText("Please enter password");
                    }
                    */

                    Toast.makeText(Login.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    loginPassword.setError("Password is required.");
                    loginPassword.requestFocus();
                } else {
                    loginUser(email, password);

                }
            }
        });
    }

    private void loginUser(String email, String password) {
        Intent loginSuccessfulIntent = new Intent(Login.this, Cryptocurrency.class);
        loginSuccessfulIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);


            mAuthLogin.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(loginSuccessfulIntent);
                    } else {
                        try {
                            throw task.getException();
                        } catch(FirebaseAuthInvalidUserException e) {
                            loginEmail.setError("User invalid or no longer exists. Please register again.");
                            loginEmail.requestFocus();
                        } catch(FirebaseAuthInvalidCredentialsException e) {
                            loginEmail.setError("Invalid credentials. Please try again.");
                            loginEmail.requestFocus();
                        } catch (Exception e) {

                            Log.e(TAG, e.getMessage());
                            Toast.makeText(Login.this, "Login failed! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                }
            });
    }

    public void onCreateNewClick(View view){
        Intent intent = new Intent(this, SignUp.class);

        EditText enterEmail = findViewById(R.id.email);
        String email = enterEmail.getText().toString();

        EditText enterPassword = findViewById(R.id.password);
        String password = enterPassword.getText().toString();

        if (!email.equals("")){
            intent.putExtra("email", email);
        }

        if (!password.equals("")){
            intent.putExtra("password", password);
        }

        startActivity(intent);
    }
}