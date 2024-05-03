package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText userEmail, userPwd;
    TextView signIn;
    Button signUp;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


//        if(auth.getCurrentUser() !=null){
//            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//            finish();
//        }


        auth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.userEmail);
        userPwd = findViewById(R.id.userPwd);
        signUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.signIn);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = userEmail.getText().toString().trim();
                String pass = userPwd.getText().toString().trim();

                if(user.isEmpty()){
                    userEmail.setError("Email cannot be empty");
                } else if (pass.isEmpty()) {
                    userPwd.setError("Password cannot be empty");
                }else {
                    auth.createUserWithEmailAndPassword(user , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                String userID = auth.getCurrentUser().getUid();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                HelperClass helperClass = new HelperClass(user , pass);
                                reference.child(userID).setValue(helperClass);
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            }else{
                                Toast.makeText(RegisterActivity.this, "Signup Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
//    public Boolean validateUserName(){
//        String val = userName.getText().toString();
//        if(val.isEmpty()){
//            userName.setError("User Name Cannot Be Empty");
//            return false;
//        }else {
//            userName.setError(null);
//            return true;
//        }
//    }

//    public Boolean validateEmail(){
//        String val = userEmail.getText().toString();
//        if(val.isEmpty()){
//            userEmail.setError("Password Cannot Be Empty");
//            return false;
//        }else {
//            userEmail.setError(null);
//            return true;
//        }
//    }

//    public Boolean validatePwd(){
//        String val = userPwd.getText().toString();
//        if(val.isEmpty()){
//            userPwd.setError("Password Cannot Be Empty");
//            return false;
//        }else {
//            userPwd.setError(null);
//            return true;
//        }
//    }
