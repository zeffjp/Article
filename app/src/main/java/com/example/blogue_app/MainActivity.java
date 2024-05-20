package com.example.blogue_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Article> articleList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.articleListView);
        FloatingActionButton addButton = findViewById(R.id.addArticleButton);
        dbHelper = new MyDatabaseHelper(this);

        articleList.add(new Article("Introduction a l'Intelligence Artificielle", "Dans cet article, nous allons explorer le fascinant domaine de l'intelligence artificielle (IA)..."));
        articleList.add(new Article("Les dernières tendances en développement web", "Dans cet article, nous allons explorer les tendances actuelles en matière de développement web..."));
        articleList.add(new Article("Sécurité informatique : Principes et bonnes pratiques", "Dans cet article, nous plongerons dans le monde de la sécurité informatique..."));
        articleList.add(new Article("Blockchain : Technologie révolutionnaire et ses applications", "Cet article sera une plongée profonde dans la technologie de la blockchain..."));
        articleList.add(new Article("Développement mobile : Comparaison des plateformes Android et iOS", "Dans cet article comparatif, nous examinerons les deux principales plateformes mobiles..."));
        articleList.add(new Article("Internet des objets (IoT) : Applications et défis", "L'Internet des objets (IoT) est une technologie en plein essor qui connecte les appareils physiques à Internet..."));
        articleList.add(new Article("Cybersécurité dans le cloud : Enjeux et meilleures pratiques", "Avec la montée en puissance du cloud computing, la cybersécurité dans le cloud est devenue une priorité essentielle..."));

        List<String> titles = new ArrayList<>();
        for (Article article : articleList) {
            titles.add(article.getTitle());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article selectedArticle = articleList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailsArticle.class);
                intent.putExtra("title", selectedArticle.getTitle());
                intent.putExtra("content", selectedArticle.getContent());
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddArticleDialog();
            }
        });
    }

    private void openAddArticleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_article, null);
        builder.setView(dialogView);

        EditText editTextTitle = dialogView.findViewById(R.id.titleTextView);
        EditText editTextContent = dialogView.findViewById(R.id.contentTextView);
        Button buttonAddArticle = dialogView.findViewById(R.id.addButton);
        Button backButton = dialogView.findViewById(R.id.backButton);

        AlertDialog dialog = builder.create();

        buttonAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();

                if (!title.isEmpty() && !content.isEmpty()) {
                    Article newArticle = new Article(title, content);
                    articleList.add(newArticle);
                    adapter.notifyDataSetChanged();

                    dbHelper.save(newArticle);

                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
