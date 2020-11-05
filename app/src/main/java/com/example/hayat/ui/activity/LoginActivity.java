package com.example.hayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayat.ui.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText email, password;
    Button login;
    TextView forget, creat;
    FirebaseAuth mAuth;
    private void creat(){
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        login = findViewById(R.id.btn_login_login);
        forget = findViewById(R.id.txv_forget_login);
        creat = findViewById(R.id.txv_creat_account_login);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*getActionBar().setTitle("Login");
        getActionBar().addOnMenuVisibilityListener(true);*/
        creat();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_cheak();
            }
        });
    }
    private void login_cheak() {
        String Email = String.valueOf(email.getText().toString());
        String Password = String.valueOf(password.getText().toString());
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email Reqire", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password Reqire", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "أهلا و سهلا",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            //updateUI(user);
                        } else if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}