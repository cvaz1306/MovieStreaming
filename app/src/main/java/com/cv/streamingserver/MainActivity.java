package com.cv.streamingserver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    List<String> titles;
    List<String> data;
    List<String> vids;
    SharedPreferences sharedPreferences;
    public static boolean watch=true;
    public static String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        titles = new ArrayList<>();
        data = new ArrayList<>();
        vids=new ArrayList<>();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        CustomComponentAdapter adapter = new CustomComponentAdapter(data, titles, vids);
        recyclerView.setAdapter(adapter);
        Intent i=new Intent(MainActivity.this,SettingsActivity.class);
        FloatingActionButton settingsTrig=findViewById(R.id.settingsTrig);
        settingsTrig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform network operations here
                Looper.prepare();
                try{
                    fetchDataFromServer();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_LONG);
                }
            }
        }).start();

    }
    private void fetchDataFromServer(){
        URL url = null;
        try {
            url = new URL("http://"+sharedPreferences.getString("address","localhost:80")+"/api/list");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            InputStream in = null;
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String inputLine;
            String response = "";
            while ((inputLine = reader.readLine()) != null) {
                response+=(inputLine);
            }
            try {
                JSONArray jsonArray = new JSONArray(response); // jsonString is your JSON data

                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                    // Extract values from the JSON object
                    String title = jsonObject.getString("title");
                    String thumbnailUrl = jsonObject.getString("thumbnail");
                    String videoUrl = jsonObject.getString("video");

                    titles.add(title);
                    data.add("http://"+sharedPreferences.getString("address","localhost:80")+thumbnailUrl);
                    vids.add("http://"+sharedPreferences.getString("address","localhost:80")+videoUrl);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle JSON parsing errors

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            urlConnection.disconnect();
        }
    }
}
