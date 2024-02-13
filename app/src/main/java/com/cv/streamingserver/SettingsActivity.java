package com.cv.streamingserver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextInputEditText t=findViewById(R.id.waddr);
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t.getText().length()>0){
                    editor.putString("address",t.getText().toString());
                    editor.apply();
                }
                Intent i=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
