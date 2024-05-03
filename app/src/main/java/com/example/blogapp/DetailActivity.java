package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView detailTitle , detailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            detailTitle.setText(bundle.getString("TITLE"));
            detailDesc.setText(bundle.getString("DESCRIPTION"));

        }
    }
}