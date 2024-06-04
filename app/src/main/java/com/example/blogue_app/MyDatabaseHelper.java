// MyDatabaseHelper.java
package com.example.blogue_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myDb.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "Article";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_STATUS = "status";

    private final Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_STATUS + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;
        if (article != null && !article.getTitle().isEmpty() && !article.getContent().isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, article.getTitle());
            values.put(COLUMN_CONTENT, article.getContent());
            values.put(COLUMN_STATUS, article.getStatus());
            result = db.insert(TABLE_NAME, null, values);
            if (result == -1) {
                Toast.makeText(context, "Échec de l'ajout de l'article", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Article ajouté avec succès", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Attention champ vide", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return result;
    }

    public List<Article> getAllArticles() {
        List<Article> articleList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
                Article article = new Article(id, title, content, status);
                articleList.add(article);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return articleList;
    }

    public void updateArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, article.getTitle());
        values.put(COLUMN_CONTENT, article.getContent());
        values.put(COLUMN_STATUS, article.getStatus());
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(article.getId())});
        if (rowsAffected > 0) {
            Toast.makeText(context, "Article mis à jour avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Échec de la mise à jour de l'article", Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
        }

        Log.d("MyDatabaseHelper", "Lignes affectées : " + rowsAffected);
        Log.d("MyDatabaseHelper", "ID de l'article mis à jour : " + article.getId());

        db.close();
    }

    public void deleteArticle(int articleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(articleId)});
        if (rowsDeleted > 0) {
            Toast.makeText(context, "Article supprimé avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Échec de la suppression de l'article", Toast.LENGTH_SHORT).show();
        }

        Log.d("MyDatabaseHelper", "Lignes supprimées : " + rowsDeleted);
        Log.d("MyDatabaseHelper", "ID de l'article supprimé : " + articleId);
=======
        }

        Log.d("MyDatabaseHelper", "Lignes affectées : " + rowsAffected);
        Log.d("MyDatabaseHelper", "ID de l'article mis à jour : " + article.getId());
>>>>>>> 9350ed799b49a8485fb07f514b66e58587005c1e

        db.close();
    }
}
