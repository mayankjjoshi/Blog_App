package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    EditText userName, userPwd;
    TextView signUp;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.userName);
        userPwd = findViewById(R.id.userPwd);
        signUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userName.getText().toString();
                String pass = userPwd.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT)
                                                .show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        userPwd.setError("Password cannot be Empty");
                    }
                } else if (email.isEmpty()) {
                    userName.setError("Email cannot be Empty");
                } else {
                    userName.setError("Please enter Valid Email");
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

// signIn.setOnClickListener(new View.OnClickListener() {
// @Override
// public void onClick(View v) {
// if (!validateUserName() && !validatePwd()) {
//
// } else {
// checkUser();
// }
// }
// });
// public Boolean validateUserName(){
// String val = userName.getText().toString();
// if(val.isEmpty()){
// userName.setError("User Name Cannot Be Empty");
// return false;
// }else {
// userName.setError(null);
// return true;
// }
// }
//
// public Boolean validatePwd(){
// String val = userPwd.getText().toString();
// if(val.isEmpty()){
// userPwd.setError("Password Cannot Be Empty");
// return false;
// }else {
// userPwd.setError(null);
// return true;
// }
// }
//
// public void checkUser(){
// String name = userName.getText().toString().trim();
// String pwd = userPwd.getText().toString().trim();
//
// DatabaseReference reference =
// FirebaseDatabase.getInstance().getReference("users");
// Query checkUserDatabase = reference.orderByChild("username").equalTo(name);
//
// checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
// @Override
// public void onDataChange(@NonNull DataSnapshot snapshot) {
// if(snapshot.exists()){
// userName.setError(null);
// String pwdFromDB =
// snapshot.child(name).child("password").getValue(String.class);
//
// if(pwdFromDB.equals(pwd)){
// userPwd.setError(null);
//
// String usernamefromDB =
// snapshot.child(name).child("username").getValue(String.class);
// String useremailfromDB =
// snapshot.child(name).child("email").getValue(String.class);
// String passwordfromDB =
// snapshot.child(name).child("password").getValue(String.class);
//
//
// Intent intent = new Intent(LoginActivity.this,AboutActivity.class);
// intent.putExtra("USERNAME",usernamefromDB);
// intent.putExtra("EMAIL",useremailfromDB);
// intent.putExtra("PASSWORD",passwordfromDB);
//
// startActivity(intent);
// }else{
// userPwd.setError("Invalid Password");
// userPwd.requestFocus();
// }
// }else {
// userName.setError("user does not exits");
// userName.requestFocus();
// }
// }
//
// @Override
// public void onCancelled(@NonNull DatabaseError error) {
//
// }
// });
// }
