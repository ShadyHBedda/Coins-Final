package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private ArrayList<Coin> AllCoins;
    private List<Integer> listIndex;

    String searchUrl = "";
    String search = "";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AllCoins = new ArrayList<>();
        listIndex = new ArrayList<Integer>();

        listView = findViewById(R.id.listViewSearch);

        getChart();
    }

    public void onClickMarket(View view){
        Intent intent = new Intent(this, Cryptocurrency.class);
        startActivity(intent);
    }

    public void onClickFavorite(View view){
        Intent intent = new Intent(this, Favorite.class);
        startActivity(intent);
    }

    public void onClickSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void onClickSearch(View view){

        EditText editText = findViewById(R.id.searchText);
        search = String.valueOf(editText.getText()).toLowerCase();
        editText.setText("");

        if (search != null) {
            searchUrl = "https://api.coingecko.com/api/v3/simple/price?ids=" + search + "&vs_currencies=usd&include_market_cap=true";

            if (Cryptocurrency.listNames.indexOf(search.toUpperCase()) != -1) {
                listIndex.add(Cryptocurrency.listNames.indexOf(search.toUpperCase()));
            }

            Log.i("index", String.valueOf(listIndex));

            RequestQueue queue = Volley.newRequestQueue(Search.this);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, searchUrl, null, new Response.Listener<JSONObject>() {

                @Override

                public void onResponse(JSONObject response) {

                    try {
                        JSONObject responseObj = response.getJSONObject(search);
                        double price = responseObj.getDouble("usd");
                        long volume = responseObj.getLong("usd_market_cap");

                        Coin temp = new Coin(String.valueOf(1), search.toUpperCase(), "$ "+ String.valueOf(price), "$ " + String.valueOf(volume));

                        AllCoins.add(temp);
                        ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter (Search.this, R. layout.custom_row_view2, AllCoins);
                        listView.setAdapter(adapter);

//                        getChart(listIndex);

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }

            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                }

            });

            queue.add(jsonObjectRequest);
        }

    }

    public void getChart(){

        Log.i("index1", String.valueOf(listIndex));

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {

                Log.i("index2", Cryptocurrency.listSymbols.get(listIndex.get(position)));
                Log.i("index3", Cryptocurrency.listNames.get(listIndex.get(position)));

                Intent intent = new Intent(Search.this, Chart2.class);
                intent.putExtra("coinSymbol", Cryptocurrency.listSymbols.get(listIndex.get(position)));
                intent.putExtra("coinName", Cryptocurrency.listNames.get(listIndex.get(position)));

                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);


    }
}