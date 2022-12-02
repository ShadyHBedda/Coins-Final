package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class Chart3 extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart3);

        Intent intent = getIntent();

        String name = intent.getStringExtra("coinName");
        String symbol = intent.getStringExtra("coinSymbol");

        TextView title = findViewById(R.id.textView14);
        title.setText(name);

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("https://www.tradingview.com/symbols/"+symbol+"USD/");
    }

    public void onClickRemove(View view){
        TextView textView = findViewById(R.id.textView14);
        String favoriteName = String.valueOf(textView.getText());

        SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(Chart3.this);

        try {
            db = DatabaseHelper.getReadableDatabase();
            db.delete("FAVORITE", "NAME = ?", new String[]{favoriteName});

            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Chart3.this, Favorite.class);
            startActivity(intent);
        }
        catch (SQLiteException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}