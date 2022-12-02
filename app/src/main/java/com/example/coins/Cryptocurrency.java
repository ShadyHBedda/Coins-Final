package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cryptocurrency extends AppCompatActivity {

    private ArrayList<Coin> AllCoins;
    public static List<String> listSymbols;
    public static List<String> listNames;

    String coinUrl = "";
    String globalUrl = "";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptocurrency);

        AllCoins = new ArrayList<>();
        listSymbols = new ArrayList<String>();
        listNames = new ArrayList<String>();

        coinUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1&sparkline=false";
        globalUrl ="https://api.coingecko.com/api/v3/global";

        listView = findViewById(R.id.listView);

        getCoin();
        getGlobalMarketCap();
        getVolume();
        getChart();

    }

    public void onClickFavorite(View view){
        Intent intent = new Intent(this, Favorite.class);
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

    public void getCoin() {

        RequestQueue queue = Volley.newRequestQueue(Cryptocurrency.this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, coinUrl, null, new Response.Listener<JSONArray>() {

            @Override

            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject responseObj = response.getJSONObject(i);
                        String id = responseObj.getString("symbol").toUpperCase();
                        double id2 = responseObj.getDouble("current_price");
                        long id3 = responseObj.getLong("market_cap");


                        Coin temp = new Coin(String.valueOf(i+1), id, "$ "+ String.valueOf(id2), "$ " + String.valueOf(id3));

                        AllCoins.add(temp);
                        FourColumn_ListAdapter adapter = new FourColumn_ListAdapter (Cryptocurrency.this, R. layout.custom_row_view, AllCoins);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {}

        });

        queue.add(jsonArrayRequest);

    }

    public void getGlobalMarketCap() {

        RequestQueue queue = Volley.newRequestQueue(Cryptocurrency.this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, globalUrl, null, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {

                    try {
                        JSONObject responseObj = response.getJSONObject("data");
                        JSONObject responseObj2 = responseObj.getJSONObject("total_market_cap");
                        long marketCap = responseObj2.getLong("usd");

                        TextView globalMarketCap = findViewById(R.id.textView10);
                        globalMarketCap.setText("$ " + String.valueOf(marketCap));


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

    public void getVolume() {

        RequestQueue queue = Volley.newRequestQueue(Cryptocurrency.this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, globalUrl, null, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {

                try {
                    JSONObject responseObj = response.getJSONObject("data");
                    JSONObject responseObj2 = responseObj.getJSONObject("total_volume");
                    Long volume = responseObj2.getLong("usd");

                    TextView globalMarketCap = findViewById(R.id.textView2);
                    globalMarketCap.setText("$ " + String.valueOf(volume));


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

    public void getChart(){

        RequestQueue queue = Volley.newRequestQueue(Cryptocurrency.this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, coinUrl, null, new Response.Listener<JSONArray>() {

            @Override

            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject responseObj = response.getJSONObject(i);
                        String symbol = responseObj.getString("symbol").toUpperCase();
                        listSymbols.add(symbol);
                        String name = responseObj.getString("name").toUpperCase();
                        listNames.add(name);

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {}

        });

        queue.add(jsonArrayRequest);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
                Intent intent = new Intent(Cryptocurrency.this, Chart.class);
                intent.putExtra("coinSymbol", listSymbols.get(position));
                intent.putExtra("coinName", listNames.get(position));

                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }
}