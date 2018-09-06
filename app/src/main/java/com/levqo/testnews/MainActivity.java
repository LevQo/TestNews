package com.levqo.testnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener{

    public static final String EXTRA_URL = "article";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_TIME = "time";
    public static final String EXTRA_PLACE = "place";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<Item> mItemList;
    private RequestQueue mRequestQueue;

    String url;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_football:
                    mItemList.clear();
                    parseJSON("http://mikonatoruri.win/list.php?category=football");
                    return true;
                case R.id.navigation_hockey:
                    mItemList.clear();
                    parseJSON("http://mikonatoruri.win/list.php?category=hockey");
                    return true;
                case R.id.navigation_tennis:
                    mItemList.clear();
                    parseJSON("http://mikonatoruri.win/list.php?category=tennis");
                    return true;
                case R.id.navigation_basketball:
                    mItemList.clear();
                    parseJSON("http://mikonatoruri.win/list.php?category=basketball");
                    return true;
                case R.id.navigation_volleyball:
                    mItemList.clear();
                    parseJSON("http://mikonatoruri.win/list.php?category=volleyball");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mItemList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON("http://mikonatoruri.win/list.php?category=football");

    }

    private void parseJSON(String url){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("events");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject event = jsonArray.getJSONObject(i);

                        String title = event.getString("title");
                        String coefficient = event.getString("coefficient");
                        String time = event.getString("time");
                        String place = event.getString("place");
                        String preview = event.getString("preview");
                        String article = event.getString("article");

                        mItemList.add(new Item(title, coefficient, time, place, preview, article));
                    }

                    mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, mItemList);
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                    mRecyclerViewAdapter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detatilIntent = new Intent(this, DetailActivity.class);
        Item clickedItem = mItemList.get(position);

        detatilIntent.putExtra(EXTRA_URL, clickedItem.getArticle());
        detatilIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detatilIntent.putExtra(EXTRA_TIME, clickedItem.getTime());
        detatilIntent.putExtra(EXTRA_PLACE, clickedItem.getPlace());

        startActivity(detatilIntent);
    }
}
