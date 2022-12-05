package com.example.coins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {

    final String TAG = "ChangePassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }
    public void onChangeClick(View view){
        Intent intent = new Intent(this, Settings.class);

        EditText current = findViewById(R.id.currentPassword);
        String currentPassword = current.getText().toString();

        TextView wrongCurrentPassword = findViewById(R.id.wrongCurrentPassword);
        wrongCurrentPassword.setText("");

        EditText newP = findViewById(R.id.newPassword);
        String newPassword = newP.getText().toString();

        TextView wrongNewPassword = findViewById(R.id.wrongNewPassword);
        wrongNewPassword.setText("");

        Boolean valid = true;

        if (currentPassword.equals("")){
            valid = false;
            wrongCurrentPassword.setText("Please enter password.");
        }
        if (newPassword.equals("")){
            valid = false;
            wrongNewPassword.setText("Please enter password.");
        }
        if (!currentPassword.equals("") && !newPassword.equals("") && currentPassword.equals(newPassword)){
            valid = false;
            wrongNewPassword.setText("Please enter NEW password.");
        }
        if (valid) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                            }
                        }
                    });

            Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show();

            startActivity(intent);

        }
    }
}