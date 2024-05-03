package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends AppCompatActivity {

    TextView userName , userEmail;
    FirebaseAuth auth;
    DatabaseReference reference;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        String userID = auth.getCurrentUser().getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email =snapshot.child(userID).child("email").getValue(String.class);
                String password =snapshot.child(userID).child("password").getValue(String.class);

                userName.setText(email);
                userEmail.setText(password);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AboutActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AboutActivity.this,LoginActivity.class));
                finish();
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.about);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.dashboard){
                startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                overridePendingTransition(0,0);
                return true;
            } else if (item.getItemId() == R.id.about) {
                return true;
            } else if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });

    }


}