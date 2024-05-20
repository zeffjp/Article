package com.example.blogue_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private final Context context;

    // Database
    private static final String DB_NAME = "myDb.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "Article";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void save(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, article.getTitle());
        values.put(COLUMN_CONTENT, article.getContent());

        long result;
        try {
            result = db.insertOrThrow(TABLE_NAME, null, values);
            Toast.makeText(context, "L'ajout s'est effectué avec succès", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "Une erreur est survenue lors de l'ajout", Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
                    articles.add(new Article(id, title, content));
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Erreur lors de la récupération des articles", Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }

        return articles;
    }
}
