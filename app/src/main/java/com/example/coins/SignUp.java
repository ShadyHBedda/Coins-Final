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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    FirebaseAuth mAuthSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuthSignUp = FirebaseAuth.getInstance();

        /*
        Intent intent = getIntent();

        EditText email = findViewById(R.id.emailSignUp);
        email.setText(intent.getStringExtra("email"));

        EditText password = findViewById(R.id.passwordSignUp);
        password.setText(String.valueOf(intent.getStringExtra("password")));
        */
    }

    private void createUser() {
        Log.d(TAG, "CREATE SUCCESSFUL");

        // Retrieve email and password inputs by user
        EditText SignUpEmail = findViewById(R.id.emailSignUp);
        String email = SignUpEmail.getText().toString();

        EditText SignUpPassword = findViewById(R.id.passwordSignUp);
        String password = SignUpPassword.getText().toString();

        // Firebase-provided method to create user with email and password
        mAuthSignUp.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuthSignUp.getCurrentUser();

                        /*
                        // Update user display name
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(INSERTNAMEHERE).build();
                        firebaseUser.updateProfile(profileChangeRequest);
                        */

                            // Enter user data into Firebase Database
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(email);

                            // Extracting user reference from registered user database
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Send verification email to user
                                        // firebaseUser.sendEmailVerification();

                                        Toast.makeText(SignUp.this, "User registered successfully.", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "TASK SUCCESSFUL");
                                        // Logs successful sign-up
                                        Log.d(TAG, "createUserWithEmail:success");

                                        Intent signUpSuccessfulIntent = new Intent(SignUp.this, Login.class);
                                        signUpSuccessfulIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);

                                        //startActivity(signUpSuccessfulIntent);
                                        finish();
                                    } else {
                                        Log.d(TAG, "createUserWithEmail:failure");
                                        Toast.makeText(SignUp.this, "User registration failed. Please try again.", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                        } else {
                            Log.d(TAG, "TASK FAILED");
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                SignUpPassword.setError("Your password is too weak. " +
                                        "Consider using a mix of letters, numbers, " +
                                        "and special characters, or making it longer.");
                                SignUpPassword.requestFocus();
                                Log.w(TAG, "createUserWithEmail:failure" + e.getMessage(), task.getException());
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                SignUpEmail.setError("The email address you entered is either " +
                                        "invalid or already in use. Please enter a valid " +
                                        "email address.");
                                SignUpEmail.requestFocus();
                                Log.w(TAG, "createUserWithEmail:failure" + e.getMessage(), task.getException());
                            } catch (FirebaseAuthUserCollisionException e) {
                                SignUpEmail.setError("This email  is already in use. " +
                                        "Please login or enter another email.");
                                SignUpEmail.requestFocus();
                                Log.w(TAG, "createUserWithEmail:failure" + e.getMessage(), task.getException());
                            } catch (Exception e) {
                                Log.w(TAG, "createUserWithEmail:failure" + e.getMessage(), task.getException());
                                Toast.makeText(SignUp.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            ;


                        }
                    }
                });
    }


    public void onSignUpClick(View view) {
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

        if (!matcher.matches()) {
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

        if (!verify.equals(password)) {
            valid = false;
            wrongVerify.setText(R.string.DoesNotMatchPassword);
        }

        if (valid) {
            createUser();
        }
    }

}