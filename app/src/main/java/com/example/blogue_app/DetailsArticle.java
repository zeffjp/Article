package com.example.blogue_app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetailsArticle extends AppCompatActivity {

    private EditText titleTextView;
    private EditText contentTextView;
    private Spinner statusSpinner;
    private MyDatabaseHelper dbHelper;
    private int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        statusSpinner = findViewById(R.id.statusSpinner);
        FloatingActionButton saveButton = findViewById(R.id.saveButton);
        FloatingActionButton backButton = findViewById(R.id.backButton);

        dbHelper = new MyDatabaseHelper(this);

        // Récupérer les données de l'article à partir de l'intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            articleId = extras.getInt("articleId");
            String title = extras.getString("title");
            String content = extras.getString("content");
            String status = extras.getString("status");

            titleTextView.setText(title);
            contentTextView.setText(content);

            setupStatusSpinner(status);
        }

        saveButton.setOnClickListener(v -> saveArticle());

        backButton.setOnClickListener(v -> finish());
    }

    private void setupStatusSpinner(String status) {
        List<StatusItem> statusItems = new ArrayList<>();
        statusItems.add(new StatusItem("Todo", ContextCompat.getColor(this, R.color.colorTodo)));
        statusItems.add(new StatusItem("InProgress", ContextCompat.getColor(this, R.color.colorInProgress)));
        statusItems.add(new StatusItem("Done", ContextCompat.getColor(this, R.color.colorDone)));
        statusItems.add(new StatusItem("Bug", ContextCompat.getColor(this, R.color.colorBug)));

        StatusSpinnerAdapter adapter = new StatusSpinnerAdapter(this, statusItems);
        statusSpinner.setAdapter(adapter);

        // Sélection du statut actuel dans le Spinner
        int position = 0;
        for (int i = 0; i < statusItems.size(); i++) {
            if (statusItems.get(i).getStatus().equals(status)) {
                position = i;
                break;
            }
        }
        statusSpinner.setSelection(position);
    }

    private void saveArticle() {
        String newTitle = titleTextView.getText().toString();
        String newContent = contentTextView.getText().toString();
        String newStatus = ((StatusItem) statusSpinner.getSelectedItem()).getStatus();

        if (!newTitle.isEmpty() && !newContent.isEmpty()) {
            Article updatedArticle = new Article(articleId, newTitle, newContent, newStatus);
            dbHelper.updateArticle(updatedArticle);

            Toast.makeText(this, "Article mis à jour", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
}
