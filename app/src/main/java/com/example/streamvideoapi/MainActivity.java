package com.example.streamvideoapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "tag";
    private RecyclerView videoList;
    private VideoAdapter adapter;
    private List<Video> all_Video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoList = findViewById(R.id.recycleViewId);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        all_Video = new ArrayList<>();
        adapter = new VideoAdapter(this, all_Video);
        videoList.setAdapter(adapter);
        getJsonData();

    }

    private void getJsonData() {

        String URL = "https://raw.githubusercontent.com/bikashthapa01/myvideos-android-app/master/data.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray categories = response.getJSONArray("categories");
                    JSONObject categoriesData = categories.getJSONObject(0);
                    JSONArray videos = categoriesData.getJSONArray("videos");

                     for (int i = 0; i< videos.length(); i++)
                     {
                         JSONObject video = videos.getJSONObject(i);

                        Video v = new Video();
                        v.setTitle(video.getString("title"));
                        v.setDescription(video.getString("description"));
                        v.setAuthor(video.getString("subtitle"));
                        v.setImageUrl(video.getString("thumb"));
                        JSONArray vodeoUrl = video.getJSONArray("sources");
                        v.setVideoUrl(vodeoUrl.getString(0));

                        all_Video.add(v);
                        adapter.notifyDataSetChanged();
                       // Log.d(TAG, "Response :"+v.getVideoUrl());
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"onREsponse :"+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:"+error.getMessage());
            }
        });
        requestQueue.add(objectRequest);
    }
}