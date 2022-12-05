package com.example.coins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    FirebaseAuth mAuth;

    private void createUser(){

        EditText SignUpEmail = findViewById(R.id.emailSignUp);
        String email = SignUpEmail.getText().toString();

        EditText SignUpPassword = findViewById(R.id.passwordSignUp);
        String password = SignUpPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(SignUp.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, Login.class));
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUp.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

        EditText email = findViewById(R.id.emailSignUp);
        email.setText(intent.getStringExtra("email"));

        EditText password = findViewById(R.id.passwordSignUp);
        password.setText(String.valueOf(intent.getStringExtra("password")));
    }

    public void onSignUpClick(View view){
        EditText enterPassword = findViewById(R.id.passwordSignUp);
        String password = enterPassword.getText().toString();

        TextView wrongPassword = findViewById(R.id.wrongPasswordSignUp);
        wrongPassword.setText("");

        EditText verifyPassword = findViewById(R.id.verifySignUp);
        String verify = verifyPassword.getText().toString();

        TextView wrongVerify = findViewById(R.id.wrongVerifySignUp);
        wrongVerify.setText("");

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        EditText enterEmail = findViewById(R.id.emailSignUp);
        String email = enterEmail.getText().toString();

        TextView wrongEmail = findViewById(R.id.wrongEmailSignUp);
        wrongEmail.setText("");

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        Boolean valid = true;

        if (!matcher.matches()){
            valid = false;
            wrongEmail.setText(R.string.InvalidEmail);
        }

        if (email.equals("")) {
            valid = false;
            wrongEmail.setText(R.string.EmptyEmail);
        }

        if (password.equals("")) {
            valid = false;
            wrongPassword.setText(R.string.EmptyPassword);
        }

        if (!verify.equals(password)){
            valid = false;
            wrongVerify.setText(R.string.DoesNotMatchPassword);
        }

        if (valid) {
            createUser();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

}