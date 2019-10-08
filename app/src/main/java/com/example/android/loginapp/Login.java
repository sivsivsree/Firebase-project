package com.example.android.loginapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    private static final String TAG = "Register";
    Button mRegisterbtn;
    TextView mLoginPageBack;
    DatabaseReference mdatabase;
    String Name, Email, Password;
    ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void init() {
        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        mRegisterbtn = (Button) findViewById(R.id.register_btn);
        mLoginPageBack = (TextView) findViewById(R.id.loginBackbtn);
        // for auth
        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        mLoginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    public void onClick(View v) {

        if (v == mRegisterbtn) {
            startActivity(new Intent(Login.this, Register.class));
        } else if (v == mLoginPageBack) {
            UserLogin();
        }
    }

    private void UserLogin() {


        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (Password.length() < 6) {
            Toast.makeText(Login.this, "Passwor must be greater then 6 digit", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Sign In...");
        mDialog.show();

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            boolean emailVerified = user.isEmailVerified();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            if (emailVerified == true) {

                                Toast.makeText(Login.this, "Authentication Sucessfull.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, Welcome.class));
                                updateUI(user);
                            } else if (emailVerified == false) {
                                Toast.makeText(Login.this, "Email Not Verified Please Verify Your Mail.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {

        com.example.android.loginapp.User user = BuildNewuser();
        mdatabase.child(uid).setValue(user);
    }


    private User BuildNewuser() {
        return new User(
                getDisplayName(),
                getUserEmail(),
                new Date().getTime()
        );
    }

    private void updateUI(FirebaseUser user) {

    }

    public String getDisplayName() {
        return Name;
    }

    public String getUserEmail() {
        return Email;
    }

}

