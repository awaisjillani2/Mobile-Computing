package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rc = (RecyclerView) findViewById(R.id.rc);
        rc.setLayoutManager(new LinearLayoutManager(this));
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        InputStream in = getResources().openRawResource(R.raw.issues);
        byte[] data=null;
        String json = null;
        try{
            data = new byte[in.available()];
            while (in.read(data) != -1);
            json = new String(data);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        List<Book> books = gson.fromJson(json, Books.class).getBooks();
        rc.setAdapter(new MyAdapter(books, this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
                    Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
