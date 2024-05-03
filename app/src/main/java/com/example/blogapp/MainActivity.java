package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

RecyclerView recyclerView;
List<BlogClass> blogList;
DatabaseReference databaseReference;

ValueEventListener eventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        blogList = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(MainActivity.this,blogList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("blog");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blogList.clear();
                for(DataSnapshot itemSnapshot:snapshot.getChildren()){
                    BlogClass blogClass = itemSnapshot.getValue(BlogClass.class);
                    blogList.add(blogClass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
           if(item.getItemId() == R.id.dashboard){
               startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
               overridePendingTransition(0,0);
               return true;
           } else if (item.getItemId() == R.id.home) {
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