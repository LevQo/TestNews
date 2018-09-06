package com.levqo.testnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Iterator;
import java.util.List;

import static com.levqo.testnews.MainActivity.EXTRA_PLACE;
import static com.levqo.testnews.MainActivity.EXTRA_TIME;
import static com.levqo.testnews.MainActivity.EXTRA_TITLE;
import static com.levqo.testnews.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    public TextView mTextViewTeams;
    public TextView mTextViewTime;
    public TextView mTextViewTournament;
    public TextView mTextViewPlace;
    public TextView mTextViewStatistic;
    public TextView mTextViewPrediction;

    String articleUrl;
    String title;
    String time;
    String place;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        articleUrl = intent.getStringExtra(EXTRA_URL);
        title = intent.getStringExtra(EXTRA_TITLE);
        time = intent.getStringExtra(EXTRA_TIME);
        place = intent.getStringExtra(EXTRA_PLACE);

        mTextViewTeams = (TextView) findViewById(R.id.tv_teams);
        mTextViewTime = (TextView) findViewById(R.id.tv_time);
        mTextViewTournament = (TextView) findViewById(R.id.tv_tournament);
        mTextViewPlace = (TextView) findViewById(R.id.tv_place);
        mTextViewStatistic = (TextView) findViewById(R.id.tv_statistic);
        mTextViewPrediction = (TextView) findViewById(R.id.tv_prediction);

        mTextViewTeams.setText(title);
        mTextViewTime.setText(time);
        mTextViewPlace.setText(place);

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON(){
        String url = "http://mikonatoruri.win/post.php?article=" + articleUrl;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<String> array = new ArrayList<String>();

                    for (Iterator<String> it = response.keys(); it.hasNext(); ) {
                        String key = it.next();
                        String value = response.getString(key);
                        array.add(value);
                    }

                    mTextViewTournament.setText(array.get(3));
                    mTextViewPrediction.setText("Прогноз:\n" + array.get(5));
                    JSONArray jsonArray = response.getJSONArray("article");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject event = jsonArray.getJSONObject(i);
                        String statistic = event.getString("text");
                        mTextViewStatistic.setText("Статистика:\n" + statistic);
                    }
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
}
