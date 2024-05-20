package com.example.blogue_app;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DetailsArticle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView contentTextView = findViewById(R.id.contentTextView);
        ImageButton backButton = findViewById(R.id.backButton);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        titleTextView.setText(title);
        contentTextView.setText(content);

        // Définir un OnClickListener pour le bouton de retour
        backButton.setOnClickListener(v -> finish());
    }
}
