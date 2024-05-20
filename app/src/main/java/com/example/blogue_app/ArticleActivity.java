package com.example.blogue_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ArticleActivity extends AppCompatActivity {
    TextView titleTextView;
    TextView contentTextView;
    FloatingActionButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        backButton = findViewById(R.id.backButton);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String title = extras.getString("title");
            String content = extras.getString("content");

            if (title != null) {
                titleTextView.setText(title);
            }
            if (content != null) {
                contentTextView.setText(content);
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
