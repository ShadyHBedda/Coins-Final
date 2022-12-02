package com.example.coins;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.view.TintableBackgroundView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    int coin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);

        LinearLayout linear = findViewById(R.id.linear);
        ListView listView = findViewById(R.id.listViewFav);
        SQLiteOpenHelper DatabaseHelper = new DatabaseHelper(this);

        List<String> listNames = new ArrayList<String>();
        List<String> symbols = new ArrayList<String>();
        List<Integer> names = new ArrayList<Integer>();

        try {
            db = DatabaseHelper.getReadableDatabase();
            cursor = db.query("FAVORITE", new String[]{"_id", "NAME"}, null, null, null, null, null);


            if (cursor.getCount() > 0 && cursor != null) {
                Log.i("hi", "empty");
                if (cursor.moveToFirst()) {

                    do {
                        listNames.add(cursor.getString(1));
                    } while (cursor.moveToNext());

                }
                Log.i("list", String.valueOf(listNames));

                for (int i = 0; i < listNames.size(); i++) {
                    if (Cryptocurrency.listNames.indexOf(listNames.get(i).toUpperCase()) != -1) {
                        coin = Cryptocurrency.listNames.indexOf(listNames.get(i).toUpperCase());
                        symbols.add(Cryptocurrency.listSymbols.get(coin));
                        names.add(coin);
                    }
                }

                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
                        Intent intent = new Intent(Favorite.this, Chart3.class);
                        intent.putExtra("coinSymbol", symbols.get(position));
                        intent.putExtra("coinName", Cryptocurrency.listNames.get(names.get(position)));

                        startActivity(intent);
                    }
                };
                listView.setOnItemClickListener(itemClickListener);

                SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"NAME"},
                        new int[]{android.R.id.text1},
                        0);

                listView.setAdapter(cursorAdapter);
            } else {
                linear.removeAllViews();
                linear.setWeightSum(2);
                linear.setGravity(Gravity.TOP | Gravity.CENTER);

                TextView textView = new TextView(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1);
                params1.setMargins(0, 0, 0, 20);
                textView.setLayoutParams(params1);
                textView.setGravity(Gravity.BOTTOM);
                textView.setText("Empty :(");
                textView.setTextColor(Color.parseColor("#747679"));
                textView.setTextSize(25);
                linear.addView(textView);

                Button button = new Button(this);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(400, 0, 0.2F);
                params2.setMargins(0, 20, 0, 0);
                button.setLayoutParams(params2);
                button.setGravity(Gravity.TOP | Gravity.CENTER);
                button.setText("Add Coins To Favorite");
                button.setTextColor(Color.WHITE);
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3C90F3")));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(Favorite.this, Cryptocurrency.class);
                        startActivity(intent);
                    }
                });
                linear.addView(button);
            }
        } catch (SQLiteException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickMarket(View view){
        Intent intent = new Intent(this, Cryptocurrency.class);
        startActivity(intent);
    }

    public void onClickSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void onClickSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


}