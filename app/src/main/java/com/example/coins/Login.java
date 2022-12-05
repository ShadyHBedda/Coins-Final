package com.example.coins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    EditText LoginEmail;
    EditText LoginPassword;
    TextView CreateNewAccount;
    Button btnLogin;

    FirebaseAuth mAuth;

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginEmail = findViewById(R.id.email);
        LoginPassword = findViewById(R.id.password);
        CreateNewAccount = findViewById(R.id.newAcct);
        btnLogin = findViewById(R.id.loginButton);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            onLoginClick();
        });

        CreateNewAccount.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, SignUp.class));
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

    public void onLoginClick(){
        Intent intent = new Intent(this, Cryptocurrency.class);

        EditText enterPassword = findViewById(R.id.password);
        String password = enterPassword.getText().toString();

        TextView wrongPassword = findViewById(R.id.wrongPassword);
        wrongPassword.setText("");

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        EditText enterEmail = findViewById(R.id.email);
        String email = enterEmail.getText().toString();

        TextView wrongEmail = findViewById(R.id.wrongEmail);
        wrongEmail.setText("");

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        Boolean valid = true;

        if (!matcher.matches()){
            valid = false;
            wrongEmail.setText("Please enter a valid email address.");
        }
        if (password.equals("")) {
            valid = false;
            wrongPassword.setText("Please enter password");
        }
        if (valid) {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            
            startActivity(intent);
        }
    }
}