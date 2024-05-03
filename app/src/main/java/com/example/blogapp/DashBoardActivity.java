package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class DashBoardActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    DatabaseReference reference;

    EditText blog_title , blog_description;
    Button save_btn;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        blog_title = findViewById(R.id.blog_title);
        blog_description = findViewById(R.id.blog_description);
        save_btn = findViewById(R.id.save_btn);
        Array email;


       save_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String title = blog_title.getText().toString().trim();
               String description = blog_description.getText().toString().trim();
               if(title.isEmpty()){
                   blog_title.setError("Title cannot be empty");
               } else if (description.isEmpty()) {
                   blog_description.setError("Description cannot be empty");
               }else{

                   DatabaseReference ref = FirebaseDatabase.getInstance().getReference("blog");
                   BlogClass blogClass = new BlogClass(userID, title ,description);
                   ref.push().setValue(blogClass);
                   Toast.makeText(DashBoardActivity.this, "Blog Added Successfully", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(DashBoardActivity.this,MainActivity.class));
               }
           }
       });






        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(0,0);
                return true;
            } else if (item.getItemId() ==  R.id.dashboard) {
                return true;
            } else if (item.getItemId() == R.id.about) {
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });
    }
}