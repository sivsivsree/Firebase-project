package com.example.android.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity {
    ProgressDialog mDialog;
    private FirebaseAuth mAuth;
    Button logOut;
    DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView welcome = (TextView) findViewById(R.id.welcome_user);
        logOut = (Button) findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            welcome.setText("Welcome to the LoginApp: "+email);
        }
    }
    public void logOut(View v){
        signOut();
        redirect();
        mDialog.setMessage("Redirecting User to Registration page");
        mDialog.show();
    }
    private void redirect(){
        Intent numbersIntent = new Intent(Welcome.this, Register.class);
        startActivity(numbersIntent);
    }
    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
    private void updateUI(FirebaseUser user) {

    }

}
