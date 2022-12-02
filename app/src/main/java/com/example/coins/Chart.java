package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Chart extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();

        String name = intent.getStringExtra("coinName");
        String symbol = intent.getStringExtra("coinSymbol");

        TextView title = findViewById(R.id.textView14);
        title.setText(name);

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("https://www.tradingview.com/symbols/"+symbol+"USD/");
    }

    public void onClickFavorite(View view){
        TextView textView = findViewById(R.id.textView14);
        String favoriteName = String.valueOf(textView.getText());

        SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(Chart.this);

        try {
            db = DatabaseHelper.getWritableDatabase();
            cursor= db.query("FAVORITE", new String[]{"_id", "NAME"}, "NAME = ?", new String[]{favoriteName}, null, null, null);

            if (!cursor.moveToFirst()) {
                ContentValues favoriteValues = new ContentValues();

                favoriteValues.put("NAME", favoriteName);

                db.insert("FAVORITE", null, favoriteValues);
            }
            else{
                Toast.makeText(this, "Already favorite!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}