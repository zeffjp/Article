package com.example.blogue_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Article> articleList = new ArrayList<>();
    private ArticleAdapter adapter;
    private MyDatabaseHelper dbHelper;
    private Spinner filterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.articleRecyclerView);
        filterSpinner = findViewById(R.id.filterSpinner);
        FloatingActionButton addButton = findViewById(R.id.addArticleButton);
        dbHelper = new MyDatabaseHelper(this);

        setupRecyclerView();
        setupFilterSpinner();

        addButton.setOnClickListener(v -> openAddArticleDialog());
    }

    private void setupRecyclerView() {
        articleList.addAll(dbHelper.getAllArticles());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticleAdapter(this, articleList, new ArticleAdapter.OnArticleClickListener() {
            @Override
            public void onArticleClick(Article article) {
                openDetailsActivity(article);
            }

            @Override
            public void onDeleteClick(Article article) {
                showDeleteConfirmationDialog(article);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setupFilterSpinner() {
        List<StatusItem> statusItems = new ArrayList<>();
        statusItems.add(new StatusItem("All", ContextCompat.getColor(this, android.R.color.transparent)));
        statusItems.add(new StatusItem("Todo", ContextCompat.getColor(this, R.color.colorTodo)));
        statusItems.add(new StatusItem("InProgress", ContextCompat.getColor(this, R.color.colorInProgress)));
        statusItems.add(new StatusItem("Done", ContextCompat.getColor(this, R.color.colorDone)));
        statusItems.add(new StatusItem("Bug", ContextCompat.getColor(this, R.color.colorBug)));

        StatusSpinnerAdapter spinnerAdapter = new StatusSpinnerAdapter(this, statusItems);
        filterSpinner.setAdapter(spinnerAdapter);

        filterSpinner.setSelection(0);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatusItem selectedItem = (StatusItem) parent.getItemAtPosition(position);
                filterArticlesByStatus(selectedItem.getStatus());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void filterArticlesByStatus(String status) {
        List<Article> filteredList = new ArrayList<>();
        for (Article article : dbHelper.getAllArticles()) {
            if (status.equals("All") || article.getStatus().equals(status)) {
                filteredList.add(article);
            }
        }

        articleList.clear();
        articleList.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

    private void openAddArticleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_article, null);
        builder.setView(dialogView);

        EditText editTextTitle = dialogView.findViewById(R.id.titleTextView);
        EditText editTextContent = dialogView.findViewById(R.id.contentTextView);
        Spinner statusSpinner = dialogView.findViewById(R.id.statusSpinner);
        FloatingActionButton buttonAddArticle = dialogView.findViewById(R.id.addButton);
        FloatingActionButton backButton = dialogView.findViewById(R.id.backButton);
        AlertDialog dialog = builder.create();

        List<StatusItem> statusItems = new ArrayList<>();
        statusItems.add(new StatusItem("Todo", ContextCompat.getColor(this, R.color.colorTodo)));
        statusItems.add(new StatusItem("InProgress", ContextCompat.getColor(this, R.color.colorInProgress)));
        statusItems.add(new StatusItem("Done", ContextCompat.getColor(this, R.color.colorDone)));
        statusItems.add(new StatusItem("Bug", ContextCompat.getColor(this, R.color.colorBug)));
        StatusSpinnerAdapter spinnerAdapter = new StatusSpinnerAdapter(this, statusItems);
        statusSpinner.setAdapter(spinnerAdapter);

        buttonAddArticle.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String content = editTextContent.getText().toString();
            String status = ((StatusItem) statusSpinner.getSelectedItem()).getStatus();

            if (!title.isEmpty() && !content.isEmpty()) {
                Article newArticle = new Article(0, title, content, status);
                long result = dbHelper.addArticle(newArticle);

                if (result != -1) {
                    refreshArticleList();
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Article ajouté avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity
                            .this, "Erreur lors de l'ajout de l'article", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void refreshArticleList() {
        articleList.clear();
        articleList.addAll(dbHelper.getAllArticles());
        adapter.notifyDataSetChanged();
    }

    private void openDetailsActivity(Article article) {
        Intent intent = new Intent(MainActivity.this, DetailsArticle.class);
        intent.putExtra("articleId", article.getId());
        intent.putExtra("title", article.getTitle());
        intent.putExtra("content", article.getContent());
        intent.putExtra("status", article.getStatus());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            refreshArticleList();
        }
    }

    private void showDeleteConfirmationDialog(Article article) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer l'article")
                .setMessage("Êtes-vous sûr de vouloir supprimer cet article?")
                .setPositiveButton("Oui", (dialog, which) -> deleteArticle(article))
                .setNegativeButton("Non", null)
                .show();
    }

    private void deleteArticle(Article article) {
        dbHelper.deleteArticle(article.getId());
        refreshArticleList();
        Toast.makeText(this, "Article supprimé", Toast.LENGTH_SHORT).show();
    }
}
 
